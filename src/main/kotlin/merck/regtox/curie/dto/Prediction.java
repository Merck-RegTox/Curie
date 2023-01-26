package merck.regtox.curie.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@Entity
@Table
public class Prediction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name = "chemical_id")
    private Long cid;

    @Column(name="prediction_raw")
    private String prediction_raw;

    @Column(name="prediction")
    private String prediction;

    @Column(name="reliability_raw")
    private String reliability_raw;

    @Column(name="reliability")
    private String reliability;
    @Column(name = "model_id")
    private Long mid;

    public Prediction() {
    }

    public Prediction(Long cid, String prediction_raw, String prediction, String reliability_raw, String reliability, Long model) {
        this.cid = cid;
        this.prediction_raw = prediction_raw;
        this.prediction = prediction;
        this.reliability_raw = reliability_raw;
        this.reliability = reliability;
        this.mid = mid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getPrediction_raw() {
        return prediction_raw;
    }

    public void setPrediction_raw(String prediction_raw) {
        this.prediction_raw = prediction_raw;
    }

    public String getPrediction() {
        return prediction;
    }

    public void setPrediction(String prediction) {
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

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }
}
