package merck.regtox.curie.dto;

import jakarta.persistence.*;

@Entity
@Table
public class Prediction {
    @Id
    @Column(name="id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "chemical_id")
    private Chemical chemical;

    @Column(name="prediction_raw")
    private String prediction_raw;

    @Column(name="prediction")
    private Boolean prediction;

    @Column(name="reliability_raw")
    private String reliability_raw;

    @Column(name="reliability")
    private String reliability;
    @ManyToOne
    @JoinColumn(name = "model_id")
    private Model model;

    public Prediction() {
    }

    public Prediction(Chemical chemical, String prediction_raw, Boolean prediction, String reliability_raw, String reliability, Model model) {
        this.chemical = chemical;
        this.prediction_raw = prediction_raw;
        this.prediction = prediction;
        this.reliability_raw = reliability_raw;
        this.reliability = reliability;
        this.model = model;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Chemical getChemical() {
        return chemical;
    }

    public void setChemical(Chemical chemical) {
        this.chemical = chemical;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrediction_raw() {
        return prediction_raw;
    }

    public void setPrediction_raw(String prediction_raw) {
        this.prediction_raw = prediction_raw;
    }

    public Boolean getPrediction() {
        return prediction;
    }

    public void setPrediction(Boolean prediction) {
        this.prediction = prediction;
    }

    public String getReliability_raw() {
        return reliability_raw;
    }

    public void setReliability_raw(String reliability_raw) {
        this.reliability_raw = reliability_raw;
    }

    public String getReliability() {
        return reliability;
    }

    public void setReliability(String reliability) {
        this.reliability = reliability;
    }
}
