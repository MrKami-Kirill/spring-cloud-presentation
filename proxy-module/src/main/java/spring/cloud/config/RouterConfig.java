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
import spring.cloud.config.property.ProxyModuleProperty;
import spring.cloud.handler.RouterHandler;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> router(RouterHandler routerHandler,
                                                 ProxyModuleProperty proxyModuleProperty) {
        return nest(accept(APPLICATION_JSON), nest(path(proxyModuleProperty.getAdditional()),
                route(GET(proxyModuleProperty.getClientsPath()), routerHandler::getClients)
                        .andRoute(GET(proxyModuleProperty.getClientByIdPath()), routerHandler::getClientById)
                        .andRoute(GET(proxyModuleProperty.getClientContactsPath()),
                                routerHandler::getClientContacts)
                        .andRoute(POST(proxyModuleProperty.getAddClientPath()),
                                routerHandler::addClient)
                        .andRoute(PUT(proxyModuleProperty.getChangeClientPath()),
                                routerHandler::changeClient)
                        .andRoute(POST(proxyModuleProperty.getAddContactToClientPath()),
                                routerHandler::addContactToClient)
                        .andRoute(DELETE(proxyModuleProperty.getDeleteClientPath()),
                                routerHandler::deleteClient)
                        .andRoute(DELETE(proxyModuleProperty.getDeleteClientContactPath()),
                                routerHandler::deleteClientContact)));
    }

}
