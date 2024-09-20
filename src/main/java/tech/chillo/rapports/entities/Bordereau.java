package tech.chillo.rapports.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "bordereaux")
public class Bordereau implements Serializable {

    @JsonManagedReference
    @OneToMany(mappedBy = "bordereau",
            orphanRemoval = true,
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private final List<Courrier> courriers = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "numero")
    private String numero;
    @Column(name = "provenance")
    private String provenance;
    @Column(name = "destination")
    private String destination;
    @Column(name = "emission")
    private LocalDateTime emission;

}
