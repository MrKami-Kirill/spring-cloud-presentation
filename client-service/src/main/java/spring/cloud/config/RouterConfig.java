package spring.cloud.config;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import spring.cloud.config.property.ClientServiceProperty;
import spring.cloud.handler.RouterHandler;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> router(RouterHandler routerHandler,
                                                 ClientServiceProperty clientServiceProperty) {
        return nest(accept(APPLICATION_JSON), nest(path(clientServiceProperty.getAdditional()),
                route(GET(clientServiceProperty.getClientsPath()), routerHandler::getClients)
                        .andRoute(GET(clientServiceProperty.getClientByIdPath()),
                                routerHandler::getClientById)
                        .andRoute(GET(clientServiceProperty.getClientContactsPath()),
                                routerHandler::getClientContacts)
                        .andRoute(POST(clientServiceProperty.getAddClientPath()), routerHandler::addClient)
                        .andRoute(PUT(clientServiceProperty.getChangeClientPath()), routerHandler::changeClient)
                        .andRoute(DELETE(clientServiceProperty.getDeleteClientPath()), routerHandler::deleteClient)
                        .andRoute(DELETE(clientServiceProperty.getDeleteClientContactPath()),
                                routerHandler::deleteClientContact)));
    }

}
