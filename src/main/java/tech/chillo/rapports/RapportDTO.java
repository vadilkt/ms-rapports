package tech.chillo.rapports;

import lombok.Getter;
import lombok.Setter;
import tech.chillo.rapports.entities.Bordereau;
import tech.chillo.rapports.entities.Courrier;
import tech.chillo.rapports.entities.PieceJointe;

@Setter
@Getter
public class RapportDTO {
    private PieceJointe pieceJointe;
    private Courrier courrier;
    private Bordereau bordereau;

    public RapportDTO(PieceJointe pieceJointe, Courrier courrier, Bordereau bordereau) {
        this.pieceJointe = pieceJointe;
        this.courrier = courrier;
        this.bordereau = bordereau;
    }

}
