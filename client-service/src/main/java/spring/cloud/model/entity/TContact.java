package spring.cloud.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import spring.cloud.model.entity.component.ContactType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table("tcontact")
public class TContact {

    @Id
    private Long id;
    private String value;
    private ContactType contactType;
    @Column("tClient_id")
    private Long clientId;

}
