package merck.regtox.curie.dto.repository;

import merck.regtox.curie.dto.Prediction;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PredictionRepository extends JpaRepository<Prediction, Long> {
    Boolean existsByCidAndMid(Long cid, Long mid);
}
