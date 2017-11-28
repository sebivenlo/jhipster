package fontys.esd.web.rest;

import com.codahale.metrics.annotation.Timed;
import fontys.esd.domain.BeverageChart;

import fontys.esd.repository.BeverageChartRepository;
import fontys.esd.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing BeverageChart.
 */
@RestController
@RequestMapping("/api")
public class BeverageChartResource {

    private final Logger log = LoggerFactory.getLogger(BeverageChartResource.class);

    private static final String ENTITY_NAME = "beverageChart";

    private final BeverageChartRepository beverageChartRepository;

    public BeverageChartResource(BeverageChartRepository beverageChartRepository) {
        this.beverageChartRepository = beverageChartRepository;
    }

    /**
     * POST  /beverage-charts : Create a new beverageChart.
     *
     * @param beverageChart the beverageChart to create
     * @return the ResponseEntity with status 201 (Created) and with body the new beverageChart, or with status 400 (Bad Request) if the beverageChart has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/beverage-charts")
    @Timed
    public ResponseEntity<BeverageChart> createBeverageChart(@Valid @RequestBody BeverageChart beverageChart) throws URISyntaxException {
        log.debug("REST request to save BeverageChart : {}", beverageChart);
        if (beverageChart.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new beverageChart cannot already have an ID")).body(null);
        }
        BeverageChart result = beverageChartRepository.save(beverageChart);
        return ResponseEntity.created(new URI("/api/beverage-charts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /beverage-charts : Updates an existing beverageChart.
     *
     * @param beverageChart the beverageChart to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated beverageChart,
     * or with status 400 (Bad Request) if the beverageChart is not valid,
     * or with status 500 (Internal Server Error) if the beverageChart couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/beverage-charts")
    @Timed
    public ResponseEntity<BeverageChart> updateBeverageChart(@Valid @RequestBody BeverageChart beverageChart) throws URISyntaxException {
        log.debug("REST request to update BeverageChart : {}", beverageChart);
        if (beverageChart.getId() == null) {
            return createBeverageChart(beverageChart);
        }
        BeverageChart result = beverageChartRepository.save(beverageChart);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, beverageChart.getId().toString()))
            .body(result);
    }

    /**
     * GET  /beverage-charts : get all the beverageCharts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of beverageCharts in body
     */
    @GetMapping("/beverage-charts")
    @Timed
    public List<BeverageChart> getAllBeverageCharts() {
        log.debug("REST request to get all BeverageCharts");
        return beverageChartRepository.findByUserIsCurrentUser();
        }

    /**
     * GET  /beverage-charts/:id : get the "id" beverageChart.
     *
     * @param id the id of the beverageChart to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the beverageChart, or with status 404 (Not Found)
     */
    @GetMapping("/beverage-charts/{id}")
    @Timed
    public ResponseEntity<BeverageChart> getBeverageChart(@PathVariable Long id) {
        log.debug("REST request to get BeverageChart : {}", id);
        BeverageChart beverageChart = beverageChartRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(beverageChart));
    }

    /**
     * DELETE  /beverage-charts/:id : delete the "id" beverageChart.
     *
     * @param id the id of the beverageChart to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/beverage-charts/{id}")
    @Timed
    public ResponseEntity<Void> deleteBeverageChart(@PathVariable Long id) {
        log.debug("REST request to delete BeverageChart : {}", id);
        beverageChartRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
