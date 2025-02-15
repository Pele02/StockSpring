package com.stockspring.service;


import com.stockspring.dto.StockDTO;

/**
 * Interface for fetching stock-related data.
 */
public interface StockDataService {

    /**
     * Fetches the latest news related to stocks.
     *
     * @return a string containing the latest news data if the request is successful;
     *         an error message if the request fails
     */
     String getLatestNews();

    /**
     * Fetches the latest news related to a specified stock.
     *
     * @param ticker the stock ticker symbol
     *
     * @return a string containing the latest news data of a company if the request is successful;
     *         an error message if the request fails
     */
    String getCompanyNews(String ticker);

    /**
     * Fetches the latest stock data.
     *
     * @param symbol the stock ticker symbol
     *
     * @return a string containing the latest stock data if the request is successful;
     *         an error message if the request fails
     */
    StockDTO getStockData(String symbol);

}
