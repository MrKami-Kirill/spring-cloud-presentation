package spring.cloud.util;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import lombok.experimental.UtilityClass;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@UtilityClass
public class MonoHelper {

    public static <T> Mono<Optional<T>> optionalMono(Mono<T> mono, Consumer<Throwable> errorConsumer) {
        return mono.onErrorResume(t -> {
            errorConsumer.accept(t);
            return Mono.empty();
        }).map(Optional::of).switchIfEmpty(Mono.just(Optional.empty()));
    }

    public static <T> Mono<Optional<T>> optionalMono(Mono<T> mono) {
        return optionalMono(mono, v -> {
        });
    }

    public static <T, V> Mono<Optional<T>> optionalMono(V value, Function<V, Mono<T>> monoFunction,
                                                        Consumer<Throwable> errorConsumer) {
        return optionalMono(Optional.ofNullable(value).map(monoFunction).orElse(Mono.empty()), errorConsumer);
    }

    public static <T, V> Mono<Optional<T>> optionalMono(V value, Function<V, Mono<T>> monoFunction) {
        return optionalMono(Optional.ofNullable(value).map(monoFunction).orElse(Mono.empty()));
    }

    public static <T> Mono<Optional<T>> optionalMono(T value) {
        return Mono.just(Optional.ofNullable(value));
    }

    public static <T> Mono<Optional<List<T>>> optionalMonoFromFlux(Flux<T> flux) {
        return flux.collectList()
                .map(list -> CollectionUtils.isEmpty(list)
                        ? Optional.empty()
                        : Optional.of(list));
    }

    public static <T> Mono<Optional<T>> emptyMono() {
        return Mono.just(Optional.empty());
    }

    public static <T> Mono<T> emptyIfFalse(boolean condition, Mono<T> mono) {
        return condition ? mono : Mono.empty();
    }

}
