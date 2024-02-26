package projects.currency_exchange_service.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import projects.currency_exchange_service.model.CurrencyDTO;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CurrencyServiceImplTest {

    @Autowired
    CurrencyService currencyService;

    @Test
    void getAllCurrencies() throws IOException {

    }

    @Test
    void getCurrencyByName() {
    }

    @Test
    void addNewCurrency() {
    }
}