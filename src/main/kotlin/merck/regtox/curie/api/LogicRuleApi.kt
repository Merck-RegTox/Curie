package merck.regtox.curie.api

import jakarta.persistence.EntityExistsException
import jakarta.persistence.EntityNotFoundException
import jakarta.websocket.server.PathParam
import merck.regtox.curie.dto.LogicRule
import merck.regtox.curie.dto.Model
import merck.regtox.curie.dto.repository.LogicRuleRepository
import merck.regtox.curie.dto.repository.ModelRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping(path = ["api/v1/logicRule"])
class LogicRuleApi(
    @Autowired val logicRuleRepository: LogicRuleRepository,
    @Autowired val modelRepository: ModelRepository
) {
    @GetMapping("")
    fun getAllLogicRules(@RequestParam(value = "page") page: Int, @RequestParam(value = "pageSize") pageSize: Int): Iterable<LogicRule> {
        return logicRuleRepository.findAll(PageRequest.of(page, pageSize)).toList()
    }

    @GetMapping("model")
    fun getLogicRulesByModel(@RequestParam(value="model_Id") modelId: Long,
                             @RequestParam(value = "page") page: Int,
                             @RequestParam(value = "pageSize") pageSize: Int, ): Iterable<LogicRule> {
        val model: Optional<Model> = modelRepository.findById(modelId)
        if (model.isEmpty) {
            return listOf()
        }
        return logicRuleRepository.findByModelContaining(model.get(),PageRequest.of(page, pageSize)).toList()
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

    @DeleteMapping(path=["remove"], consumes=[MediaType.APPLICATION_JSON_VALUE], produces=[MediaType.APPLICATION_JSON_VALUE])
    fun deleteLogicRules(@RequestBody id: LogicRuleDeletionObj): Iterable<LogicRule> {
        val deleted: MutableList<LogicRule> = mutableListOf()
        id.ids.forEach {
            val rule = logicRuleRepository.findById(it)
            if (!rule.isEmpty) {
                logicRuleRepository.delete(rule.get())
                deleted.add(rule.get())
            }
        }
        return deleted
    }
}

data class LogicRuleObj(val raw: String,
                        val mapped: String,
                        val modelId: Long,
                        val isReliability: Boolean
                        )

data class LogicRuleDeletionObj(val ids: List<Long>)