package spring.cloud.handler;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import spring.cloud.feign.ClientServiceProxy;
import spring.cloud.model.dto.request.AddClient;
import spring.cloud.model.dto.request.AddContact;
import spring.cloud.model.dto.request.ChangeClient;
import spring.cloud.model.dto.response.ClientContactsResponse;
import spring.cloud.model.dto.response.ClientResponse;
import spring.cloud.model.dto.response.ClientsResponse;

@Component
@RequiredArgsConstructor
public class RouterHandler {

    private final ClientServiceProxy clientServiceProxy;

    @HystrixCommand
    public Mono<ServerResponse> getClients(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                .body(fromPublisher(clientServiceProxy.getClients(), ClientsResponse.class));
    }

    @HystrixCommand
    public Mono<ServerResponse> getClientById(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                .body(fromPublisher(clientServiceProxy.getClientById(request.pathVariable("clientId")),
                        ClientResponse.class));
    }

    @HystrixCommand
    public Mono<ServerResponse> getClientContacts(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                .body(fromPublisher(clientServiceProxy.getClientContacts(request.pathVariable("clientId")),
                        ClientContactsResponse.class));
    }

    @HystrixCommand
    public Mono<ServerResponse> addClient(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                .body(fromPublisher(clientServiceProxy.addClient(request.bodyToMono(AddClient.class)),
                        Long.class));
    }

    @HystrixCommand
    public Mono<ServerResponse> changeClient(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                .body(fromPublisher(clientServiceProxy.changeClient(request.pathVariable("clientId"),
                                request.bodyToMono(ChangeClient.class)),
                        ClientResponse.class));
    }

    @HystrixCommand
    public Mono<ServerResponse> addContactToClient(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                .body(fromPublisher(clientServiceProxy.addContactToClient(request.pathVariable("clientId"),
                                request.bodyToMono(AddContact.class)),
                        Long.class));
    }

    @HystrixCommand
    public Mono<ServerResponse> deleteClient(ServerRequest request) {
        return ok().build(clientServiceProxy.deleteClient(request.pathVariable("clientId")));
    }

    @HystrixCommand
    public Mono<ServerResponse> deleteClientContact(ServerRequest request) {
        return ok().build(clientServiceProxy.deleteClientContact(request.pathVariable("clientId"),
                request.pathVariable("contactId")));
    }

}
