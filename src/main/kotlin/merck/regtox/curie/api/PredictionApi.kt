package merck.regtox.curie.api

import merck.regtox.curie.buissnesLogic.ChemicalLogic
import merck.regtox.curie.dto.Prediction
import merck.regtox.curie.dto.repository.ChemicalRepository
import merck.regtox.curie.dto.repository.PredictionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["api/v1/prediction"])
class PredictionApi(@Autowired val predictionRepository: PredictionRepository,
                    @Autowired val chemicalLogic: ChemicalLogic) {
    @GetMapping
    fun getAllPredictions(@RequestParam(value = "page") page: Int,
                          @RequestParam(value = "pageSize") pageSize: Int,
                          @RequestParam(value="cas", required = false) cas: String?,
                          @RequestParam(value="smile", required = false) smile: String?,
                          @RequestParam(value="model_Id", required = false) mid: Long?): List<Prediction> {
        val cid = chemicalLogic.getChemicalIdOrCreateNew(cas,smile)

        val example: Prediction = Prediction().apply {
            this.mid = mid
            this.cid = cid
        }
        return predictionRepository.findAll(Example.of(example), PageRequest.of(page, pageSize)).toList()
    }

    @DeleteMapping
    fun deletePredictions(@RequestBody toDelete: PredictionsIdObj): Unit {
        return predictionRepository.deleteAllById(toDelete.ids)
    }

    fun addPredictions() {
        throw NotImplementedError()
    }

    fun getPredictionsByChemical() {
        throw NotImplementedError()
    }
}

data class PredictionObj(val cas: String,
                         val smile: String,
                         val model_id: Long,
                         val prediction: String,
                         val reliability: String)

data class PredictionsIdObj(val ids: List<Long>)