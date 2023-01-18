package merck.regtox.curie.api

import jakarta.persistence.EntityNotFoundException
import merck.regtox.curie.dto.Endpoint
import merck.regtox.curie.dto.LogicRule
import merck.regtox.curie.dto.Model
import merck.regtox.curie.dto.repository.EndpointRepository
import merck.regtox.curie.dto.repository.ModelRepository
import merck.regtox.curie.dto.repository.SoftwareRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

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
        return modelRepository.findModelByEndpointContaining(endpoint.get())
    }

    @GetMapping("software")
    fun getModelsBySoftware(@RequestParam(value = "page") page: Int,
                            @RequestParam(value = "pageSize") pageSize: Int,
                            @RequestParam(value = "software_Id") softwareId: Long): List<Model> {
        val software = softwareRepository.findById(softwareId)
        if (software.isEmpty) {
            throw EntityNotFoundException("Endpoint with id: $softwareId does not exist")
        }
        return modelRepository.findModelBySoftwareContaining(software.get())
    }


    @DeleteMapping
    fun deleteModelsById(): List<Model> {

    }

    @PostMapping
    fun addModels(): List<Model> {

    }

    data class ModelObj()

    /*TODO:
        1. adding new models
        2. deleting new models
        3. test models api
        4. test logic rules api
    */
}
