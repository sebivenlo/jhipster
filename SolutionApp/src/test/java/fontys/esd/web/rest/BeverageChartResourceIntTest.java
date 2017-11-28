package fontys.esd.web.rest;

import fontys.esd.JHipsterApp;

import fontys.esd.domain.BeverageChart;
import fontys.esd.repository.BeverageChartRepository;
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
 * Test class for the BeverageChartResource REST controller.
 *
 * @see BeverageChartResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JHipsterApp.class)
public class BeverageChartResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INTRO = "AAAAAAAAAA";
    private static final String UPDATED_INTRO = "BBBBBBBBBB";

    @Autowired
    private BeverageChartRepository beverageChartRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBeverageChartMockMvc;

    private BeverageChart beverageChart;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BeverageChartResource beverageChartResource = new BeverageChartResource(beverageChartRepository);
        this.restBeverageChartMockMvc = MockMvcBuilders.standaloneSetup(beverageChartResource)
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
    public static BeverageChart createEntity(EntityManager em) {
        BeverageChart beverageChart = new BeverageChart()
            .name(DEFAULT_NAME)
            .intro(DEFAULT_INTRO);
        return beverageChart;
    }

    @Before
    public void initTest() {
        beverageChart = createEntity(em);
    }

    @Test
    @Transactional
    public void createBeverageChart() throws Exception {
        int databaseSizeBeforeCreate = beverageChartRepository.findAll().size();

        // Create the BeverageChart
        restBeverageChartMockMvc.perform(post("/api/beverage-charts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beverageChart)))
            .andExpect(status().isCreated());

        // Validate the BeverageChart in the database
        List<BeverageChart> beverageChartList = beverageChartRepository.findAll();
        assertThat(beverageChartList).hasSize(databaseSizeBeforeCreate + 1);
        BeverageChart testBeverageChart = beverageChartList.get(beverageChartList.size() - 1);
        assertThat(testBeverageChart.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBeverageChart.getIntro()).isEqualTo(DEFAULT_INTRO);
    }

    @Test
    @Transactional
    public void createBeverageChartWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = beverageChartRepository.findAll().size();

        // Create the BeverageChart with an existing ID
        beverageChart.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBeverageChartMockMvc.perform(post("/api/beverage-charts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beverageChart)))
            .andExpect(status().isBadRequest());

        // Validate the BeverageChart in the database
        List<BeverageChart> beverageChartList = beverageChartRepository.findAll();
        assertThat(beverageChartList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = beverageChartRepository.findAll().size();
        // set the field null
        beverageChart.setName(null);

        // Create the BeverageChart, which fails.

        restBeverageChartMockMvc.perform(post("/api/beverage-charts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beverageChart)))
            .andExpect(status().isBadRequest());

        List<BeverageChart> beverageChartList = beverageChartRepository.findAll();
        assertThat(beverageChartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIntroIsRequired() throws Exception {
        int databaseSizeBeforeTest = beverageChartRepository.findAll().size();
        // set the field null
        beverageChart.setIntro(null);

        // Create the BeverageChart, which fails.

        restBeverageChartMockMvc.perform(post("/api/beverage-charts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beverageChart)))
            .andExpect(status().isBadRequest());

        List<BeverageChart> beverageChartList = beverageChartRepository.findAll();
        assertThat(beverageChartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBeverageCharts() throws Exception {
        // Initialize the database
        beverageChartRepository.saveAndFlush(beverageChart);

        // Get all the beverageChartList
        restBeverageChartMockMvc.perform(get("/api/beverage-charts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(beverageChart.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].intro").value(hasItem(DEFAULT_INTRO.toString())));
    }

    @Test
    @Transactional
    public void getBeverageChart() throws Exception {
        // Initialize the database
        beverageChartRepository.saveAndFlush(beverageChart);

        // Get the beverageChart
        restBeverageChartMockMvc.perform(get("/api/beverage-charts/{id}", beverageChart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(beverageChart.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.intro").value(DEFAULT_INTRO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBeverageChart() throws Exception {
        // Get the beverageChart
        restBeverageChartMockMvc.perform(get("/api/beverage-charts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBeverageChart() throws Exception {
        // Initialize the database
        beverageChartRepository.saveAndFlush(beverageChart);
        int databaseSizeBeforeUpdate = beverageChartRepository.findAll().size();

        // Update the beverageChart
        BeverageChart updatedBeverageChart = beverageChartRepository.findOne(beverageChart.getId());
        updatedBeverageChart
            .name(UPDATED_NAME)
            .intro(UPDATED_INTRO);

        restBeverageChartMockMvc.perform(put("/api/beverage-charts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBeverageChart)))
            .andExpect(status().isOk());

        // Validate the BeverageChart in the database
        List<BeverageChart> beverageChartList = beverageChartRepository.findAll();
        assertThat(beverageChartList).hasSize(databaseSizeBeforeUpdate);
        BeverageChart testBeverageChart = beverageChartList.get(beverageChartList.size() - 1);
        assertThat(testBeverageChart.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBeverageChart.getIntro()).isEqualTo(UPDATED_INTRO);
    }

    @Test
    @Transactional
    public void updateNonExistingBeverageChart() throws Exception {
        int databaseSizeBeforeUpdate = beverageChartRepository.findAll().size();

        // Create the BeverageChart

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBeverageChartMockMvc.perform(put("/api/beverage-charts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beverageChart)))
            .andExpect(status().isCreated());

        // Validate the BeverageChart in the database
        List<BeverageChart> beverageChartList = beverageChartRepository.findAll();
        assertThat(beverageChartList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBeverageChart() throws Exception {
        // Initialize the database
        beverageChartRepository.saveAndFlush(beverageChart);
        int databaseSizeBeforeDelete = beverageChartRepository.findAll().size();

        // Get the beverageChart
        restBeverageChartMockMvc.perform(delete("/api/beverage-charts/{id}", beverageChart.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BeverageChart> beverageChartList = beverageChartRepository.findAll();
        assertThat(beverageChartList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BeverageChart.class);
        BeverageChart beverageChart1 = new BeverageChart();
        beverageChart1.setId(1L);
        BeverageChart beverageChart2 = new BeverageChart();
        beverageChart2.setId(beverageChart1.getId());
        assertThat(beverageChart1).isEqualTo(beverageChart2);
        beverageChart2.setId(2L);
        assertThat(beverageChart1).isNotEqualTo(beverageChart2);
        beverageChart1.setId(null);
        assertThat(beverageChart1).isNotEqualTo(beverageChart2);
    }
}
