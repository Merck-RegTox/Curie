package merck.regtox.curie.dto.repository;

import merck.regtox.curie.dto.Endpoint;
import merck.regtox.curie.dto.Model;
import merck.regtox.curie.dto.Software;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRepository extends JpaRepository<Model, Long> {
    Boolean existsByNameAndEndpointIdAndSoftwareId(String name, Long endpointId, Long softwareId);
    Page<Model> findByEndpointContainingOrSoftwareContaining(Endpoint endpoint, Software software, PageRequest pageRequest);
}
