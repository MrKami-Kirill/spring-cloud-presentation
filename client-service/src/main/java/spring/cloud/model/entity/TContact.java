package spring.cloud.model.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String value;
    private ContactType contactType;
    @Column(name = "tclient_id")
    private Long clientId;

}
