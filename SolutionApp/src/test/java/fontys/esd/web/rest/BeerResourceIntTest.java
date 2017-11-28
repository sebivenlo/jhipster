package fontys.esd.web.rest;

import fontys.esd.JHipsterApp;

import fontys.esd.domain.Beer;
import fontys.esd.repository.BeerRepository;
import fontys.esd.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BeerResource REST controller.
 *
 * @see BeerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JHipsterApp.class)
public class BeerResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Float DEFAULT_PRICE = 1F;
    private static final Float UPDATED_PRICE = 2F;

    private static final Float DEFAULT_QUANTITY = 1F;
    private static final Float UPDATED_QUANTITY = 2F;

    @Autowired
    private BeerRepository beerRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBeerMockMvc;

    private Beer beer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BeerResource beerResource = new BeerResource(beerRepository);
        this.restBeerMockMvc = MockMvcBuilders.standaloneSetup(beerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beer createEntity(EntityManager em) {
        Beer beer = new Beer()
            .name(DEFAULT_NAME)
            .price(DEFAULT_PRICE)
            .quantity(DEFAULT_QUANTITY);
        return beer;
    }

    @Before
    public void initTest() {
        beer = createEntity(em);
    }

    @Test
    @Transactional
    public void createBeer() throws Exception {
        int databaseSizeBeforeCreate = beerRepository.findAll().size();

        // Create the Beer
        restBeerMockMvc.perform(post("/api/beers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beer)))
            .andExpect(status().isCreated());

        // Validate the Beer in the database
        List<Beer> beerList = beerRepository.findAll();
        assertThat(beerList).hasSize(databaseSizeBeforeCreate + 1);
        Beer testBeer = beerList.get(beerList.size() - 1);
        assertThat(testBeer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBeer.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testBeer.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    public void createBeerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = beerRepository.findAll().size();

        // Create the Beer with an existing ID
        beer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBeerMockMvc.perform(post("/api/beers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beer)))
            .andExpect(status().isBadRequest());

        // Validate the Beer in the database
        List<Beer> beerList = beerRepository.findAll();
        assertThat(beerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = beerRepository.findAll().size();
        // set the field null
        beer.setName(null);

        // Create the Beer, which fails.

        restBeerMockMvc.perform(post("/api/beers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beer)))
            .andExpect(status().isBadRequest());

        List<Beer> beerList = beerRepository.findAll();
        assertThat(beerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = beerRepository.findAll().size();
        // set the field null
        beer.setPrice(null);

        // Create the Beer, which fails.

        restBeerMockMvc.perform(post("/api/beers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beer)))
            .andExpect(status().isBadRequest());

        List<Beer> beerList = beerRepository.findAll();
        assertThat(beerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = beerRepository.findAll().size();
        // set the field null
        beer.setQuantity(null);

        // Create the Beer, which fails.

        restBeerMockMvc.perform(post("/api/beers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beer)))
            .andExpect(status().isBadRequest());

        List<Beer> beerList = beerRepository.findAll();
        assertThat(beerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBeers() throws Exception {
        // Initialize the database
        beerRepository.saveAndFlush(beer);

        // Get all the beerList
        restBeerMockMvc.perform(get("/api/beers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(beer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())));
    }

    @Test
    @Transactional
    public void getBeer() throws Exception {
        // Initialize the database
        beerRepository.saveAndFlush(beer);

        // Get the beer
        restBeerMockMvc.perform(get("/api/beers/{id}", beer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(beer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBeer() throws Exception {
        // Get the beer
        restBeerMockMvc.perform(get("/api/beers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBeer() throws Exception {
        // Initialize the database
        beerRepository.saveAndFlush(beer);
        int databaseSizeBeforeUpdate = beerRepository.findAll().size();

        // Update the beer
        Beer updatedBeer = beerRepository.findOne(beer.getId());
        updatedBeer
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE)
            .quantity(UPDATED_QUANTITY);

        restBeerMockMvc.perform(put("/api/beers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBeer)))
            .andExpect(status().isOk());

        // Validate the Beer in the database
        List<Beer> beerList = beerRepository.findAll();
        assertThat(beerList).hasSize(databaseSizeBeforeUpdate);
        Beer testBeer = beerList.get(beerList.size() - 1);
        assertThat(testBeer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBeer.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testBeer.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void updateNonExistingBeer() throws Exception {
        int databaseSizeBeforeUpdate = beerRepository.findAll().size();

        // Create the Beer

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBeerMockMvc.perform(put("/api/beers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beer)))
            .andExpect(status().isCreated());

        // Validate the Beer in the database
        List<Beer> beerList = beerRepository.findAll();
        assertThat(beerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBeer() throws Exception {
        // Initialize the database
        beerRepository.saveAndFlush(beer);
        int databaseSizeBeforeDelete = beerRepository.findAll().size();

        // Get the beer
        restBeerMockMvc.perform(delete("/api/beers/{id}", beer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Beer> beerList = beerRepository.findAll();
        assertThat(beerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Beer.class);
        Beer beer1 = new Beer();
        beer1.setId(1L);
        Beer beer2 = new Beer();
        beer2.setId(beer1.getId());
        assertThat(beer1).isEqualTo(beer2);
        beer2.setId(2L);
        assertThat(beer1).isNotEqualTo(beer2);
        beer1.setId(null);
        assertThat(beer1).isNotEqualTo(beer2);
    }
}
