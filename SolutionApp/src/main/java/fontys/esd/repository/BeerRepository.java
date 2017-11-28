package fontys.esd.repository;

import fontys.esd.domain.Beer;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Beer entity.
 */
        
        
@SuppressWarnings("unused")
@Repository
public interface BeerRepository extends JpaRepository<Beer, Long> {
    @Query("select distinct beer from Beer beer left join fetch beer.properties")
    List<Beer> findAllWithEagerRelationships();

    @Query("select beer from Beer beer left join fetch beer.properties where beer.id =:id")
    Beer findOneWithEagerRelationships(@Param("id") Long id);
}
