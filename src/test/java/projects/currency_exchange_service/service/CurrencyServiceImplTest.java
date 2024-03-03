package projects.currency_exchange_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import projects.currency_exchange_service.entity.Currency;
import projects.currency_exchange_service.mapper.CurrencyMapper;
import projects.currency_exchange_service.model.CurrencyDTO;
import projects.currency_exchange_service.repository.CurrencyRepository;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceImplTest {
    @Mock
    CurrencyRepository currencyRepository;

    @InjectMocks
    CurrencyServiceImpl currencyService;

    @Mock
    CurrencyMapper currencyMapper;

    @Test
    void updateAllCurrenciesTest() throws IOException {
        List<Currency> currencies = Arrays.asList(
                Currency.builder()
                        .id(UUID.randomUUID())
                        .shortName("USD")
                        .fullName("USA dollar")
                        .code("123")
                        .buyRate(36.2)
                        .sellRate(36.0)
                        .build(),
                Currency.builder()
                        .id(UUID.randomUUID())
                        .shortName("USD")
                        .fullName("USA dollar")
                        .code("123")
                        .buyRate(36.2)
                        .sellRate(36.0)
                        .build()
        );
        when(currencyRepository.saveAll(any())).thenReturn(currencies);

        //doNothing().when(currencyRepository).deleteAll();
        List<CurrencyDTO> updatedCurrencies = currencyService.updateAllCurrencies();
        assertThat(updatedCurrencies).isNotNull();
        assertThat(updatedCurrencies.size()).isNotEqualTo(0);
    }

    @Test
    void getCurrencyByName() {
    }

    @Test
    void getAllCurrencies() {
        List<Currency> currencies = Arrays.asList(
                Currency.builder()
                        .id(UUID.randomUUID())
                        .shortName("USD")
                        .fullName("USA dollar")
                        .code("123")
                        .buyRate(36.2)
                        .sellRate(36.0)
                        .build(),
                Currency.builder()
                        .id(UUID.randomUUID())
                        .shortName("USD")
                        .fullName("USA dollar")
                        .code("123")
                        .buyRate(36.2)
                        .sellRate(36.0)
                        .build()
        );

        when(currencyRepository.findAll()).thenReturn(currencies);

        List<CurrencyDTO> foundCurrencyDTOS = currencyService.getAllCurrencies();

        assertThat(foundCurrencyDTOS).isNotNull();
        assertThat(foundCurrencyDTOS.size()).isEqualTo(2);
    }

    @Test
    void updateCurrencyById() {
        Currency currencyToUpdate = Currency.builder()
                .id(UUID.randomUUID())
                .shortName("USD")
                .fullName("USA dollar")
                .code("123")
                .buyRate(36.2)
                .sellRate(36.0)
                .build();

        CurrencyDTO currencyDTO = CurrencyDTO.builder()
                .id(currencyToUpdate.getId())
                .shortName("USD")
                .fullName("USA dollar")
                .code("123")
                .buyRate(36.2)
                .sellRate(36.0)
                .build();

        when(currencyRepository.findById(any())).thenReturn(Optional.of(currencyToUpdate));
        currencyToUpdate.setFullName("updated");
        when(currencyRepository.save(any())).thenReturn(currencyToUpdate);


        CurrencyDTO updatedCurrencyDTO = currencyService.updateCurrencyById(currencyToUpdate.getId(), currencyDTO).get();

        assertThat(updatedCurrencyDTO).isNotNull();
        assertThat(updatedCurrencyDTO.getFullName()).isEqualTo(currencyToUpdate.getFullName());
    }
}