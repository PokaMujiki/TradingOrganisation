package com.project.tradingorganisation;

import com.project.tradingorganisation.model.TradingPoint;
import com.project.tradingorganisation.model.TradingPointType;
import com.project.tradingorganisation.repository.TradingPointRepository;
import com.project.tradingorganisation.repository.TradingPointTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class TradingPointRestControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TradingPointRepository repository;

    @Autowired
    private TradingPointTypeRepository tradingPointTypeRepository;

    @BeforeEach
    void setupDb() {
        tradingPointTypeRepository.deleteAll();
        tradingPointTypeRepository.saveAll(List.of(new TradingPointType("store"), new TradingPointType("kiosk")));

        repository.deleteAll();
    }

    @Test
    void givenTwoTradingPoints_whenGetAllTradingPoints_thenReturnAllTradingPoints() throws Exception {
        TradingPointType store = tradingPointTypeRepository.findByType("store").orElseThrow();
        TradingPointType kiosk = tradingPointTypeRepository.findByType("kiosk").orElseThrow();

        repository.save(new TradingPoint(store, 1, 100, 10));
        repository.save(new TradingPoint(kiosk, 2, 200, 20));

        this.mockMvc
                .perform(get("/trading-points")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].size", is(1)))
                .andExpect(jsonPath("$[0].rentPayment", is(100)))
                .andExpect(jsonPath("$[0].utilitiesPayment", is(10)))
                .andExpect(jsonPath("$[1].size", is(2)))
                .andExpect(jsonPath("$[1].rentPayment", is(200)))
                .andExpect(jsonPath("$[1].utilitiesPayment", is(20)))
                .andExpect(jsonPath("$", hasSize(2)))
                .andReturn();
    }

    @Test
    void givenTradingPoints_whenGetTradingPointById_thenReturnTradingPoint() throws Exception {
        TradingPointType store = tradingPointTypeRepository.findByType("store").orElseThrow();
        TradingPointType kiosk = tradingPointTypeRepository.findByType("kiosk").orElseThrow();

        repository.save(new TradingPoint(store, 1, 100, 10));
        repository.save(new TradingPoint(kiosk, 2, 200, 20));

        this.mockMvc
                .perform(get("/trading-points/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size", is(1)))
                .andExpect(jsonPath("$.rentPayment", is(100)))
                .andExpect(jsonPath("$.utilitiesPayment", is(10)))
                .andReturn();
    }

    @Test
    void whenGetTradingPointTypeWithNonExistentId_thenReturn404() throws Exception {
        MvcResult result = this.mockMvc
                .perform(get("/trading-points/123")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();

        assertThat(result.getResponse().getContentAsString())
                .isEqualTo(String.format("Trading point with id %d doesn't exist", 123));
    }

    @Test
    void whenPostTradingPoint_thenSaveInDbAndReturnIt() throws Exception {
        TradingPointType store = tradingPointTypeRepository.findByType("store").orElseThrow();

        repository.save(new TradingPoint(store, 1, 100, 10));

        this.mockMvc
                .perform(post("/trading-points/")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size", is(1)))
                .andExpect(jsonPath("$.rentPayment", is(100)))
                .andExpect(jsonPath("$.utilitiesPayment", is(10)))
                .andReturn();

        assertThat(repository.findAll()).hasSize(1);
    }

    @Test
    void whenPostTradingPointWithUnnecessaryFields_thenSaveInDbAndReturnIt() throws Exception {
        TradingPointType store = tradingPointTypeRepository.findByType("store").orElseThrow();

        repository.save(new TradingPoint(store));

        this.mockMvc
                .perform(post("/trading-points/")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size", is(1)))
                .andExpect(jsonPath("$.rentPayment", is(100)))
                .andExpect(jsonPath("$.utilitiesPayment", is(10)))
                .andReturn();

        // todo checks refactor

        assertThat(repository.findAll()).hasSize(1);
    }
}
