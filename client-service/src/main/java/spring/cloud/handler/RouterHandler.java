package spring.cloud.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import spring.cloud.repository.TClientRepository;
import spring.cloud.repository.TContactRepository;

@Component
@RequiredArgsConstructor
public class RouterHandler {

    private final TClientRepository tClientRepository;
    private final TContactRepository tContactRepository;

    public Mono<ServerResponse> getClients(ServerRequest request) {
        return null;
    }

    public Mono<ServerResponse> getClientById(ServerRequest request) {
        return null;
    }

    public Mono<ServerResponse> getClientContacts(ServerRequest request) {
        return null;
    }

    public Mono<ServerResponse> addClient(ServerRequest request) {
        return null;
    }

    public Mono<ServerResponse> changeClient(ServerRequest request) {
        return null;
    }

    public Mono<ServerResponse> deleteClient(ServerRequest request) {
        return null;
    }

    public Mono<ServerResponse> deleteClientContact(ServerRequest request) {
        return null;
    }


}
