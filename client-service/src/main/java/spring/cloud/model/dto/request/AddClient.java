package spring.cloud.model.dto.request;

import static spring.cloud.util.Constants.BIRTHDAY_DATE_FORMAT;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record AddClient(@NotBlank String surName, @NotBlank String firstName, String middleName,
                        @NotNull @JsonFormat(pattern = BIRTHDAY_DATE_FORMAT, shape = JsonFormat.Shape.STRING)
                        LocalDate birthDay) {

}
