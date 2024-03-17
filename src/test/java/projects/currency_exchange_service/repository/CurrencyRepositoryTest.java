package projects.currency_exchange_service.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import projects.currency_exchange_service.entity.Currency;

import java.util.List;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CurrencyRepositoryTest {
    @Autowired
    CurrencyRepository currencyRepository;

    @Test
    void saveCurrencyTest () {
        Currency currency = Currency.builder()
                .id(UUID.randomUUID())
                .shortName("USD")
                .fullName("USA dollar")
                .code("123")
                .buyRate(36.2)
                .sellRate(36.0)
                .build();

        Currency savedCurrency = currencyRepository.save(currency);

        assertThat(savedCurrency).isNotNull();
        assertThat(savedCurrency.getId()).isEqualTo(currency.getId());
    }

    @Test
    void getCurrencyByIdTest () {
        Currency savedCurrency = currencyRepository.save(Currency.builder()
                .id(UUID.randomUUID())
                .shortName("USD")
                .fullName("USA dollar")
                .code("123")
                .buyRate(36.2)
                .sellRate(36.0)
                .build());

        Currency foundCurrency = currencyRepository.findById(savedCurrency.getId()).get();

        assertThat(foundCurrency).isEqualTo(savedCurrency);
    }

    @Test
    void getCurrencyByFullNameTest () {
        Currency savedCurrency = currencyRepository.save(Currency.builder()
                .id(UUID.randomUUID())
                .shortName("USD")
                .fullName("USA dollar")
                .code("123")
                .buyRate(36.2)
                .sellRate(36.0)
                .build());

        List<Currency> foundCurrency = currencyRepository.findAllByFullName(savedCurrency.getFullName());

        assertThat(foundCurrency.size()).isNotEqualTo(0);
        assertThat(foundCurrency.get(0).getFullName()).isEqualTo(savedCurrency.getFullName());
    }

    @Test
    void getCurrencyByShortNameTest () {
        Currency savedCurrency = currencyRepository.save(Currency.builder()
                .id(UUID.randomUUID())
                .shortName("USD")
                .fullName("USA dollar")
                .code("123")
                .buyRate(36.2)
                .sellRate(36.0)
                .build());

        List<Currency> foundCurrency = currencyRepository.findAllByShortName(savedCurrency.getShortName());

        assertThat(foundCurrency.size()).isEqualTo(1);
        assertThat(foundCurrency.get(0).getShortName()).isEqualTo(savedCurrency.getShortName());
    }
}
