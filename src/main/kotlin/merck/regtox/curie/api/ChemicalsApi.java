package merck.regtox.curie.api;

import jakarta.persistence.EntityExistsException;
import merck.regtox.curie.dto.Endpoint;
import merck.regtox.curie.dto.Model;
import merck.regtox.curie.dto.Software;
import merck.regtox.curie.dto.repository.EndpointRepository;
import merck.regtox.curie.dto.repository.ModelRepository;
import merck.regtox.curie.dto.repository.SoftwareRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/software")
    public List<Software> getAllSoftware(){
        return softwareRepository.findAll();
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

    @GetMapping("/model")
    public List<Model> getAllModels(){
        return modelRepository.findAll();
    }

    @GetMapping("/endpoint")
    public List<Endpoint> getAllEndpoints(){
        return endpointRepository.findAll();
    }
}
