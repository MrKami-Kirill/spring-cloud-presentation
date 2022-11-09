package spring.cloud.model.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import spring.cloud.model.entity.component.ContactType;

@Value
@Builder
public class AddContact {

    @NotBlank
    String value;
    @NotNull
    ContactType type;

}
