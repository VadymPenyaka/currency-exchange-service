package projects.currency_exchange_service;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
//
public class AWSRequestHandler implements RequestHandler<Object, String> {

    @Override
    public String handleRequest(Object input, Context context) {
        String apiUrl = "https://api.privatbank.ua/p24api/pubinfo?exchange&coursid=5";
        try {
            URL url = new URL(apiUrl);

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("HTTP GET Request Failed with Error Code: " + responseCode);
            }

            // Read the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            return response.toString();

        } catch (Exception e) {
            return "Error occurred: " + e.getMessage();
        }
    }
}
