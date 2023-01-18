package merck.regtox.curie.api;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.websocket.server.PathParam;
import merck.regtox.curie.buissnesLogic.ChemicalCreator;
import merck.regtox.curie.dto.*;
import merck.regtox.curie.dto.repository.*;
import merck.regtox.curie.dto.request.ChemicalRequestTemplate;
import merck.regtox.curie.dto.request.ModelRequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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


    @PostMapping(path="/model/add", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public void createNewModel(@RequestBody ModelRequestTemplate model) {
        model.setName(model
                .getName()
                .trim()
                .toLowerCase()
        );
        Optional<Software> software = softwareRepository.findById(model.getSoftwareId());
        Optional<Endpoint> endpoint = endpointRepository.findById(model.getEndpointId());

        if(endpoint.isEmpty()) {
            throw new EntityNotFoundException("Endpoint with id: "+model.getSoftwareId()+" not found");
        }
        if(software.isEmpty()) {
            throw new EntityNotFoundException("Software with id: "+model.getEndpointId()+" not found");
        }

        if(modelRepository.existsByNameAndEndpointIdAndSoftwareId(model.getName(), model.getEndpointId(), model.getSoftwareId()))
        {
            throw new EntityExistsException("Entity with given parameters already exists");
        }
        Model dbModel = new Model();
        dbModel.setEndpoint(endpoint.get());
        dbModel.setSoftware(software.get());
        dbModel.setName(model.getName());
        modelRepository.save(dbModel);
    }

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
