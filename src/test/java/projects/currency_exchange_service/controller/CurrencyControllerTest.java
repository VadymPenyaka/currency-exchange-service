package projects.currency_exchange_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import projects.currency_exchange_service.entity.Currency;
import projects.currency_exchange_service.mapper.CurrencyMapper;
import projects.currency_exchange_service.model.CurrencyDTO;
import projects.currency_exchange_service.repository.CurrencyRepository;
import projects.currency_exchange_service.service.CurrencyService;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = CurrencyController.class)
class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CurrencyService currencyService;
    @Autowired
    private ObjectMapper objectMapper;
    private static List<Currency> currencies = new ArrayList<>();
    private static List<CurrencyDTO> currencyDTOS = new ArrayList<>();


    private static final String CURRENCY_PATH = "/api/v1/currencies";

    @BeforeEach
    void setUp () {
        currencies = Arrays.asList(
                projects.currency_exchange_service.entity.Currency.builder()
                        .id(UUID.randomUUID())
                        .shortName("USD")
                        .fullName("USA dollar")
                        .code("123")
                        .buyRate(36.2)
                        .sellRate(36.0)
                        .build(),
                Currency.builder()
                        .id(UUID.randomUUID())
                        .shortName("EUR")
                        .fullName("EURO")
                        .code("123")
                        .buyRate(36.2)
                        .sellRate(36.0)
                        .build()
        );

        currencyDTOS = Arrays.asList(
                CurrencyDTO.builder()
                        .id(UUID.randomUUID())
                        .shortName("USD")
                        .fullName("USA dollar")
                        .code("123")
                        .buyRate(36.2)
                        .sellRate(36.0)
                        .build(),
                CurrencyDTO.builder()
                        .id(UUID.randomUUID())
                        .shortName("EUR")
                        .fullName("EURO")
                        .code("123")
                        .buyRate(36.2)
                        .sellRate(36.0)
                        .build()
        );
        
    }


    @Test
    public void getAllCurrencies() throws Exception {
        when(currencyService.getAllCurrencies(any(), any())).thenReturn(currencyDTOS);

        ResultActions response = mockMvc.perform(get(CURRENCY_PATH)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", is(2)));
    }

    @Test
    public void getAllCurrenciesByFullName() throws Exception {
        when(currencyService.getAllCurrencies(any(), any())).thenReturn(Arrays.asList(currencyDTOS.get(0)));

        ResultActions response = mockMvc.perform(get(CURRENCY_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("fullName", "EURO"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", is(1)));
    }

    @Test
    public void getCurrencyByID() throws Exception {
        when(currencyService.getCurrencyByID(any())).thenReturn(Optional.ofNullable(currencyDTOS.get(0)));

        ResultActions response = mockMvc.perform(get(CURRENCY_PATH+"/"+currencyDTOS.get(0).getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(currencyDTOS.get(0))));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName", is(currencyDTOS.get(0).getFullName())));
    }

    @Test
    public void updateCurrencyByID() throws Exception {
        CurrencyDTO currencyDTO = currencyDTOS.get(0);

        when(currencyService.updateCurrencyById(currencyDTO.getId(), currencyDTO)).thenReturn(Optional.of(currencyDTO));

        ResultActions response = mockMvc.perform(put(CURRENCY_PATH+"/"+currencyDTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .param("currency", currencyDTO.toString())
                .content(objectMapper.writeValueAsString(currencyDTO)));

        response.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void getCurrencyByIDNotFoundException() throws Exception {
        when(currencyService.getCurrencyByID(any())).thenReturn(Optional.ofNullable(null));

        ResultActions response = mockMvc.perform(get(CURRENCY_PATH+"/"+currencyDTOS.get(0).getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(currencyDTOS.get(0))));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}