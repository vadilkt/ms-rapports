package tech.chillo.rapports;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.chillo.rapports.entities.Courrier;

import java.util.List;

public interface CourrierRepository extends JpaRepository<Courrier, Integer> {
    @Query("select c from Courrier c left join fetch c.pieces where c.bordereau.id = :id")
    List<Courrier> findCourriersWithPieces(@Param("id") int id);

}
