package spring.cloud.model.dto.response;

import java.util.List;
import spring.cloud.model.dto.response.component.Client;

public record ClientsResponse(List<Client> data) {

}
