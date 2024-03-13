package projects.currency_exchange_service.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projects.currency_exchange_service.entity.Currency;
import projects.currency_exchange_service.mapper.CurrencyMapperImpl;
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
    CurrencyMapperImpl currencyMapper;

    static List<Currency> currencies = new ArrayList<>();
    static List<CurrencyDTO> currencyDTOS = new ArrayList<>();
    @BeforeAll
    static void setUp () {
        currencies = Arrays.asList(
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
    void updateAllCurrenciesTest() throws IOException {
        when(currencyRepository.saveAll(any())).thenReturn(currencies);

        List<CurrencyDTO> updatedCurrencies = currencyService.updateAllCurrencies();
        assertThat(updatedCurrencies).isNotNull();
        assertThat(updatedCurrencies.size()).isNotEqualTo(0);
    }

    @Test
    void getCurrencyByName() {
        when(currencyRepository.findAllByFullName(any())).thenReturn(currencies);
        List<CurrencyDTO> foundCurrencies = currencyService.getCurrencyByFullName("USA");

        assertThat(foundCurrencies).isNotNull();
        assertThat(foundCurrencies.size()).isEqualTo(2);
    }

    @Test
    void getAllCurrencies() {
        when(currencyRepository.findAll()).thenReturn(currencies);

        List<CurrencyDTO> foundCurrencyDTOS = currencyService.getAllCurrencies();

        assertThat(foundCurrencyDTOS).isNotNull();
        assertThat(foundCurrencyDTOS.size()).isEqualTo(2);
    }

    @Test
    void updateCurrencyById() {
        Currency currencyToUpdate = currencies.get(0);
        CurrencyDTO currencyDTO = currencyDTOS.get(0);

        when(currencyRepository.findById(any())).thenReturn(Optional.of(currencyToUpdate));
        currencyToUpdate.setFullName("updated");
        when(currencyRepository.save(any())).thenReturn(currencyToUpdate);
        when(currencyMapper.currencyToCurrencyDto(any())).thenReturn(currencyDTO);

        CurrencyDTO updatedCurrencyDTO = currencyService.updateCurrencyById(currencyToUpdate.getId(), currencyDTO).get();

        assertThat(updatedCurrencyDTO).isNotNull();
        assertThat(updatedCurrencyDTO.getFullName()).isEqualTo(currencyToUpdate.getFullName());
    }

    @Test
    void getCurrencyByShortName () {
        when(currencyRepository.findAllByShortName(any())).thenReturn(currencies);

        List<CurrencyDTO> foundCurrencyDTOS = currencyService.getCurrencyByShortName(currencies.get(0).getShortName());

        assertThat(foundCurrencyDTOS).isNotNull();
        assertThat(foundCurrencyDTOS.size()).isEqualTo(2);

    }
}