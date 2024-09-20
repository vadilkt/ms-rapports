package tech.chillo.rapports;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tech.chillo.rapports.entities.Bordereau;

@Repository
public interface BordereauRepository extends JpaRepository<Bordereau, Integer>, JpaSpecificationExecutor<Bordereau> {
}

