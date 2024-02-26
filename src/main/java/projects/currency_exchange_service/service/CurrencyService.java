package projects.currency_exchange_service.service;

import projects.currency_exchange_service.model.CurrencyDTO;

import java.io.IOException;
import java.util.Optional;

public interface CurrencyService {
    public void updateAllCurrencies() throws IOException;

    public Optional<CurrencyDTO> getCurrencyByName (String currencyName);

    public CurrencyDTO addNewCurrency ();
}
