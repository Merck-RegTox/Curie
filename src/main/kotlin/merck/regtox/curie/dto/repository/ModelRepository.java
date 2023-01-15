package merck.regtox.curie.dto.repository;

import merck.regtox.curie.dto.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRepository extends JpaRepository<Model, Long> {
}
