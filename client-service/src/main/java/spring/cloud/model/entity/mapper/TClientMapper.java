package spring.cloud.model.entity.mapper;

import java.time.LocalDate;
import spring.cloud.model.dto.request.AddClient;
import spring.cloud.model.dto.request.ChangeClient;
import spring.cloud.model.entity.TClient;

public class TClientMapper {

    public static TClient fromAddClientDto(AddClient addClient) {
        return TClient.builder()
                .surName(addClient.surName())
                .firstName(addClient.firstName())
                .middleName(addClient.middleName())
                .birthDay(addClient.birthDay())
                .build();
    }

    public static TClient fromChangeClientDto(TClient tClient, ChangeClient changeClient) {
        String surName = changeClient.surName();
        if (surName != null && !surName.isBlank()) {
            tClient.setSurName(surName);
        }

        String firstName = changeClient.firstName();
        if (firstName != null && !firstName.isBlank()) {
            tClient.setFirstName(firstName);
        }
        String middleName = changeClient.middleName();
        if (middleName != null && !middleName.isBlank()) {
            tClient.setMiddleName(middleName);
        }
        LocalDate birthDate = changeClient.birthDay();
        if (birthDate != null) {
            tClient.setBirthDay(birthDate);
        }

        return tClient;
    }

}
