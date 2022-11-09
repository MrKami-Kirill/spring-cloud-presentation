package spring.cloud.handler;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import spring.cloud.feign.ClientServiceProxy;

@Component
@RequiredArgsConstructor
public class RouterHandler {

    private final ClientServiceProxy clientServiceProxy;

    public Mono<ServerResponse> getClients(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                .body(fromPublisher(clientServiceProxy.getClients(), String.class));
    }

    public Mono<ServerResponse> getClientById(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                .body(fromPublisher(clientServiceProxy.getClientById(request.pathVariable("clientId")),
                        String.class));
    }

}
