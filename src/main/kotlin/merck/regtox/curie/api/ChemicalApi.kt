package merck.regtox.curie.api

import jakarta.persistence.EntityNotFoundException
import merck.regtox.curie.buissnesLogic.ChemicalCreator
import merck.regtox.curie.dto.Chemical
import merck.regtox.curie.dto.repository.ChemicalRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*
import kotlin.jvm.optionals.toList

@RestController
@RequestMapping(path = ["api/v1/chemical"])
class ChemicalApi(@Autowired val chemicalRepository: ChemicalRepository,
                  @Autowired val chemicalCreator: ChemicalCreator) {
    @GetMapping
    fun getChemicals(@RequestParam(value = "page") page: Int, @RequestParam(value = "pageSize") pageSize: Int): List<Chemical> {
        return chemicalRepository.findAll(PageRequest.of(page, pageSize)).toList()
    }

    @GetMapping("/cas/{cas}")
    fun getChemicalByCas(@RequestParam(value = "page") page: Int, @RequestParam(value = "pageSize") pageSize: Int, @PathVariable cas: String): List<Chemical> {
        return chemicalRepository.findAllByCas(cas, PageRequest.of(page, pageSize))
    }

    @GetMapping("/smile/{smile}")
    fun getChemicalBySmile(@RequestParam(value = "page") page: Int, @RequestParam(value = "pageSize") pageSize: Int, @PathVariable smile: String): Chemical {
        return chemicalRepository.findBySmile(smile).get()
    }

    @DeleteMapping("remove/{id}")
    fun deleteEndpoint(@PathVariable("id") id: Long) {
        chemicalRepository.deleteById(id)
    }

    @DeleteMapping("remove")
    fun deleteEndpoints(@RequestBody toDelte: ChemicalsDeletionObj): List<Chemical> {
        val deleteChemicals = mutableListOf<Chemical>()
        toDelte.ids.forEach {
            if (chemicalRepository.existsById(it)) {
                val chemical = chemicalRepository.findById(it)
                chemicalRepository.delete(chemical.get())
                deleteChemicals.add(chemical.get())
            }
        }
        return deleteChemicals
    }

    @PostMapping("add")
    fun addChemicals(@RequestBody chemicals: ChemicalsObj): List<Chemical> {
        val createdChemicals = mutableListOf<Chemical>()
        chemicals.chems.forEach {
            val newChemical = chemicalCreator.createChemical(it.cas, it.smile)
            if (newChemical.id != null)
                createdChemicals.add(newChemical)
        }
        return createdChemicals
    }

    data class ChemicalsDeletionObj(val ids: List<Long>)

    data class ChemicalObj(val cas: String, val smile:String)
    data class ChemicalsObj(val chems: List<ChemicalObj>)
}