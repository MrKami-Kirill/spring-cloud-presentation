package spring.cloud.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import spring.cloud.model.entity.TContact;

@Repository
public interface TContactRepository extends ReactiveCrudRepository<TContact, Long> {

    Flux<TContact> findByClientId(Long clientId);

}
