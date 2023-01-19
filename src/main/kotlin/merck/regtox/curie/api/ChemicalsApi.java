package merck.regtox.curie.api;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.websocket.server.PathParam;
import merck.regtox.curie.buissnesLogic.ChemicalCreator;
import merck.regtox.curie.dto.*;
import merck.regtox.curie.dto.repository.*;
import merck.regtox.curie.dto.request.ChemicalRequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1")
public class ChemicalsApi {
    @Autowired
    SoftwareRepository softwareRepository;
    @Autowired
    ModelRepository modelRepository;
    @Autowired
    EndpointRepository endpointRepository;
    @Autowired
    ChemicalCreator chemicalCreator;
    @Autowired
    ChemicalRepository chemicalRepository;


    @GetMapping("/software/remove/{id}")
    public void deleteSoftware(@PathParam("id") Long id) {
        if(softwareRepository.existsById(id)) {
            softwareRepository.deleteById(id);
        }else {
            throw new EntityNotFoundException("Entity with id: "+id+" does not exist.");
        }
    }

    @GetMapping("/endpoint/remove/{id}")
    public void deleteEndpoint(@PathParam("id") Long id) {
        if(endpointRepository.existsById(id)) {
            endpointRepository.deleteById(id);
        }else {
            throw new EntityNotFoundException("Entity with id: "+id+" does not exist.");
        }
    }

    @GetMapping("/model/remove/{id}")
    public void deleteModel(@PathParam("id") Long id) {
        if(modelRepository.existsById(id)) {
            modelRepository.deleteById(id);
        }else {
            throw new EntityNotFoundException("Entity with id: "+id+" does not exist.");
        }
    }

    @PostMapping(path="/chemical/add", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public void addNewChemical(@RequestBody ChemicalRequestTemplate chemical) {
        if(chemical.getSmiles() == null && chemical.getCas() == null) {
            throw new IllegalArgumentException("At least one of CAS or SMILES values must have a value");
        }
        chemicalCreator.createChemical(chemical.getCas(), chemical.getSmiles());
    }

    @GetMapping("/chemical")
    public List<Chemical> getAllChemicals(@RequestParam(value="0") int page, @RequestParam(value="50") int size) {
        return chemicalRepository.findAll(PageRequest.of(page,size)).toList();
    }


}
