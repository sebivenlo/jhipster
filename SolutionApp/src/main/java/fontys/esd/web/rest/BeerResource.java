package fontys.esd.web.rest;

import com.codahale.metrics.annotation.Timed;
import fontys.esd.domain.Beer;

import fontys.esd.repository.BeerRepository;
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
 * REST controller for managing Beer.
 */
@RestController
@RequestMapping("/api")
public class BeerResource {

    private final Logger log = LoggerFactory.getLogger(BeerResource.class);

    private static final String ENTITY_NAME = "beer";

    private final BeerRepository beerRepository;

    public BeerResource(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    /**
     * POST  /beers : Create a new beer.
     *
     * @param beer the beer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new beer, or with status 400 (Bad Request) if the beer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/beers")
    @Timed
    public ResponseEntity<Beer> createBeer(@Valid @RequestBody Beer beer) throws URISyntaxException {
        log.debug("REST request to save Beer : {}", beer);
        if (beer.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new beer cannot already have an ID")).body(null);
        }
        Beer result = beerRepository.save(beer);
        return ResponseEntity.created(new URI("/api/beers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /beers : Updates an existing beer.
     *
     * @param beer the beer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated beer,
     * or with status 400 (Bad Request) if the beer is not valid,
     * or with status 500 (Internal Server Error) if the beer couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/beers")
    @Timed
    public ResponseEntity<Beer> updateBeer(@Valid @RequestBody Beer beer) throws URISyntaxException {
        log.debug("REST request to update Beer : {}", beer);
        if (beer.getId() == null) {
            return createBeer(beer);
        }
        Beer result = beerRepository.save(beer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, beer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /beers : get all the beers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of beers in body
     */
    @GetMapping("/beers")
    @Timed
    public List<Beer> getAllBeers() {
        log.debug("REST request to get all Beers");
        return beerRepository.findAllWithEagerRelationships();
        }

    /**
     * GET  /beers/:id : get the "id" beer.
     *
     * @param id the id of the beer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the beer, or with status 404 (Not Found)
     */
    @GetMapping("/beers/{id}")
    @Timed
    public ResponseEntity<Beer> getBeer(@PathVariable Long id) {
        log.debug("REST request to get Beer : {}", id);
        Beer beer = beerRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(beer));
    }

    /**
     * DELETE  /beers/:id : delete the "id" beer.
     *
     * @param id the id of the beer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/beers/{id}")
    @Timed
    public ResponseEntity<Void> deleteBeer(@PathVariable Long id) {
        log.debug("REST request to delete Beer : {}", id);
        beerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
