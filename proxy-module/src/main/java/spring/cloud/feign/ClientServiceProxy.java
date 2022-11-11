package spring.cloud.feign;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;
import spring.cloud.model.dto.request.AddClient;
import spring.cloud.model.dto.request.AddContact;
import spring.cloud.model.dto.request.ChangeClient;
import spring.cloud.model.dto.response.ClientContactsResponse;
import spring.cloud.model.dto.response.ClientResponse;
import spring.cloud.model.dto.response.ClientsResponse;

@ReactiveFeignClient(name = "client-service")
public interface ClientServiceProxy {

    @RequestMapping(method = RequestMethod.GET, value = "/client-service/clients")
    Mono<ClientsResponse> getClients();

    @RequestMapping(method = RequestMethod.GET, value = "/client-service/clients/{clientId}")
    Mono<ClientResponse> getClientById(@PathVariable("clientId") String clientId);

    @RequestMapping(method = RequestMethod.GET, value = "/client-service/clients/{clientId}/contacts")
    Mono<ClientContactsResponse> getClientContacts(@PathVariable("clientId") String clientId);

    @RequestMapping(method = RequestMethod.POST, value = "/client-service/clients")
    Mono<Long> addClient(@RequestBody Mono<AddClient> addClient);

    @RequestMapping(method = RequestMethod.PUT, value = "/client-service/clients/{clientId}")
    Mono<ClientResponse> changeClient(@PathVariable("clientId") String clientId,
                                      @RequestBody Mono<ChangeClient> client);

    @RequestMapping(method = RequestMethod.POST, value = "/client-service/clients/{clientId}/contacts")
    Mono<Long> addContactToClient(@PathVariable("clientId") String clientId,
                                            @RequestBody Mono<AddContact> addContact);

    @RequestMapping(method = RequestMethod.DELETE, value = "/client-service/clients/{clientId}")
    Mono<Void> deleteClient(@PathVariable("clientId") String clientId);

    @RequestMapping(method = RequestMethod.DELETE, value = "/client-service/clients/{clientId}/contacts/{contactId}")
    Mono<Void> deleteClientContact(@PathVariable("clientId") String clientId,
                                   @PathVariable("contactId") String contactId);

}
