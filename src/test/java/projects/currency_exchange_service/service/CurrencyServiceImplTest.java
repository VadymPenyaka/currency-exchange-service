package projects.currency_exchange_service.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import projects.currency_exchange_service.model.CurrencyDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CurrencyServiceImplTest {

    @Autowired
    CurrencyService currencyService;

    @Test
    void getAllCurrencies() throws IOException {
        List<CurrencyDTO> currencies = currencyService.getAllCurrencies();

        assertThat(currencies).isNotNull();
        assertThat(currencies.size()).isNotEqualTo(0);
    }

    @Test
    void getCurrencyByName() {
    }

    @Test
    void addNewCurrency() {
    }
}