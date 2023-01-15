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
    private Long endpoint_id;

    @JoinColumn(name="sid", nullable = false)
    @ManyToOne(targetEntity = Software.class, cascade = CascadeType.ALL)
    private Long software_id;
}
