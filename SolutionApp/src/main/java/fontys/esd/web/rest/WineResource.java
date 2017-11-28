package fontys.esd.web.rest;

import com.codahale.metrics.annotation.Timed;
import fontys.esd.domain.Wine;

import fontys.esd.repository.WineRepository;
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
 * REST controller for managing Wine.
 */
@RestController
@RequestMapping("/api")
public class WineResource {

    private final Logger log = LoggerFactory.getLogger(WineResource.class);

    private static final String ENTITY_NAME = "wine";

    private final WineRepository wineRepository;

    public WineResource(WineRepository wineRepository) {
        this.wineRepository = wineRepository;
    }

    /**
     * POST  /wines : Create a new wine.
     *
     * @param wine the wine to create
     * @return the ResponseEntity with status 201 (Created) and with body the new wine, or with status 400 (Bad Request) if the wine has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/wines")
    @Timed
    public ResponseEntity<Wine> createWine(@Valid @RequestBody Wine wine) throws URISyntaxException {
        log.debug("REST request to save Wine : {}", wine);
        if (wine.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new wine cannot already have an ID")).body(null);
        }
        Wine result = wineRepository.save(wine);
        return ResponseEntity.created(new URI("/api/wines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /wines : Updates an existing wine.
     *
     * @param wine the wine to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated wine,
     * or with status 400 (Bad Request) if the wine is not valid,
     * or with status 500 (Internal Server Error) if the wine couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/wines")
    @Timed
    public ResponseEntity<Wine> updateWine(@Valid @RequestBody Wine wine) throws URISyntaxException {
        log.debug("REST request to update Wine : {}", wine);
        if (wine.getId() == null) {
            return createWine(wine);
        }
        Wine result = wineRepository.save(wine);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, wine.getId().toString()))
            .body(result);
    }

    /**
     * GET  /wines : get all the wines.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of wines in body
     */
    @GetMapping("/wines")
    @Timed
    public List<Wine> getAllWines() {
        log.debug("REST request to get all Wines");
        return wineRepository.findAllWithEagerRelationships();
        }

    /**
     * GET  /wines/:id : get the "id" wine.
     *
     * @param id the id of the wine to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the wine, or with status 404 (Not Found)
     */
    @GetMapping("/wines/{id}")
    @Timed
    public ResponseEntity<Wine> getWine(@PathVariable Long id) {
        log.debug("REST request to get Wine : {}", id);
        Wine wine = wineRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(wine));
    }

    /**
     * DELETE  /wines/:id : delete the "id" wine.
     *
     * @param id the id of the wine to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/wines/{id}")
    @Timed
    public ResponseEntity<Void> deleteWine(@PathVariable Long id) {
        log.debug("REST request to delete Wine : {}", id);
        wineRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
