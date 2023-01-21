package merck.regtox.curie.api

import jakarta.persistence.EntityExistsException
import jakarta.persistence.EntityNotFoundException
import merck.regtox.curie.dto.Endpoint
import merck.regtox.curie.dto.Software
import merck.regtox.curie.dto.repository.EndpointRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["api/v1/endpoint"])
class EndpointApi(@Autowired val endpointRepository: EndpointRepository) {

    @GetMapping("")
    fun getAllEndpoints(@RequestParam(value = "page") page: Int, @RequestParam(value = "pageSize") pageSize: Int): Iterable<Endpoint> {
        return endpointRepository.findAll(PageRequest.of(page, pageSize)).toList()
    }

    @PutMapping("add/{name}")
    fun createEndpoint(@PathVariable("name") name: String): Endpoint {
        val newName = name.trim().lowercase()
        if (endpointRepository.existsByName(newName)) {
           throw EntityExistsException("Endpoint with name: $name already exits.")
        }
        return endpointRepository.save(Endpoint(name))
    }

    @DeleteMapping("remove/{id}")
    fun deleteEndpoint(@PathVariable("id") id: Long) {
        endpointRepository.deleteById(id)
    }

    @GetMapping("get/{name}")
    fun getEndpointByName(@PathVariable("name")  name: String, @RequestParam(value = "page") page: Int, @RequestParam(value = "pageSize") pageSize: Int): Endpoint {
        val endpoint = endpointRepository.findByName(name.trim().lowercase())
        if (endpoint.isEmpty) {
            throw EntityNotFoundException("Endpoint with name $name does not exist")
        }
        return endpoint.get()
    }

}

