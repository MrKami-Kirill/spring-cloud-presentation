package spring.cloud.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ValidationHandler<T> {

    private final Validator validator;

    public final Mono<T> validate(Mono<T> object, Class<T> validationClass) {
        return object.flatMap(body -> {
            Errors errors = new BeanPropertyBindingResult(body, validationClass.getName());
            validator.validate(body, errors);
            if (!errors.getAllErrors().isEmpty()) {
                return Mono.error(new ValidationException(errors.getAllErrors().toString()));
            } else {
                return Mono.just(body);
            }
        });
    }

}
