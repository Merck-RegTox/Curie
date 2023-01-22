package merck.regtox.curie.buissnesLogic;

import merck.regtox.curie.dto.Chemical;
import merck.regtox.curie.dto.repository.ChemicalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ChemicalLogic {

    @Autowired
    ChemicalRepository chemicalRepository;

    public ChemicalLogic() {
    }

    public Chemical createChemical(String cas, String smile) {
        if (smile != null) {
            Boolean existsByCas = chemicalRepository.existsByCas(cas);
            Boolean existsBySmile = chemicalRepository.existsBySmile(smile);
            if (!existsBySmile && !existsByCas) {
                Chemical newChemical = new Chemical();
                newChemical.setSmile(smile);
                newChemical.setCas(cas);
                return chemicalRepository.save(newChemical);
            }
            if (!existsByCas && existsBySmile) {
                Chemical newChemical = chemicalRepository.findBySmile(smile).get();
                newChemical.setCas(cas);
                return chemicalRepository.save(newChemical);
            }
            if (existsBySmile && !existsByCas) {
                Chemical newChemical = chemicalRepository.findBySmile(smile).get();
                newChemical.setSmile(smile);
                return chemicalRepository.save(newChemical);
            }
        }else {
            if(!chemicalRepository.existsByCas(cas)) {
                Chemical newChemical = new Chemical();
                newChemical.setCas(cas);
                return chemicalRepository.save(newChemical);
            }
        }
        return new Chemical();
    }

    public Long getChemicalId(String cas, String smile) {
        if (cas == null && smile == null) {
            return null;
        }
        if (cas != null) {
            cas = cas.trim().toLowerCase();
        }
        if (smile != null) {
            smile = smile.trim().toLowerCase();
        }
        Chemical chemicalExample = Chemical.builder()
                .cas(cas)
                .smile(smile)
                .build();
        List<Chemical> chemicals = chemicalRepository.findAll(Example.of(chemicalExample));
        if (chemicals.isEmpty()) {
            return -1L;
        }

        return chemicals.get(0).getId();
    }

}
