package tech.chillo.rapports.entities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String phone;
    private String phoneIndex;
    private String firstName;
    private String lastName;

    @ManyToOne
    private Bordereau bordereau;

}
