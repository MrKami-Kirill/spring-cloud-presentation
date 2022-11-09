package spring.cloud.model.entity;

import java.time.LocalDate;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import spring.cloud.model.dto.request.AddClient;

@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table("tcontact")
public class TClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String surName;
    private String firstName;
    private String middleName;
    private LocalDate birthDay;

    public TClient(AddClient addClient) {
        this.surName = addClient.getSurName();
        this.firstName = addClient.getFirstName();
        this.middleName = addClient.getMiddleName();
        this.birthDay = addClient.getBirthDay();
    }

}
