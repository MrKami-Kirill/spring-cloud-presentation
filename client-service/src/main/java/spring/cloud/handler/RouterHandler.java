package spring.cloud.handler;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static spring.cloud.model.entity.mapper.TClientMapper.fromAddClientDto;
import static spring.cloud.model.entity.mapper.TClientMapper.fromChangeClientDto;
import static spring.cloud.model.entity.mapper.TContactMapper.fromAddContactDto;
import static spring.cloud.util.MonoHelper.optionalMono;
import static spring.cloud.util.MonoHelper.optionalMonoFromFlux;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import spring.cloud.model.dto.request.AddClient;
import spring.cloud.model.dto.request.AddContact;
import spring.cloud.model.dto.request.ChangeClient;
import spring.cloud.model.dto.response.ClientContactsResponse;
import spring.cloud.model.dto.response.ClientResponse;
import spring.cloud.model.dto.response.ClientsResponse;
import spring.cloud.model.entity.TClient;
import spring.cloud.model.entity.TContact;
import spring.cloud.model.mapper.ClientContactsResponseMapper;
import spring.cloud.model.mapper.ClientResponseMapper;
import spring.cloud.model.mapper.ClientsResponseMapper;
import spring.cloud.repository.TClientRepository;
import spring.cloud.repository.TContactRepository;

@RefreshScope
@Component
@RequiredArgsConstructor
public class RouterHandler {

    //Для тестирования Spring Cloud Bus
    @Value("${text.test-message-log}")
    private String message;

    private final TClientRepository tClientRepository;
    private final TContactRepository tContactRepository;
    private final ValidationHandler<AddClient> addClientValidationHandler;
    private final ValidationHandler<AddContact> addContactValidationHandler;

    @HystrixCommand
    public Mono<ServerResponse> getClients(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                .body(fromPublisher(tClientRepository.findAll().collectList()
                        .map(ClientsResponseMapper::toDto), ClientsResponse.class));
    }

    @HystrixCommand
    public Mono<ServerResponse> getClientById(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                .body(fromPublisher(tClientRepository.findById(Long.valueOf(request.pathVariable("clientId")))
                        .map(ClientResponseMapper::toDto), ClientResponse.class));
    }

    @HystrixCommand
    public Mono<ServerResponse> getClientContacts(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                .body(fromPublisher(optionalMonoFromFlux(
                                tContactRepository.findByClientId(Long.valueOf(request.pathVariable("clientId"))))
                                .map(data -> data.isPresent() ? ClientContactsResponseMapper.toDto(data.get())
                                        : ClientContactsResponseMapper.emptyDto()),
                        ClientContactsResponse.class));
    }

    @HystrixCommand
    public Mono<ServerResponse> addClient(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                .body(fromPublisher(addClientValidationHandler.validate(request.bodyToMono(AddClient.class),
                                AddClient.class)
                        .flatMap(req -> tClientRepository.save(fromAddClientDto(req)))
                        .map(TClient::getId), Long.class));
    }

    @HystrixCommand
    public Mono<ServerResponse> changeClient(ServerRequest request) {
        Long clientId = Long.valueOf(request.pathVariable("clientId"));
        return ok().contentType(APPLICATION_JSON)
                .body(fromPublisher(request.bodyToMono(ChangeClient.class)
                                .flatMap(req -> Mono.zip(Mono.just(req),
                                        optionalMono(tClientRepository.findById(clientId))))
                                .flatMap(zip -> {
                                    Optional<TClient> client = zip.getT2();
                                    if (client.isEmpty()) {
                                        return Mono.error(
                                                new RuntimeException("Клиент с id = " + clientId + " не найден"));
                                    }

                                    return tClientRepository.save(fromChangeClientDto(client.get(), zip.getT1()));
                                })
                                .map(ClientResponseMapper::toDto),
                        ClientResponse.class));
    }

    @HystrixCommand
    public Mono<ServerResponse> addContactToClient(ServerRequest request) {
        Long clientId = Long.valueOf(request.pathVariable("clientId"));
        return ok().contentType(APPLICATION_JSON)
                .body(fromPublisher(addContactValidationHandler.validate(request.bodyToMono(AddContact.class),
                                AddContact.class)
                        .flatMap(req -> Mono.zip(Mono.just(req), optionalMono(tClientRepository.findById(clientId))))
                        .flatMap(zip -> {
                            if (zip.getT2().isEmpty()) {
                                return Mono.error(
                                        new RuntimeException("Клиент с id = " + clientId + " не найден"));
                            }

                            return tContactRepository.save(fromAddContactDto(zip.getT1(), clientId));
                        })
                        .map(TContact::getId), Long.class));
    }

    @HystrixCommand
    public Mono<ServerResponse> deleteClient(ServerRequest request) {
        Long clientId = Long.valueOf(request.pathVariable("clientId"));
        return ok().build(optionalMono(tClientRepository.findById(clientId))
                .flatMap(client -> {
                    if (client.isEmpty()) {
                        return Mono.error(
                                new RuntimeException("Клиент с id = " + clientId + " не найден"));
                    }

                    return Mono.just(client);
                }).then(tContactRepository.deleteAllByClientId(clientId))
                .then(tClientRepository.deleteById(clientId)));
    }

    @HystrixCommand
    public Mono<ServerResponse> deleteClientContact(ServerRequest request) {
        Long clientId = Long.valueOf(request.pathVariable("clientId"));
        Long contactId = Long.valueOf(request.pathVariable("contactId"));
        return ok().build(optionalMono(tContactRepository.findByClientId(clientId).collectList())
                .flatMap(contacts -> {
                    if (contacts.isEmpty()) {
                        return Mono.error(
                                new RuntimeException("У Клиента с id = " + clientId + " нет контактов"));
                    }
                    TContact contact =
                            contacts.get().stream()
                                    .filter(f -> contactId.equals(f.getId()))
                                    .findAny()
                                    .orElse(null);
                    if (contact == null) {
                        return Mono.error(
                                new RuntimeException(
                                        "Контакт с id = " + contactId + " не принадлежит Клиенту с id = " + clientId));
                    }

                    return tContactRepository.delete(contact);
                }));
    }

}
