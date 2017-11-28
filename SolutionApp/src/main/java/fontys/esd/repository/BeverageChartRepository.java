package fontys.esd.repository;

import fontys.esd.domain.BeverageChart;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the BeverageChart entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BeverageChartRepository extends JpaRepository<BeverageChart, Long> {

    @Query("select beverage_chart from BeverageChart beverage_chart where beverage_chart.user.login = ?#{principal.username}")
    List<BeverageChart> findByUserIsCurrentUser();

}
