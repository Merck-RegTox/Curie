package merck.regtox.curie.api

import merck.regtox.curie.buissnesLogic.ChemicalLogic
import merck.regtox.curie.dto.Chemical
import merck.regtox.curie.dto.Prediction
import merck.regtox.curie.dto.repository.ModelRepository
import merck.regtox.curie.dto.repository.PredictionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["api/v1/prediction"])
class PredictionApi(@Autowired val predictionRepository: PredictionRepository,
                    @Autowired val chemicalLogic: ChemicalLogic,
                    @Autowired val modelRepository: ModelRepository) {
    @GetMapping
    fun getAllPredictions(@RequestParam(value = "page") page: Int,
                          @RequestParam(value = "pageSize") pageSize: Int,
                          @RequestParam(value="cas", required = false) cas: String?,
                          @RequestParam(value="smile", required = false) smile: String?,
                          @RequestParam(value="model_Id", required = false) mid: Long?): List<Prediction> {
        val cid = chemicalLogic.getChemicalId(cas,smile)

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

    @PostMapping
    fun addPredictions(@RequestBody predictionsToCreate: PredictionsObj): Long{
        var createdPredictions = 0L
        predictionsToCreate.predictions.forEach {
            if ( modelRepository.existsById(it.model_id) ) {
                chemicalLogic.createChemical(it.cas.trim().lowercase(), it.smile.trim().lowercase())
                val newCid: Long? = chemicalLogic.getChemicalId(it.cas.trim().lowercase(), it.smile.trim().lowercase())
                if (newCid != null) {
                    val newPrediction = Prediction().apply {
                        cid = newCid
                        reliability_raw = it.reliability
                        reliability = null
                        prediction_raw = it.prediction
                        prediction = null
                        mid = it.model_id
                    }
                    if (!predictionRepository.existsByCidAndMid(newCid, it.model_id)) {
                        predictionRepository.save(newPrediction)
                        createdPredictions += 1
                    }

                }
            }
        }
        return createdPredictions
    }
}

data class PredictionObj(val cas: String,
                         val smile: String,
                         val model_id: Long,
                         val prediction: String,
                         val reliability: String)

data class PredictionsObj(val predictions: List<PredictionObj>)

data class PredictionsIdObj(val ids: List<Long>)