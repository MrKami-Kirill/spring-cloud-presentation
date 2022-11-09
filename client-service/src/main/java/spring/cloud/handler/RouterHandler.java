package spring.cloud.handler;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static spring.cloud.util.MonoHelper.optionalMono;
import static spring.cloud.util.MonoHelper.optionalMonoFromFlux;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import spring.cloud.model.dto.request.AddClient;
import spring.cloud.model.dto.request.ChangeClient;
import spring.cloud.model.dto.response.ClientContactsResponse;
import spring.cloud.model.dto.response.ClientResponse;
import spring.cloud.model.dto.response.ClientsResponse;
import spring.cloud.model.entity.TClient;
import spring.cloud.model.mapper.ClientContactsResponseMapper;
import spring.cloud.model.mapper.ClientResponseMapper;
import spring.cloud.model.mapper.ClientsResponseMapper;
import spring.cloud.repository.TClientRepository;
import spring.cloud.repository.TContactRepository;

@Component
@RequiredArgsConstructor
public class RouterHandler {

    private final TClientRepository tClientRepository;
    private final TContactRepository tContactRepository;
    private final ValidationHandler<AddClient> addClientValidationHandler;

    public Mono<ServerResponse> getClients(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                .body(fromPublisher(tClientRepository.findAll().collectList()
                        .map(ClientsResponseMapper::toDto), ClientsResponse.class));
    }

    public Mono<ServerResponse> getClientById(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                .body(fromPublisher(tClientRepository.findById(Long.valueOf(request.pathVariable("clientId")))
                        .map(ClientResponseMapper::toDto), ClientResponse.class));
    }

    public Mono<ServerResponse> getClientContacts(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                .body(fromPublisher(optionalMonoFromFlux(
                                tContactRepository.findByClientId(Long.valueOf(request.pathVariable("clientId"))))
                                .map(data -> data.isPresent() ? ClientContactsResponseMapper.toDto(data.get())
                                        : ClientContactsResponseMapper.emptyDto()),
                        ClientContactsResponse.class));
    }

    public Mono<ServerResponse> addClient(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                .body(fromPublisher(addClientValidationHandler.validate(request.bodyToMono(AddClient.class),
                                AddClient.class)
                        .flatMap(req -> tClientRepository.save(new TClient(req)))
                        .map(TClient::getId), Long.class));
    }

    public Mono<ServerResponse> changeClient(ServerRequest request) {
        String clientId = request.pathVariable("clientId");
        return ok().contentType(APPLICATION_JSON)
                .body(fromPublisher(request.bodyToMono(ChangeClient.class)
                                .flatMap(req -> Mono.zip(Mono.just(req),
                                        optionalMono(tClientRepository.findById(Long.valueOf(clientId)))))
                                .map(zip -> {
                                    ChangeClient changeClient = zip.getT1();
                                    Optional<TClient> tClient = zip.getT2();
                                    tClient.isPresent() ? tClientRepository.save(
                                            tClient.get().setSurName(zip.getT1().getSurName())
                                                    .setFirstName(zip.getT1().getFirstName()))
                                            .map(ClientResponseMapper::toDto)
                                            : Mono.error(new RuntimeException("Клиент с id = " + clientId + " не найден"))
                                }),
                        ClientResponse.class));
    }

    public Mono<ServerResponse> deleteClient(ServerRequest request) {
        return null;
    }

    public Mono<ServerResponse> deleteClientContact(ServerRequest request) {
        return null;
    }

}
