package merck.regtox.curie.api

import jakarta.persistence.EntityExistsException
import merck.regtox.curie.dto.Endpoint
import merck.regtox.curie.dto.Software
import merck.regtox.curie.dto.repository.SoftwareRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path =["api/v1/software"])
class SoftwareApi(@Autowired val softwareRepository: SoftwareRepository) {

    @GetMapping("")
    fun getAllEndpoints(@RequestParam(value = "0") page: Int, @RequestParam(value = "50") pageSize: Int): Iterable<Software> {
        return softwareRepository.findAll(PageRequest.of(page, pageSize)).toList()
    }

    @PutMapping("add/{name}")
    fun createEndpoint(@PathVariable("name") name: String): Software {
        val newName = name.trim().lowercase()
        if (softwareRepository.existsByName(newName)) {
            throw EntityExistsException("Software with name: $name already exits.")
        }
        return softwareRepository.save(Software(name))
    }

    @DeleteMapping("remove/{id}")
    fun deleteEndpoint(@PathVariable("id") id: Long) {
        softwareRepository.deleteById(id)
    }

}
