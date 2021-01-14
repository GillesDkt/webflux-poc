package net.vince.webfluxpoc.repositories;

import net.vince.webfluxpoc.domain.Configuration;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository extends ReactiveCrudRepository<Configuration, String> {

}
