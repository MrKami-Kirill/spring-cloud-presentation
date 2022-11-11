package spring.cloud.model.dto.response.component;

import java.time.LocalDate;

public record Client(Long id, String surName, String firstName, String middleName, LocalDate birthDay) {

}
