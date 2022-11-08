package spring.cloud.handler.component;

import static spring.cloud.util.Constants.ERROR_MAP_KEY;
import static spring.cloud.util.Constants.MESSAGE_MAP_KEY;
import static spring.cloud.util.Constants.STACK_TRACE_MAP_KEY;
import static spring.cloud.util.Constants.STATUS_MAP_KEY;
import static spring.cloud.util.Error.EX_NOT_FOUND;
import static spring.cloud.util.Error.EX_SYSTEM_ERROR;
import static spring.cloud.util.Error.EX_VALIDATION_ERROR;

import java.util.Arrays;
import java.util.Map;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;
import spring.cloud.exception.ValidationException;
import spring.cloud.exception.WebClientException;
import spring.cloud.util.ErrorResponse;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request,
                                                  ErrorAttributeOptions options) {
        Map<String, Object> map = super.getErrorAttributes(request, options);
        Throwable throwable = getError(request);
        if (throwable instanceof WebClientException exception) {
            ErrorResponse body = exception.getBody();
            setErrorAttributeMap(map, body.getError(), body.getMessage(), body.getStackTrace(),
                    exception.getHttpStatus());
        } else if (throwable instanceof ValidationException exception) {
            setErrorAttributeMap(map, EX_VALIDATION_ERROR.name(), HttpStatus.BAD_REQUEST, exception);
        } else if (throwable instanceof ResponseStatusException exception) {
            setErrorAttributeMap(map, EX_NOT_FOUND.name(), exception.getStatus(), exception);
        } else {
            setErrorAttributeMap(map, EX_SYSTEM_ERROR.name(), HttpStatus.INTERNAL_SERVER_ERROR, throwable);
        }

        return map;
    }

    private void setErrorAttributeMap(Map<String, Object> map, String error, HttpStatus httpStatus,
                                      Throwable throwable) {
        setErrorAttributeMap(map, error, throwable.getMessage(), Arrays.toString(throwable.getStackTrace()),
                httpStatus);
    }

    private void setErrorAttributeMap(Map<String, Object> map, String error, String message, String stackTrace,
                                      HttpStatus httpStatus) {
        map.put(ERROR_MAP_KEY, error);
        map.put(MESSAGE_MAP_KEY, message);
        map.put(STACK_TRACE_MAP_KEY, stackTrace);
        map.put(STATUS_MAP_KEY, httpStatus);
    }

}
