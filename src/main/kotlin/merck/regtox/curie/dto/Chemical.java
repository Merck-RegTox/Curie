package merck.regtox.curie.dto;

import jakarta.persistence.*;

@Entity
@Table(name="chemical")
public class Chemical {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="cas")
    private String cas;
    @Column(name="smile")
    private String smile;

    public Chemical() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCas() {
        return cas;
    }

    public void setCas(String cas) {
        this.cas = cas;
    }

    public String getSmile() {
        return smile;
    }

    public void setSmile(String smile) {
        this.smile = smile;
    }
}
