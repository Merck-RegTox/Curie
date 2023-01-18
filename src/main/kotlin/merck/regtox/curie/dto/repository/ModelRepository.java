package merck.regtox.curie.dto.repository;

import merck.regtox.curie.dto.Endpoint;
import merck.regtox.curie.dto.Model;
import merck.regtox.curie.dto.Software;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModelRepository extends JpaRepository<Model, Long> {
    Boolean existsByNameAndEndpointIdAndSoftwareId(String name, Long endpointId, Long softwareId);
    List<Model> findModelByEndpointContaining(Endpoint endpoint);
    List<Model> findModelBySoftwareContaining(Software software);

}
