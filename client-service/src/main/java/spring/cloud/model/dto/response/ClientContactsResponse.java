package spring.cloud.model.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Value;
import spring.cloud.model.entity.TContact;

@Value
@Builder
public class ClientContactsResponse {

    List<TContact> data;

}
