package merck.regtox.curie.dto;

import jakarta.persistence.*;

@Entity
@Table(name="model")
public class Model {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="name", nullable = false)
    private String name;
    @JoinColumn(name="eid", nullable = false)
    @ManyToOne(targetEntity = Endpoint.class, cascade = CascadeType.ALL)
    private Endpoint endpoint;

    @JoinColumn(name="sid", nullable = false)
    @ManyToOne(targetEntity = Software.class, cascade = CascadeType.ALL)
    private Software software;

    public Model() {
    }

    public Model(String name, Endpoint endpoint, Software software) {
        this.name = name;
        this.endpoint = endpoint;
        this.software = software;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Endpoint getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    public Software getSoftware() {
        return software;
    }

    public void setSoftware(Software software) {
        this.software = software;
    }
}
