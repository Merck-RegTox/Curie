package merck.regtox.curie.api

import jakarta.persistence.EntityNotFoundException
import merck.regtox.curie.dto.Model
import merck.regtox.curie.dto.Software
import merck.regtox.curie.dto.repository.EndpointRepository
import merck.regtox.curie.dto.repository.ModelRepository
import merck.regtox.curie.dto.repository.SoftwareRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["api/v1/model"])
class ModelApi(@Autowired val endpointRepository: EndpointRepository,
               @Autowired val softwareRepository: SoftwareRepository,
               @Autowired val modelRepository: ModelRepository) {

    @GetMapping
    fun getModels(@RequestParam(value = "page") page: Int, @RequestParam(value = "pageSize") pageSize: Int): List<Model> {
        return modelRepository.findAll(PageRequest.of(page, pageSize)).toList()
    }
    @GetMapping("endpoint")
    fun getModelsByEndpoint(@RequestParam(value = "page") page: Int,
                            @RequestParam(value = "pageSize") pageSize: Int,
                            @RequestParam(value = "endpoint_Id") endpointId: Long): List<Model> {
        val endpoint = endpointRepository.findById(endpointId)
        if (endpoint.isEmpty) {
           throw EntityNotFoundException("Endpoint with id: $endpoint does not exist")
        }
        return modelRepository.findModelByEid(endpoint.get().id)
    }

    @GetMapping("software")
    fun getModelsBySoftware(@RequestParam(value = "page") page: Int,
                            @RequestParam(value = "pageSize") pageSize: Int,
                            @RequestParam(value = "software_Id") softwareId: Long): List<Model> {
        val software = softwareRepository.findById(softwareId)
        if (software.isEmpty) {
            throw EntityNotFoundException("Endpoint with id: $softwareId does not exist")
        }
        val foundSoftware = software.get()
        return modelRepository.findModelBySid(foundSoftware.id)
    }

    @DeleteMapping(
            path = ["/remove"],
            consumes = [MediaType.APPLICATION_JSON_VALUE],
            produces = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteModelsById(@RequestBody  obj: ModelDeletionObj): List<Model> {
        val deletedModels = mutableListOf<Model>()
        obj.modelIds.forEach {
            val model = modelRepository.findById(it)
            if (!model.isEmpty) {
                modelRepository.delete(model.get())
                deletedModels.add(model.get())
            }
        }
        return deletedModels
    }

    @PostMapping(
        path = ["/add"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun addModels(@RequestBody models: ModelsToCreate): List<Model> {
        val newModels: MutableList<Model> = mutableListOf()
        models.modelsToCreate.forEach {
            val endpoint = endpointRepository.findById(it.endpointId)
            val software = softwareRepository.findById(it.softwareId)
            if (endpoint.isEmpty) {
                throw EntityNotFoundException("Endpoint with id: ${it.endpointId} does not exist")
            }
            if (software.isEmpty) {
                throw EntityNotFoundException("Software with id: ${it.softwareId} does not exist")
            }
            val newModel = Model(it.name.trim().lowercase(), endpoint.get().id, software.get().id)
            modelRepository.save(newModel)
            newModels.add(newModel)
        }
        return newModels
    }

    @GetMapping("get/{name}")
    fun getModelByName(@PathVariable("name")  name: String, @RequestParam(value = "page") page: Int, @RequestParam(value = "pageSize") pageSize: Int): Model {
        val model = modelRepository.findByName(name.trim().lowercase())
        if (model.isEmpty) {
            throw EntityNotFoundException("Model with name $name does not exist")
        }
        return model.get()
    }

    data class ModelsToCreate(
            val modelsToCreate: List<ModelObj>
    )

    data class ModelObj(
        val endpointId: Long,
        val softwareId: Long,
        val name: String)


    data class ModelDeletionObj(
            val modelIds: List<Long>
    )

}
