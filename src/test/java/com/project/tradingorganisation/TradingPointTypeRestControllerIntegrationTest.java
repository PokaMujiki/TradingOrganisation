package com.project.tradingorganisation;

import com.project.tradingorganisation.model.TradingPointType;
import com.project.tradingorganisation.repository.TradingPointTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.project.TestUtils.asJsonString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TradingPointTypeRestControllerIntegrationTest {
    @Autowired
    private TradingPointTypeRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void clearDb() {
        repository.deleteAll();
    }

    @Test
    void givenTradingPointTypes_whenGetAllTradingPointTypes_thenRetrieveAllTypes() throws Exception {
        repository.save(new TradingPointType("first-type"));
        repository.save(new TradingPointType("second-type"));

        this.mockMvc
                .perform(get("/trading-point-types")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].type", is("first-type")))
                .andExpect(jsonPath("$[1].type", is("second-type")))
                .andExpect(jsonPath("$", hasSize(2)))
                .andReturn();
    }

    @Test
    void givenTradingPointType_whenGetTradingPointType_thenRetrieveOneType() throws Exception {
        repository.save(new TradingPointType(1L,"first-type"));

        this.mockMvc
                .perform(get("/trading-point-types/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.type", is("first-type")))
                .andReturn();
    }

    @Test
    void whenGetTradingPointTypeWithNonExistentId_thenReturn404() throws Exception {
        MvcResult result = this.mockMvc
                .perform(get("/trading-point-types/123")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();

        assertThat(result.getResponse().getContentAsString())
                .isEqualTo(String.format("Trading point type with id %d doesn't exist", 123));
    }

    @Test
    void whenPostTradingPointTypeWithNullId_thenSaveInDatabaseAndReturnIt() throws Exception {
        TradingPointType toPost = new TradingPointType("first-type");

        this.mockMvc
                .perform(post("/trading-point-types")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(toPost))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.type", is("first-type")))
                .andReturn();

        List<TradingPointType> types = repository.findAll();

        // repository asserts
        assertThat(types).hasSize(1);
        assertThat(types.get(0)).isInstanceOf(TradingPointType.class);
        assertThat(types.get(0).getType()).isEqualTo("first-type");
    }

    @Test
    void whenPostTradingPointTypeWithExistingId_thenDontSaveInDatabaseAndReturnConflict() throws Exception {
        repository.save(new TradingPointType(1L, "first"));

        TradingPointType toPost = new TradingPointType(1L, "second-type");

        MvcResult result = this.mockMvc
                .perform(post("/trading-point-types")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(toPost))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict())
                .andReturn();

        List<TradingPointType> types = repository.findAll();

        // response assert
        assertThat(result.getResponse().getContentAsString())
                .isEqualTo(String.format("Trading point type with id \"%d\" is already exists." +
                        " Don't specify id in POST methods", toPost.getId()));

        // repository assert
        assertThat(types).hasSize(1);
    }

    @Test
    void whenPostTradingPointTypeWithExistingName_thenDontSaveInDatabaseAndReturnConflict() throws Exception {
        repository.save(new TradingPointType("first-type"));

        TradingPointType toPost = new TradingPointType("first-type");

        MvcResult result = this.mockMvc
                .perform(post("/trading-point-types")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(toPost))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict())
                .andReturn();

        List<TradingPointType> types = repository.findAll();

        // response assert
        assertThat(result.getResponse().getContentAsString())
                .isEqualTo( String.format("Trading point type name \"%s\" is already taken", toPost.getType()));

        // repository assert
        assertThat(types).hasSize(1);
    }

    @Test
    void whenPostTradingPointTypeWithNotNullId_thenIgnoreIdAndSaveInDatabaseAndReturn() throws Exception {
        TradingPointType toPost = new TradingPointType(123L, "first-type");

        this.mockMvc
                .perform(post("/trading-point-types")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(toPost))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.type", is("first-type")))
                .andExpect(jsonPath("$.id", is(not(123L))))
                .andReturn();

        List<TradingPointType> types = repository.findAll();

        // repository asserts
        assertThat(types).hasSize(1);
        assertThat(types.get(0).getType()).isEqualTo("first-type");
        assertThat(types.get(0).getId()).isNotEqualTo(123L);
    }

    @Test
    void whenPutTradingPointType_thenReplaceInDatabaseAndReturn() throws Exception {
        repository.save(new TradingPointType("first-type"));

        TradingPointType toPut = new TradingPointType("replaced");

        for (int i = 0; i < 2; i++) {
            this.mockMvc
                    .perform(put("/trading-point-types/1")
                            .accept(MediaType.APPLICATION_JSON)
                            .content(asJsonString(toPut))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.type", is("replaced")))
                    .andExpect(jsonPath("$.id", is(1)))
                    .andReturn();

            List<TradingPointType> types = repository.findAll();

            // repository asserts
            assertThat(types).hasSize(1);
            assertThat(types.get(0).getType()).isEqualTo("replaced");
            assertThat(types.get(0).getId()).isEqualTo(1L);
        }
    }

    @Test
    void whenPutTradingPointTypeNewInstance_thenSaveInDatabaseAndReturn() throws Exception {
        TradingPointType toPut = new TradingPointType("new");

        for (int i = 0; i < 2; i++) {
            this.mockMvc
                    .perform(put("/trading-point-types/1")
                            .accept(MediaType.APPLICATION_JSON)
                            .content(asJsonString(toPut))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.type", is("new")))
                    .andExpect(jsonPath("$.id", is(1)))
                    .andReturn();

            List<TradingPointType> types = repository.findAll();

            // repository asserts
            assertThat(types).hasSize(1);
            assertThat(types.get(0).getType()).isEqualTo("new");
            assertThat(types.get(0).getId()).isEqualTo(1L);
        }
    }

    @Test
    void whenPutTradingPointTypeWithAlreadyExistentName_thenDontSaveInDatabaseAndReturn409() throws Exception {
        repository.save(new TradingPointType("new"));
        repository.save(new TradingPointType("old"));
        repository.flush();

        TradingPointType toPut = new TradingPointType("new");

        for (int i = 0; i < 2; i++) {
            MvcResult result = this.mockMvc
                    .perform(put("/trading-point-types/2")
                            .accept(MediaType.APPLICATION_JSON)
                            .content(asJsonString(toPut))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isConflict())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            List<TradingPointType> types = repository.findAll();

            // response assert
            assertThat(result.getResponse().getContentAsString())
                    .isEqualTo(String.format("Trading point type name \"%s\" is already taken", toPut.getType()));

            // repository asserts
            assertThat(types).hasSize(2);
        }
    }

    @Test
    void whenPutNewTradingPointTypeWithAlreadyExistentName_thenDontSaveInDatabaseAndReturn409() throws Exception {
        repository.save(new TradingPointType("new"));
        repository.save(new TradingPointType("old"));

        TradingPointType toPut = new TradingPointType("new");

        for (int i = 0; i < 2; i++) {
            MvcResult result = this.mockMvc
                    .perform(put("/trading-point-types/212")
                            .accept(MediaType.APPLICATION_JSON)
                            .content(asJsonString(toPut))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isConflict())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            List<TradingPointType> types = repository.findAll();

            // response assert
            assertThat(result.getResponse().getContentAsString())
                    .isEqualTo(String.format("Trading point type name \"%s\" is already taken", toPut.getType()));

            // repository asserts
            assertThat(types).hasSize(2);
        }
    }
}
