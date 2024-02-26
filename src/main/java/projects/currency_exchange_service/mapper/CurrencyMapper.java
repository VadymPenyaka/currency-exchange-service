package projects.currency_exchange_service.mapper;

import org.mapstruct.Mapper;
import projects.currency_exchange_service.entity.Currency;
import projects.currency_exchange_service.model.CurrencyDTO;

@Mapper
public interface CurrencyMapper {
    Currency currencyDtoToCurrency (CurrencyDTO currencyDTO);
    CurrencyDTO currencyToCurrencyDto (Currency currency);
}
