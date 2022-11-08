package spring.cloud.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import spring.cloud.model.entity.TClient;

@Repository
public interface TClientRepository extends ReactiveCrudRepository<TClient, Long> {

}
