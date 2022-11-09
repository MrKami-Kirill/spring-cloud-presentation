package spring.cloud.model.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Value;
import spring.cloud.model.entity.TClient;

@Value
@Builder
public class ClientsResponse {

    List<TClient> data;
}
