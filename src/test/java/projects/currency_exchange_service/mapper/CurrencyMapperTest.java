package projects.currency_exchange_service.mapper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import projects.currency_exchange_service.entity.Currency;
import projects.currency_exchange_service.model.CurrencyDTO;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CurrencyMapperTest {

    private final CurrencyMapper currencyMapper = new CurrencyMapperImpl();


    @Test
    public void currencyToCurrencyDtoTest () {
        Currency currency = Currency.builder()
                .id(UUID.randomUUID())
                .shortName("USD")
                .fullName("USA dollar")
                .code("123")
                .buyRate(36.2)
                .sellRate(36.0)
                .build();

        CurrencyDTO currencyDTO = currencyMapper.currencyToCurrencyDto(currency);

        assertThat(currencyDTO).isNotNull();
        assertThat(currencyDTO.getId()).isEqualTo(currency.getId());
    }

    @Test
    public void currencyDtoTOCurrencyTest () {
        CurrencyDTO currencyDto = CurrencyDTO.builder()
                .id(UUID.randomUUID())
                .shortName("USD")
                .fullName("USA dollar")
                .code("123")
                .buyRate(36.2)
                .sellRate(36.0)
                .build();

        Currency currency = currencyMapper.currencyDtoToCurrency(currencyDto);

        assertThat(currency).isNotNull();
        assertThat(currencyDto.getId()).isEqualTo(currency.getId());
    }
}
