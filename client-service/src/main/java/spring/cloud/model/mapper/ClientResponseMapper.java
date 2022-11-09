package spring.cloud.model.mapper;

import spring.cloud.model.dto.response.ClientResponse;
import spring.cloud.model.entity.TClient;

public class ClientResponseMapper {

    public static ClientResponse toDto(TClient data) {
        return ClientResponse.builder()
                .data(data)
                .build();
    }

}
