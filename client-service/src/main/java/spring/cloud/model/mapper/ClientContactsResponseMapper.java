package spring.cloud.model.mapper;

import java.util.List;
import spring.cloud.model.dto.response.ClientContactsResponse;
import spring.cloud.model.entity.TContact;

public class ClientContactsResponseMapper {

    public static ClientContactsResponse toDto(List<TContact> data) {
        return ClientContactsResponse.builder()
                .data(data)
                .build();
    }

    public static ClientContactsResponse emptyDto() {
        return ClientContactsResponse.builder()
                .build();
    }

}
