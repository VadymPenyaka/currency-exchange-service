package projects.currency_exchange_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import projects.currency_exchange_service.model.CurrencyDTO;
import projects.currency_exchange_service.service.CurrencyService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CurrencyController {

    private static final String CURRENCY_PATH = "/api/v1/currencies";
    private final CurrencyService currencyService;

    @GetMapping(CURRENCY_PATH)
    public List<CurrencyDTO> getAllCurrencies () {
        return currencyService.getAllCurrencies();
    }


}
