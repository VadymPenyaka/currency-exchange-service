package projects.currency_exchange_service.service;

import projects.currency_exchange_service.model.CurrencyDTO;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CurrencyService {
    List<CurrencyDTO> updateAllCurrencies() throws IOException;

    List<CurrencyDTO> getAllCurrencies (String fullName, String shortName);

    Optional<CurrencyDTO> updateCurrencyById (UUID id, CurrencyDTO currencyDTO);

    Optional<CurrencyDTO> getCurrencyByID (UUID id);
}
