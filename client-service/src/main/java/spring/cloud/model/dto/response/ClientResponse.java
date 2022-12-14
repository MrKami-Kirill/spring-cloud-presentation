package spring.cloud.model.dto.response;

import lombok.Builder;
import lombok.Value;
import spring.cloud.model.entity.TClient;

@Value
@Builder
public class ClientResponse {

    TClient data;

}
