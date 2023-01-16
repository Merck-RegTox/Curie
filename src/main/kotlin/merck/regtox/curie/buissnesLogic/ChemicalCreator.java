package merck.regtox.curie.buissnesLogic;

import jakarta.persistence.EntityExistsException;
import merck.regtox.curie.dto.Chemical;
import merck.regtox.curie.dto.repository.ChemicalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChemicalCreator {

    @Autowired
    ChemicalRepository chemicalRepository;

    public ChemicalCreator() {
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
}
