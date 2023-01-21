package merck.regtox.curie.dto;

import jakarta.persistence.*;

@Entity
@Table(name="model")
public class Model {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name", nullable = false)
    private String name;

    @Column(name="eid", nullable = false)
    private Long eid;

    @Column(name="sid", nullable = false)
    private Long sid;

    public Model() {
    }

    public Model(String name, Long eid, Long sid) {
        this.name = name;
        this.eid = eid;
        this.sid = sid;
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

    public Long getEid() {
        return eid;
    }

    public void setEid(Long eid) {
        this.eid = eid;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }
}
