package merck.regtox.curie.api

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["api/v1/prediction"])
class PredictionApi {
    fun getAllPredictions() {

    }

    fun deletePredictions() {

    }

    fun addPredictions() {

    }

    fun getPredictionsByChemical() {

    }
}

data class PredictionObj(val chemical_id: Long,
                         val model_id: Long,
                         val prediction: String,
                         val reliability: String)