package spring.cloud.model.entity.mapper;

import spring.cloud.model.dto.request.AddContact;
import spring.cloud.model.entity.TContact;

public class TContactMapper {

    public static TContact fromAddContactDto(AddContact addContact, Long clientId) {
        return TContact.builder()
                .value(addContact.value())
                .contactType(addContact.type())
                .clientId(clientId)
                .build();
    }

}
