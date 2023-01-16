package merck.regtox.curie.dto;

import jakarta.persistence.*;

@Entity
@Table(name="logic_rules")
public class LogicRule
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "model_id")
    private Model model;
    @Column(name="prediction_raw")
    private String predictionRaw;
    @Column(name="prediction_mapped")
    private String predictionMapped;

    public LogicRule(Model model, String predictionRaw, String predictionMapped) {
        this.model = model;
        this.predictionRaw = predictionRaw;
        this.predictionMapped = predictionMapped;
    }

    public LogicRule() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPredictionRaw() {
        return predictionRaw;
    }

    public void setPredictionRaw(String predictionRaw) {
        this.predictionRaw = predictionRaw;
    }

    public String getPredictionMapped() {
        return predictionMapped;
    }

    public void setPredictionMapped(String predictionMapped) {
        this.predictionMapped = predictionMapped;
    }

    public Model getModel() {
        return model;
    }
    public void setModel(Model model) {
        this.model = model;
    }
}
