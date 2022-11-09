package spring.cloud.model.entity;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table("tclient")
public class TClient {

    @Id
    private Long id;
    private String surName;
    private String firstName;
    private String middleName;
    private LocalDate birthDay;

}
