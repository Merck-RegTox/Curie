package merck.regtox.curie.api

import jakarta.persistence.EntityExistsException
import jakarta.persistence.EntityNotFoundException
import merck.regtox.curie.dto.Model
import merck.regtox.curie.dto.Software
import merck.regtox.curie.dto.repository.EndpointRepository
import merck.regtox.curie.dto.repository.ModelRepository
import merck.regtox.curie.dto.repository.SoftwareRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["http://localhost:3000"], exposedHeaders = ["x-total-count"])
@RequestMapping(path = ["api/v1/model"])
class ModelApi(@Autowired val modelRepository: ModelRepository) {

    val repository = modelRepository

    @GetMapping("")
    fun getAllModels(
        @RequestParam(value = "page", required = false, defaultValue = "0") page: Int,
        @RequestParam(value = "pageSize", required = false, defaultValue = "100") pageSize: Int,
        @RequestParam(value = "sort", required = false, defaultValue = "id") sort: String,
        @RequestParam(value = "order", required = false, defaultValue = "ASC") order: Sort.Direction,
        @ModelAttribute filter: Model,
        @RequestParam(value = "id", required = false) ids: List<Long>?
    ): ResponseEntity<List<Model>> {
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

    @PostMapping()
    fun createSoftware(@RequestBody objToCreate: ModelDto): Model {
        if (repository.existsByNameAndEidAndSid(objToCreate.name, objToCreate.endpoint_id, objToCreate.software_id )) {
            throw EntityExistsException("Model with name ${objToCreate.name} already exists")
        }
        val newModel = Model().apply {
            name = objToCreate.name
        }
        return repository.save(newModel)
    }

    @PutMapping()
    fun updateSoftware(
        @RequestParam(value = "id") id: Long,
        @RequestBody obj: SoftwareDto
    ): Model? {
        val toUpdate = repository.findById(id)
        if (toUpdate.isEmpty) {
            return null
        }
        toUpdate.get().apply {
            name = obj.name
        }
        repository.save(toUpdate.get())
        return toUpdate.get()

    }

    @DeleteMapping()
    fun deleteSoftware(@RequestParam id: Long): Model? {
        val toDelete = repository.findById(id)
        repository.deleteById(id)
        return toDelete.get()
    }
    data class ModelDto(
        val name: String,
        val endpoint_id: Long,
        val software_id: Long
    )
}