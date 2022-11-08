package spring.cloud.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import spring.cloud.util.ErrorResponse;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WebClientException extends RuntimeException {

    private final ErrorResponse body;
    private final HttpStatus httpStatus;

}
