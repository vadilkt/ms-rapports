package tech.chillo.rapports.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "pieces_jointes")
public class PieceJointe {

    @JsonBackReference
    @ManyToOne
    private Courrier courrier;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String libelle;
    private String dossier;

}
