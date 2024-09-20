package tech.chillo.rapports;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.chillo.rapports.entities.Bordereau;
import tech.chillo.rapports.entities.PieceJointe;

import java.util.List;

@Repository
public interface PJRepository extends JpaRepository<PieceJointe, Integer>, JpaSpecificationExecutor<PieceJointe> {

  /*  @Query(value =
            "select pj from PieceJointe pj right join  pj.courrier c  " +
            "right join c.bordereau b  where pj.courrier.bordereau.id= :id") */
  @Query("SELECT pj,c,b " +
          "FROM PieceJointe pj " +
          "RIGHT JOIN Courrier c on pj.courrier.id = c.id " +
          "RIGHT JOIN Bordereau b on c.bordereau.id= b.id " +
          "WHERE b.id = :id")
    List<Object[]> print(@Param("id") int id);
}

