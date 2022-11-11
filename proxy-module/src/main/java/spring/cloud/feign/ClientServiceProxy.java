package spring.cloud.feign;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "client-service")
public interface ClientServiceProxy {

    @RequestMapping(method = RequestMethod.GET, value = "/client-service/clients")
    Mono<String> getClients();

    @RequestMapping(method = RequestMethod.GET, value = "/client-service/clients/{clientId}")
    Mono<String> getClientById(@PathVariable("clientId") String clientId);

}
