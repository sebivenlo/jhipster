package fontys.esd.repository;

import fontys.esd.domain.Wine;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Wine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WineRepository extends JpaRepository<Wine, Long> {
    @Query("select distinct wine from Wine wine left join fetch wine.properties")
    List<Wine> findAllWithEagerRelationships();

    @Query("select wine from Wine wine left join fetch wine.properties where wine.id =:id")
    Wine findOneWithEagerRelationships(@Param("id") Long id);

}
