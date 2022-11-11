package spring.cloud.model.dto.response;

import java.util.List;
import spring.cloud.model.dto.response.component.Contact;

public record ClientContactsResponse(List<Contact> data) {

}
