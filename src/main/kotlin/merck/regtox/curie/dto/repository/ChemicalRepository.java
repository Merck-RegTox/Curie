package merck.regtox.curie.dto.repository;


import merck.regtox.curie.dto.Chemical;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChemicalRepository extends JpaRepository<Chemical, Long> {
    public boolean existsByCas(String cas);
    public boolean existsBySmile(String smile);
    public Optional<Chemical> findBySmile(String smile);
    public List<Chemical> findAllByCas(String smile, PageRequest pageRequest);

}
