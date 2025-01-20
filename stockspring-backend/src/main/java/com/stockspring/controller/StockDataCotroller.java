package com.stockspring.controller;

import com.stockspring.service.StockDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for managing stock data-related operations.
 * <p>
 *  This class handles requests for fetching the latest news and company-specific news
 *  related to stocks, providing necessary endpoints for the StockSpring application.
 * </p>
 *
 * <p>
 *  Cross-origin requests are allowed for the frontend hosted on
 *  <code>http://localhost:5173</code>.
 * </p>
 *
 * @version 1.0
 */
@RestController
@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600)
public class StockDataCotroller {

    @Autowired
    StockDataService stockDataService;

    /**
     * Endpoint to get the latest news related to stocks.
     *
     * @return a {@link ResponseEntity} containing the latest news data
     */
    @GetMapping("/latest-news")
    public ResponseEntity<String> getLatestNews() {
        try {
            String news = stockDataService.getLatestNews();
            return ResponseEntity.ok(news);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while fetching the latest news: " + e.getMessage());
        }
    }

    /**
     * Endpoint to get the latest news related to a specific company.
     *
     * @param ticker the stock ticker symbol of the company
     * @return a {@link ResponseEntity} containing the latest news data for the specified company
     */
    @GetMapping("/news/{ticker}")
    public ResponseEntity<String> getCompanyNews(@PathVariable("ticker") String ticker){
        try {
        String companyNews = stockDataService.getCompanyNews(ticker);
        return ResponseEntity.ok(companyNews);
        } catch (Exception e){
            return ResponseEntity.status(500).body("An error occurred while fetching the latest news on " + ticker + ": " + e.getMessage());
        }
    }
}
