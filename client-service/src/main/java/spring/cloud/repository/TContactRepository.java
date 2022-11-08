package spring.cloud.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import spring.cloud.model.entity.TContact;

@Repository
public interface TContactRepository extends ReactiveCrudRepository<TContact, Long> {

}
