package projects.currency_exchange_service.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import projects.currency_exchange_service.model.CurrencyDTO;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import com.google.gson.JsonArray;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements  CurrencyService {
    private static final String NBU_RATE_URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";

    private final double DEFAULT_MARKUP_PERCENT = 3;

    private List<CurrencyDTO> currencies = new ArrayList<>();


    @Override
//    @Scheduled(cron = "0 0 9-18 * * *")
    @Scheduled(cron = "*/5 * * * * *")
    public List<CurrencyDTO> updateAllCurrencies() throws IOException {
        currencies.removeAll(currencies);
        JsonArray jsonArray = getCurrenciesInJsonArray();

        for (int i =0; i<jsonArray.size(); i++ ) {
            JsonElement element= jsonArray.get(i);
            JsonObject jsonObject = element.getAsJsonObject();
            currencies.add(convertNBUJsonObjectToCurrencyDTO(jsonObject));
        }

        return currencies;
    }

    public List<CurrencyDTO> getCurrencyByFullName(String currencyName) {
        return currencies.stream().filter(currencyDTO -> currencyDTO.getFullName().equals(currencyName)).toList();
    }
    public List<CurrencyDTO> getCurrencyByShortName(String shortName) {
        return currencies.stream().filter(currencyDTO -> currencyDTO.getShortName().equals(shortName)).toList();
    }

    @Override
    public List<CurrencyDTO> getAllCurrencies(String fullName, String shortName) {
        if (StringUtils.hasText(fullName) && !StringUtils.hasText(shortName)) {
            return getCurrencyByFullName(fullName);
        } else if (!StringUtils.hasText(fullName) && StringUtils.hasText(shortName)) {
            return getCurrencyByShortName(shortName);
            }
            return currencies;
    }




    @Override
    public Optional<CurrencyDTO> updateCurrencyById(UUID id, CurrencyDTO currencyDTO) {
        AtomicReference<Optional<CurrencyDTO>> atomicReference = new AtomicReference<>();

        getCurrencyByID(id).ifPresentOrElse(foundCurrency->{
                foundCurrency.setBuyRate(currencyDTO.getBuyRate());
                foundCurrency.setCode(currencyDTO.getCode());
                foundCurrency.setFullName(currencyDTO.getFullName());
                foundCurrency.setShortName(currencyDTO.getShortName());
                foundCurrency.setSellRate(currencyDTO.getSellRate());
                atomicReference.set(Optional.of(foundCurrency));
                currencies.add(foundCurrency);
            }, ()-> atomicReference.set(Optional.empty()));

        return atomicReference.get();
    }

    @Override
    public Optional<CurrencyDTO> getCurrencyByID(UUID id) {
        return Optional.ofNullable(currencies.stream()
                .filter(currencyDTO -> currencyDTO.getId().equals(id))
                .findFirst().orElse(null));
    }

    private JsonArray getCurrenciesInJsonArray () throws IOException {
        JsonElement root = JsonParser.parseReader(new InputStreamReader((InputStream) getConnection().getContent()));
        JsonArray jsonArray = root.getAsJsonArray();

        return jsonArray;
    }

    private CurrencyDTO convertNBUJsonObjectToCurrencyDTO (JsonObject jsonObject) {
        return CurrencyDTO.builder()
                .shortName(jsonObject.get("cc").getAsString())
                .fullName(jsonObject.get("txt").getAsString())
                .code(jsonObject.get("r030").getAsString())
                .buyRate(calculateBuyRate(jsonObject.get("rate").getAsDouble(), DEFAULT_MARKUP_PERCENT))
                .sellRate(calculateSellRate(jsonObject.get("rate").getAsDouble(), DEFAULT_MARKUP_PERCENT))
                .build();
    }

    private double calculateSellRate (double initialRate, double markupPercent) {
        double sellRate = initialRate*(1+markupPercent/100);
        return sellRate-sellRate%0.0001;//round double
    }

    private double calculateBuyRate (double initialRate, double markupPercent) {
        double buyRate = initialRate*(1-markupPercent/100);
        return buyRate-buyRate%0.0001;//round double
    }

    private HttpURLConnection getConnection () throws IOException {
        URL url = new URL(NBU_RATE_URL);
        return (HttpURLConnection) url.openConnection();
    }
}
