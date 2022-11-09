package spring.cloud.model.entity.mapper;

import java.time.LocalDate;
import spring.cloud.model.dto.request.AddClient;
import spring.cloud.model.dto.request.ChangeClient;
import spring.cloud.model.entity.TClient;

public class TClientMapper {

    public static TClient fromAddClientDto(AddClient addClient) {
        return TClient.builder()
                .surName(addClient.getSurName())
                .firstName(addClient.getFirstName())
                .middleName(addClient.getMiddleName())
                .birthDay(addClient.getBirthDay())
                .build();
    }

    public static TClient fromChangeClientDto(TClient tClient, ChangeClient changeClient) {
        String surName = changeClient.getSurName();
        String firstName = changeClient.getFirstName();
        String middleName = changeClient.getMiddleName();
        LocalDate birthDate = changeClient.getBirthDay();
        if (surName != null && !surName.isBlank()) tClient.setSurName(surName);
        if (firstName != null && !firstName.isBlank()) tClient.setFirstName(firstName);
        if (middleName != null && !middleName.isBlank()) tClient.setMiddleName(middleName);
        if (birthDate != null) tClient.setBirthDay(birthDate);
        return tClient;
    }

}
