package spring.cloud.model.dto.request;

import static spring.cloud.util.Constants.BIRTHDAY_DATE_FORMAT;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public record ChangeClient(String surName, String firstName, String middleName,
                           @JsonFormat(pattern = BIRTHDAY_DATE_FORMAT, shape = JsonFormat.Shape.STRING)
                           LocalDate birthDay) {

}
