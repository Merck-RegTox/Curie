package merck.regtox.curie.api

import jakarta.persistence.EntityExistsException
import merck.regtox.curie.buissnesLogic.ChemicalLogic
import merck.regtox.curie.dto.Chemical
import merck.regtox.curie.dto.Model
import merck.regtox.curie.dto.Prediction
import merck.regtox.curie.dto.Software
import merck.regtox.curie.dto.repository.ModelRepository
import merck.regtox.curie.dto.repository.PredictionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@CrossOrigin(origins = ["http://localhost:3000"], exposedHeaders = ["x-total-count"])
@RequestMapping(path = ["api/v1/prediction"])
class PredictionApi(@Autowired val predictionRepository: PredictionRepository,
                    @Autowired val chemicalLogic: ChemicalLogic ) {

    val repository = predictionRepository

    @GetMapping("")
    fun getAllPredictions(
        @RequestParam(value = "page", required = false, defaultValue = "0") page: Int,
        @RequestParam(value = "pageSize", required = false, defaultValue = "100") pageSize: Int,
        @RequestParam(value = "sort", required = false, defaultValue = "id") sort: String,
        @RequestParam(value = "order", required = false, defaultValue = "ASC") order: Sort.Direction,
        @ModelAttribute filter: Prediction,
        @RequestParam(value = "id", required = false) ids: List<Long>?
    ): ResponseEntity<List<Prediction>> {
        if(ids == null) {
            val example = Example.of(
                filter,
                ExampleMatcher.matching().withIgnoreNullValues().withIgnoreCase()
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
            )
            val sort = Sort.by(order, sort)
            val paging = PageRequest.of(page, pageSize, sort)
            val data = repository.findAll(example, paging)
            return ResponseEntity.ok()
                .header("Access-Control-Exposed-Headers", "x-total-count")
                .header("x-total-count", data.totalElements.toString())
                .body(data.toList())
        }
        val data = repository.findAllById(ids)
        return ResponseEntity.ok()
            .header("Access-Control-Exposed-Headers", "x-total-count")
            .header("x-total-count", data.size.toString())
            .body(data.toList())
    }

    @PutMapping()
    fun updateSoftware(
        @RequestParam(value = "id") id: Long,
        @RequestBody obj: PredictionDto
    ): Prediction? {
        val toUpdate = repository.findById(id)
        if (toUpdate.isEmpty) {
            return null
        }
        val newChem = chemicalLogic.createChemical(obj.cas, obj.smile)
        toUpdate.get().apply {
            cid = newChem.id
            mid = obj.model_id
            prediction_raw = obj.prediction
            reliability = obj.reliability
        }
        repository.save(toUpdate.get())
        return toUpdate.get()

    }

    @PostMapping
    fun createPrediction(@RequestBody it: PredictionDto): Prediction?{
        if (repository.existsById(it.model_id) ) {
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
                    return predictionRepository.save(newPrediction)
                }

            }
        }
        return null
    }

    @DeleteMapping()
    fun deleteSoftware(@RequestParam id: Long): Prediction? {
        val toDelete = repository.findById(id)
        repository.deleteById(id)
        return toDelete.get()
    }
}



data class PredictionDto(val cas: String,
                         val smile: String,
                         val model_id: Long,
                         val prediction: String,
                         val reliability: String)
