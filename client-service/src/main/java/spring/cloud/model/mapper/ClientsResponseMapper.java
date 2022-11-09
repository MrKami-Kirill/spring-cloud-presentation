package spring.cloud.model.mapper;

import java.util.List;
import spring.cloud.model.dto.response.ClientsResponse;
import spring.cloud.model.entity.TClient;

public class ClientsResponseMapper {

    public static ClientsResponse toDto(List<TClient> list) {
        return ClientsResponse.builder()
                .data(list)
                .build();
    }

}
