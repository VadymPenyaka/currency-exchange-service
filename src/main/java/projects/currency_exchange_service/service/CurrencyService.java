package projects.currency_exchange_service.service;

import projects.currency_exchange_service.entity.Currency;
import projects.currency_exchange_service.model.CurrencyDTO;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface CurrencyService {
    void updateAllCurrencies() throws IOException;

    Optional<CurrencyDTO> getCurrencyByName (String currencyName);

    CurrencyDTO addNewCurrency ();

//  List<Currency> getAllCurrencies ();
}
