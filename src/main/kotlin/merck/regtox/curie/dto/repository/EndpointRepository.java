package merck.regtox.curie.dto.repository;

import merck.regtox.curie.dto.Endpoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EndpointRepository extends JpaRepository<Endpoint, Long> {
    Boolean existsByName(String name);
    Optional<Endpoint> findByName(String name);
}
