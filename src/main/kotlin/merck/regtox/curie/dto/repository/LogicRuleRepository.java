package merck.regtox.curie.dto.repository;

import merck.regtox.curie.dto.LogicRule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;

public interface LogicRuleRepository extends JpaRepository<LogicRule, Long> {
    Page<LogicRule> findByModelContaining(Long modelId, PageRequest pageable);

    Boolean existsByRawAndMappedAndIsReliability(String raw, String mapped, Boolean IsReliability);
}
