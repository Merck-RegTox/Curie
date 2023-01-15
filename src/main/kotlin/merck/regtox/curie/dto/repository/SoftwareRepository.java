package merck.regtox.curie.dto.repository;

import merck.regtox.curie.dto.Software;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface SoftwareRepository extends JpaRepository<Software, Long> {
    Boolean existsByName(String name);
}
