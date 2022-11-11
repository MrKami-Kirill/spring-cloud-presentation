package spring.cloud.model.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import spring.cloud.model.dto.request.component.ContactType;

public record AddContact(@NotBlank String value, @NotNull ContactType type) {

}
