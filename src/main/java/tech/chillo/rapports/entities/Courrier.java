package tech.chillo.rapports.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "courriers")
public class Courrier {
    @JsonManagedReference
    @OneToMany(mappedBy = "courrier",
            orphanRemoval = true,
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private final List<PieceJointe> pieces = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String numeroCourrier;
    private String numExpediteur;
    private String expediteur;
    private LocalDateTime dateSignCourrier;
    private String objetCourrier;
    private LocalDateTime dateEnregCourrier;
    private LocalDateTime dateEnregCot;
    private String numCoteur;
    private String nomCoteur;
    private LocalDateTime dateCotation;
    private String instructions;
    private LocalDateTime dateButoir;
    private String niveauTrtCoteur;
    private String codeNatureCourrier;
    private String natureCourrier;
    private String numCote;
    private String nomCote;
    private String niveauTrtCote;
    private String Observation;
    @JsonBackReference
    @ManyToOne
    private Bordereau bordereau;
}
