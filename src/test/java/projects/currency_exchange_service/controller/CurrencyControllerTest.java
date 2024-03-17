package projects.currency_exchange_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import projects.currency_exchange_service.entity.Currency;
import projects.currency_exchange_service.mapper.CurrencyMapper;
import projects.currency_exchange_service.model.CurrencyDTO;
import projects.currency_exchange_service.repository.CurrencyRepository;
import projects.currency_exchange_service.service.CurrencyService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
                        .shortName("USD")
                        .fullName("EURO")
                        .code("123")
                        .buyRate(36.2)
                        .sellRate(36.0)
                        .build()
        );
        
    }


    @Test
    void getAllCurrencies() {

    }

    @Test
    void getCurrencyByID() {
    }

    @Test
    void updateCurrencyByID() {
    }
}