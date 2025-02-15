package com.stockspring.service;

import com.stockspring.dto.StockDTO;
import com.stockspring.utility.ApiKey;
import com.stockspring.utility.FinancialUtility;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class ExternalStockApiService {
    @Autowired
    FinancialUtility financialUtility;

    // The API keys are stored in the ApiKey class
    private static final String finnhubKey = ApiKey.getFinnhubAPIKey();
    private static final String newsKey = ApiKey.getNewsAPIKey();
    private static final String fmpKey = ApiKey.getFmpAPIKey();

    // The base URLs for the APIs
    private static final String baseFinnhubURL = "https://finnhub.io/api/v1/";
    private static final String baseNewsURL = "https://newsapi.org/v2/";
    private static final String baseFmpURL = "https://financialmodelingprep.com/api/v3/";

    private final HttpClient httpClient = HttpClient.newHttpClient();


    /**
     * Fetches the latest news related to stocks.
     *
     * @return a string containing the latest news data if the request is successful;
     *         an error message if the request fails
     */
    public String getLatestNews(){
        try {
            HttpRequest getNewsRequest = HttpRequest.newBuilder()
                    .uri(new URI(baseNewsURL + "everything?q=stocks&apiKey=" + newsKey))
                    .build();

            HttpResponse<String> response = httpClient.send(getNewsRequest, HttpResponse.BodyHandlers.ofString());

            // Checking for a successful response
            if (response.statusCode() == 200) {
                return response.body();  // Returning the news data as a string
            } else {
                return "Error: Unable to fetch the latest news. HTTP status code: " + response.statusCode();
            }
        } catch (URISyntaxException | InterruptedException | IOException e) {
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Fetches the latest news related to a specified stock.
     *
     * @param ticker the stock ticker symbol
     *
     * @return a string containing the latest news data of a company if the request is successful;
     * an error message if the request fails
     */
    public String getCompanyNews(String ticker) {
        try {
            HttpRequest getCompanyNewsRequest = HttpRequest.newBuilder()
                    .uri(new URI(baseNewsURL + "everything?q=" + ticker + "&apiKey=" + newsKey))
                    .build();

            HttpResponse<String> response = httpClient.send(getCompanyNewsRequest, HttpResponse.BodyHandlers.ofString());
            // Checking for a successful response
            if (response.statusCode() == 200) {
                return response.body();  // Returning the news data as a string
            } else {
                return "Error: Unable to fetch the latest news. HTTP status code: " + response.statusCode();
            }
        } catch (URISyntaxException | IOException | InterruptedException e) {
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Fetches the stock data for a given symbol.
     *
     * @param symbol the stock symbol
     *
     * @return a {@link StockDTO} object containing the stock data
     */
    public StockDTO fetchStockData(String symbol){
        String profileUrl = baseFmpURL + "profile/" + symbol + "?apikey=" + fmpKey;

        String metricsUrl = baseFmpURL + "key-metrics-ttm/" + symbol + "?apikey=" + fmpKey;

        try {
            HttpRequest getCompanyProfileRequest = HttpRequest.newBuilder()
                    .uri(new URI(profileUrl))
                    .GET()
                    .build();

            HttpRequest getCompanyMetricsRequest = HttpRequest.newBuilder()
                    .uri(new URI(metricsUrl))
                    .GET()
                    .build();

            HttpResponse<String> profileResponseJson = httpClient.send(getCompanyProfileRequest,
                    HttpResponse.BodyHandlers.ofString());

            HttpResponse<String> metricsResponseJson = httpClient.send(getCompanyMetricsRequest,
                    HttpResponse.BodyHandlers.ofString());

            if (profileResponseJson.statusCode() == 429 || metricsResponseJson.statusCode() == 429) {
                throw new RuntimeException("API call limit exceeded. Please try again tomorrow.");
            }

            //Parse the JSON
            JSONArray jsonProfileArray = new JSONArray (profileResponseJson.body());
            JSONArray  jsonMetricsArray = new JSONArray (metricsResponseJson.body());

            if (jsonProfileArray.isEmpty() || jsonMetricsArray.isEmpty()) {
                throw new RuntimeException("No data found for the given stock "+ symbol+ ".");
            }

            JSONObject jsonProfileObject = jsonProfileArray.getJSONObject(0);
            JSONObject jsonMetricsObject = jsonMetricsArray.getJSONObject(0);

            double peRation = financialUtility.roundToTwoDecimals(jsonMetricsObject.optDouble("peRatioTTM", 0.0));
            double dividendYield = financialUtility.roundToTwoDecimals(jsonMetricsObject.optDouble("dividendYieldPercentageTTM", 0.0));

            return StockDTO.builder()
                    .symbol(symbol)
                    .companyName(jsonProfileObject.optString("companyName"))
                    .sector(jsonProfileObject.optString("sector"))
                    .marketCap(jsonMetricsObject.optLong("marketCapTTM", 0))
                    .peRatio(peRation)
                    .dividendYield(dividendYield)
                    .build();

        }
        catch (URISyntaxException | IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error fetching stock data: " + e.getMessage());
        }
    }
}