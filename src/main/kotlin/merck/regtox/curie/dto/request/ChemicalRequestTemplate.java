package merck.regtox.curie.dto.request;

public class ChemicalRequestTemplate {
    String cas;
    String smiles;

    public String getCas() {
        return cas;
    }

    public void setCas(String cas) {
        this.cas = cas;
    }

    public String getSmiles() {
        return smiles;
    }

    public void setSmiles(String smiles) {
        this.smiles = smiles;
    }
}
