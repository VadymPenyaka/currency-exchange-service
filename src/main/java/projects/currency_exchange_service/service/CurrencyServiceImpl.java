package projects.currency_exchange_service.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import projects.currency_exchange_service.model.CurrencyDTO;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;
import com.google.gson.JsonArray;

@Service
public class CurrencyServiceImpl implements  CurrencyService {
    private static final String NBU_RATE_URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";

    private final double DEFAULT_MARKUP_PERCENT = 3;
    private static HttpURLConnection httpURLConnection;
    private static final DecimalFormat decfor = new DecimalFormat("0.00");

    private static URL url;

    static {
        try {
            url = new URL(NBU_RATE_URL);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();// connection to NBU API
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private JsonArray getCurrenciesInJsonArray (HttpURLConnection httpURLConnection) throws IOException {

        JsonElement root = JsonParser.parseReader(new InputStreamReader((InputStream) httpURLConnection.getContent()));
        JsonArray jsonArray = root.getAsJsonArray();
        System.out.println(jsonArray);

        return jsonArray;
    }

    private CurrencyDTO convertNBUJsonObjectToCurrencyDTO (JsonObject jsonObject) {
        return CurrencyDTO.builder()
                .shortName(jsonObject.get("cc").getAsString())
                .fullName(jsonObject.get("txt").getAsString())
                .code(jsonObject.get("r030").getAsInt())
                .buyRate(calculateBuyRate(jsonObject.get("rate").getAsDouble(), DEFAULT_MARKUP_PERCENT))
                .sellRate(calculateSellRate(jsonObject.get("rate").getAsDouble(), DEFAULT_MARKUP_PERCENT))
                .build();
    }

    private double calculateSellRate (double initialRate, double markupPercent) {
        double sellRate = initialRate*(1+markupPercent/100);
        return sellRate-sellRate%0.01;//round double
    }

    private double calculateBuyRate (double initialRate, double markupPercent) {
        double buyRate = initialRate*(1-markupPercent/100);
        return buyRate-buyRate%0.01;//round double
    }



//    private HttpURLConnection getConnection () throws IOException {
//        URL url = new URL(NBU_RATE_URL);
//        HttpURLConnection request = (HttpURLConnection) url.openConnection();
//        return request;
//    }




    @Override
    public List<CurrencyDTO> getAllCurrencies() throws IOException {
        List<CurrencyDTO> currencies = new ArrayList<>();
        JsonArray jsonArray = getCurrenciesInJsonArray(httpURLConnection);

        for (int i =0; i<jsonArray.size(); i++ ) {
            JsonElement element= jsonArray.get(i);
            JsonObject jsonObject = element.getAsJsonObject();
            currencies.add(convertNBUJsonObjectToCurrencyDTO(jsonObject));
        }
        return currencies;
    }

    @Override
    public Optional<CurrencyDTO> getCurrencyByName(String currencyName) {
        return Optional.empty();
    }

    @Override
    public CurrencyDTO addNewCurrency() {
        return null;
    }
}