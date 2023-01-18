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
    @Column(name="raw")
    private String raw;
    @Column(name="mapped")
    private String mapped;

    @Column(name = "is_reliability_mapping")
    private Boolean isReliability;

    public LogicRule(Model model, String raw, String mapped, Boolean isReliability) {
        this.model = model;
        this.raw = raw;
        this.mapped = mapped;
        this.isReliability = isReliability;
    }

    public Boolean getReliability() {
        return isReliability;
    }

    public void setReliability(Boolean reliability) {
        isReliability = reliability;
    }

    public LogicRule() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getraw() {
        return raw;
    }

    public void setraw(String raw) {
        this.raw = raw;
    }

    public String getmapped() {
        return mapped;
    }

    public void setmapped(String mapped) {
        this.mapped = mapped;
    }

    public Model getModel() {
        return model;
    }
    public void setModel(Model model) {
        this.model = model;
    }
}
