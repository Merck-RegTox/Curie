package merck.regtox.curie.dto;

import jakarta.persistence.*;

@Entity
@Table(name = "endpoint")
public class Endpoint {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name", nullable = false, unique = true)
    private String name;

    public Endpoint() {
    }

    public Endpoint(String name) {
        this.name = name;
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
}
