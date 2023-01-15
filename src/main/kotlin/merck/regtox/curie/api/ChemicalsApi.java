package merck.regtox.curie.api;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.websocket.server.PathParam;
import merck.regtox.curie.dto.Endpoint;
import merck.regtox.curie.dto.Model;
import merck.regtox.curie.dto.Software;
import merck.regtox.curie.dto.repository.EndpointRepository;
import merck.regtox.curie.dto.repository.ModelRepository;
import merck.regtox.curie.dto.repository.SoftwareRepository;
import merck.regtox.curie.dto.request.ModelRequest;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/software")
    public List<Software> getAllSoftware(){
        return softwareRepository.findAll();
    }

    @GetMapping("/software/{id}")
    public Software getSoftwareById(@PathParam("id") Long id){
        return softwareRepository.findById(id)
                                 .orElseThrow(() -> new EntityNotFoundException("Software with id: "+id+" doesnt exist"));
    }

    @GetMapping("/model")
    public List<Model> getAllModels(){
        return modelRepository.findAll();
    }

    @GetMapping("/endpoint")
    public List<Endpoint> getAllEndpoints(){
        return endpointRepository.findAll();
    }

    @PostMapping(path="/software/add", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public void createNewSoftware(@RequestBody Software software) {
        software.setName(software
                .getName()
                .trim()
                .toLowerCase()
        );
        if(softwareRepository.existsByName(software.getName())) {
            throw new EntityExistsException("Software with name: '"+software.getName()+"' already exists");
        }
        softwareRepository.save(software);
    }

    @PostMapping(path="/endpoint/add", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public void createNewEndpoint(@RequestBody Endpoint endpoint) {
        endpoint.setName(endpoint
                .getName()
                .trim()
                .toLowerCase()
        );
        if(endpointRepository.existsByName(endpoint.getName())) {
            throw new EntityExistsException("Endpoint with name: '"+endpoint.getName()+"' already exists");
        }
        endpointRepository.save(endpoint);
    }

    @PostMapping(path="/model/add", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public void createNewModel(@RequestBody ModelRequest model) {
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


}
