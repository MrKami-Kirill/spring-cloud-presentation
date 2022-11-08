package spring.cloud.handler;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.RequestPredicates.all;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.status;
import static ru.rtech.util.Constants.ERROR_MAP_KEY;
import static ru.rtech.util.Constants.MESSAGE_MAP_KEY;
import static ru.rtech.util.Constants.STACK_TRACE_MAP_KEY;
import static ru.rtech.util.Constants.STATUS_MAP_KEY;
import static ru.rtech.util.HttpUtil.headers;
import static ru.rtech.util.HttpUtil.requestIdHeaderMap;

import java.util.Map;
import org.springframework.boot.autoconfigure.web.WebProperties.Resources;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.rtech.handler.component.GlobalErrorAttributes;
import ru.rtech.util.ErrorResponse;

@Component
@Order(-2)
public class GlobalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {

    public GlobalErrorWebExceptionHandler(GlobalErrorAttributes errorAttributes, ApplicationContext applicationContext,
                                          ServerCodecConfigurer serverCodecConfigurer) {
        super(errorAttributes, new Resources(), applicationContext);
        super.setMessageWriters(serverCodecConfigurer.getWriters());
        super.setMessageReaders(serverCodecConfigurer.getReaders());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(final ErrorAttributes errorAttributes) {
        return route(all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(final ServerRequest request) {
        final Map<String, Object> map = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        return status((HttpStatus) map.get(STATUS_MAP_KEY)).contentType(APPLICATION_JSON)
                .headers(headers(requestIdHeaderMap(request))).body(fromValue(createErrorResponse(map)));
    }

    private ErrorResponse createErrorResponse(Map<String, Object> map) {
        return new ErrorResponse((String) map.get(ERROR_MAP_KEY), (String) map.get(MESSAGE_MAP_KEY),
                (String) map.get(STACK_TRACE_MAP_KEY));
    }

}
