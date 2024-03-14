package projects.currency_exchange_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projects.currency_exchange_service.model.CurrencyDTO;
import projects.currency_exchange_service.service.CurrencyService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CurrencyController {

    private static final String CURRENCY_PATH = "/api/v1/currencies";
    private static final String CURRENCY_PATH_ID = CURRENCY_PATH + "/{id}";
    private final CurrencyService currencyService;

    @GetMapping(CURRENCY_PATH)
    public List<CurrencyDTO> getAllCurrencies (@RequestParam(required = false) String fullName, @RequestParam(required = false) String shortName) {
        return currencyService.getAllCurrencies(fullName, shortName);
    }

    @GetMapping(value = CURRENCY_PATH_ID)
    public CurrencyDTO getCurrencyByID (@PathVariable("id") UUID id) {
        return currencyService.getCurrencyByID(id).get();
    }

    @PutMapping(value = CURRENCY_PATH_ID)
    public ResponseEntity updateCurrencyByID (@PathVariable UUID id, @RequestBody CurrencyDTO currency) {
        currencyService.updateCurrencyById(id, currency);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
