package merck.regtox.curie.api

import jakarta.persistence.EntityExistsException
import jakarta.persistence.EntityNotFoundException
import merck.regtox.curie.dto.LogicRule
import merck.regtox.curie.dto.repository.LogicRuleRepository
import merck.regtox.curie.dto.repository.ModelRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["api/v1/logicRule"])
class LogicRuleApi(
    @Autowired val logicRuleRepository: LogicRuleRepository,
    @Autowired val modelRepository: ModelRepository
) {
    @GetMapping("")
    fun getAllLogicRules(@RequestParam(value = "0") page: Int, @RequestParam(value = "50") pageSize: Int): Iterable<LogicRule> {
        return logicRuleRepository.findAll(PageRequest.of(page, pageSize)).toList()
    }

    @PostMapping(path=["add"], consumes=[MediaType.APPLICATION_JSON_VALUE], produces=[MediaType.APPLICATION_JSON_VALUE])
    fun createLogicRules(@RequestBody logicRules: List<LogicRuleObj>): List<LogicRuleObj> {
        val validRules = logicRules.filter {
            val raw = it.raw.trim().lowercase()
            val mapped = it.mapped.trim().lowercase()
            val isReliability = it.isReliability
            val modelId = it.modelId
            logicRuleRepository.existsByRawAndMappedAndIsReliability(raw, mapped, isReliability) && modelRepository.existsById(modelId)
        }
        validRules.forEach{
            val raw = it.raw.trim().lowercase()
            val mapped = it.mapped.trim().lowercase()
            val isReliability = it.isReliability
            val modelId = it.modelId
            val model = modelRepository.findById(modelId).get()
            logicRuleRepository.save(LogicRule(model, raw, mapped, isReliability))
        }
        return validRules
    }

    @DeleteMapping("remove/{id}")
    fun deleteLogicRule(@PathVariable("id") id: Long) {
        logicRuleRepository.deleteById(id)
    }
}

data class LogicRuleObj(val raw: String,
                        val mapped: String,
                        val modelId: Long,
                        val isReliability: Boolean
                        )