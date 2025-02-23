package com.stockspring.service;

import com.stockspring.dto.DividendDTO;
import com.stockspring.dto.StockDTO;
import com.stockspring.dto.StockDetailsDTO;
import com.stockspring.entity.Dividend;
import com.stockspring.entity.Stock;
import com.stockspring.entity.StockDetails;
import com.stockspring.repository.DividendRepository;
import com.stockspring.repository.StockDetailsRepository;
import com.stockspring.repository.StockRepository;
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
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExternalStockApiService {
    @Autowired
    FinancialUtility financialUtility;

    @Autowired
    StockRepository stockRepository;

    @Autowired
    StockDetailsRepository stockDetailsRepository;

    @Autowired
    DividendRepository dividendRepository;

    // The API keys are stored in the ApiKey class
    private static final String newsKey = ApiKey.getNewsAPIKey();
    private static final String fmpKey = ApiKey.getFmpAPIKey();

    // The base URLs for the APIs
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

        String dividendUrl = baseFmpURL + "historical-price-full/stock_dividend/" + symbol +"?apikey=" + fmpKey;

        try {
            HttpRequest getCompanyProfileRequest = HttpRequest.newBuilder()
                    .uri(new URI(profileUrl))
                    .GET()
                    .build();

            HttpRequest getCompanyMetricsRequest = HttpRequest.newBuilder()
                    .uri(new URI(metricsUrl))
                    .GET()
                    .build();

            HttpRequest getDividendHistory = HttpRequest.newBuilder()
                    .uri(new URI(dividendUrl))
                    .GET()
                    .build();

            HttpResponse<String> profileResponseJson = httpClient.send(getCompanyProfileRequest,
                    HttpResponse.BodyHandlers.ofString());

            HttpResponse<String> metricsResponseJson = httpClient.send(getCompanyMetricsRequest,
                    HttpResponse.BodyHandlers.ofString());

            HttpResponse<String> dividendResponseJson = httpClient.send(getDividendHistory,
                    HttpResponse.BodyHandlers.ofString());

            if (profileResponseJson.statusCode() == 429 || metricsResponseJson.statusCode() == 429) {
                throw new RuntimeException("API call limit exceeded. Please try again tomorrow.");
            }

            //Parse the JSON
            JSONArray jsonProfileArray = new JSONArray(profileResponseJson.body());
            JSONArray  jsonMetricsArray = new JSONArray(metricsResponseJson.body());

            if (jsonProfileArray.isEmpty() || jsonMetricsArray.isEmpty()) {
                throw new RuntimeException("No data found for the given stock "+ symbol+ ".");
            }

            JSONObject jsonProfileObject = jsonProfileArray.getJSONObject(0);
            JSONObject jsonMetricsObject = jsonMetricsArray.getJSONObject(0);
            JSONObject jsonDividendObject = new JSONObject(dividendResponseJson.body());

            JSONArray dividendArray = jsonDividendObject.getJSONArray("historical");

            double dividendGrowth = financialUtility.roundToTwoDecimals(financialUtility.dividendGrowth(dividendArray));
            double peRation = financialUtility.roundToTwoDecimals(jsonMetricsObject.optDouble("peRatioTTM", 0.0));
            double dividendYield = financialUtility.roundToTwoDecimals(jsonMetricsObject.optDouble("dividendYieldPercentageTTM", 0.0));
            double pbRatio = financialUtility.roundToTwoDecimals(jsonMetricsObject.optDouble("pbRatioTTM", 0.0));
            double payoutRatio = financialUtility.roundToTwoDecimals(jsonMetricsObject.optDouble("payoutRatioTTM", 0.0));
            double netDebtEBITDA = financialUtility.roundToTwoDecimals(jsonMetricsObject.optDouble("netDebtToEBITDATTM", 0.0));
            double debtToMarketCap = financialUtility.roundToTwoDecimals(jsonMetricsObject.optDouble("debtToMarketCapTTM", 0.0));

            //Save in the Stock entity
            Stock stock = new Stock();
            stock.setSymbol(symbol);
            stock.setCompanyName(jsonProfileObject.optString("companyName", ""));
            stock.setSector(jsonProfileObject.optString("sector", ""));
            stock.setIndustry(jsonProfileObject.optString("industry", ""));
            stock.setDescription(jsonProfileObject.optString("description", ""));

            //Save Stock in Database
            stockRepository.save(stock);

            //Save in the StockDetails entity
            StockDetails stockDetails = new StockDetails();
            stockDetails.setLastUpdated(LocalDateTime.now());
            stockDetails.setPrice(jsonProfileObject.optDouble("price", 0.0));
            stockDetails.setBeta(jsonProfileObject.optDouble("beta", 0.0));
            stockDetails.setPeRatio(peRation);
            stockDetails.setPbRatio(pbRatio);
            stockDetails.setEps(jsonMetricsObject.optDouble("netIncomePerShareTTM", 0.0));
            stockDetails.setMarketCap(jsonProfileObject.optLong("mktCap", 0));
            stockDetails.setFreeCashFlow(jsonMetricsObject.optDouble("freeCashFlowYieldTTM", 0.0));
            stockDetails.setDebtToEBITDA(netDebtEBITDA);
            stockDetails.setDebtToMarketCap(debtToMarketCap);
            stockDetails.setStock(stock);

            //Save StockDetails to Database
            stockDetailsRepository.save(stockDetails);

            //Save in the Dividend entity
            Dividend dividend = new Dividend();
            dividend.setDividendYield(dividendYield);
            dividend.setExDividendDate(dividendArray.getJSONObject(0).optString("date", ""));
            dividend.setPaymentDate(dividendArray.getJSONObject(0).optString("paymentDate", ""));
            dividend.setDividendPerShare(dividendArray.getJSONObject(0).optDouble("dividend", 0.0));
            dividend.setPayoutRatio(payoutRatio);
            dividend.setDividendGrowthFiveYears(dividendGrowth);
            dividend.setStock(stock);

            //Save Dividend to Database
            dividendRepository.save(dividend);

            //Save StockDetails in Database
            return StockDTO.builder()
                    .stockId(stock.getStockId())
                    .symbol(stock.getSymbol())
                    .companyName(stock.getCompanyName())
                    .sector(stock.getSector())
                    .industry(stock.getIndustry())
                    .description(stock.getDescription())
                    .stockDetails(List.of(StockDetailsDTO.builder()
                            .stockDetailsId(stockDetails.getStockDetailsId())
                            .lastUpdated(stockDetails.getLastUpdated())
                            .price(stockDetails.getPrice())
                            .beta(stockDetails.getBeta())
                            .peRatio(stockDetails.getPeRatio())
                            .pbRatio(stockDetails.getPbRatio())
                            .eps(stockDetails.getEps())
                            .marketCap(stockDetails.getMarketCap())
                            .freeCashFlow(stockDetails.getFreeCashFlow())
                            .debtToEBITDA(stockDetails.getDebtToEBITDA())
                            .debtToMarketCap(stockDetails.getDebtToMarketCap())
                            .build()))
                    .dividend(List.of(DividendDTO.builder()
                            .dividendId(dividend.getDividendId())
                            .dividendYield(dividend.getDividendYield())
                            .exDividendDate(dividend.getExDividendDate())
                            .paymentDate(dividend.getPaymentDate())
                            .dividendPerShare(dividend.getDividendPerShare())
                            .payoutRatio(dividend.getPayoutRatio())
                            .dividendGrowthFiveYears(dividend.getDividendGrowthFiveYears())
                            .build()))
                    .build();
        }
        catch (URISyntaxException | IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error fetching stock data: " + e.getMessage());
        }
    }
}