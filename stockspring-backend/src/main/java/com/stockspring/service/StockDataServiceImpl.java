package com.stockspring.service;

import com.stockspring.utility.ApiKey;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Implementation of the StockDataService interface.
 * Provides methods to fetch stock-related data from external APIs.
 */
@Service
public class StockDataServiceImpl implements StockDataService{
    private static final String finnhubKey = ApiKey.getFinnhubAPIKey();
    private static final String newsKey = ApiKey.getNewsAPIKey();
    private static final String baseFinnhubURL = "https://finnhub.io/api/v1/";
    private static final String baseNewsURL = "https://newsapi.org/v2/";

    /**
     * Fetches the latest news related to stocks.
     *
     * @return a string containing the latest news data if the request is successful;
     *         an error message if the request fails
     */
    @Override
    public String getLatestNews(){
        try {
            HttpClient httpClient = HttpClient.newHttpClient();

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
        } catch (URISyntaxException | IOException | InterruptedException e) {
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Fetches the latest news related to a specified stock.
     *
     * @param ticker
     * @return a string containing the latest news data of a company if the request is successful;
     * an error message if the request fails
     */
    @Override
    public String getCompanyNews(String ticker) {
        try {
        HttpClient httpClient = HttpClient.newHttpClient();

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
}
