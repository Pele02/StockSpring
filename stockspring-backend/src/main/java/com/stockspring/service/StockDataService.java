package com.stockspring.service;


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
    public String getLatestNews();

    /**
     * Fetches the latest news related to a specified stock.
     *
     * @return a string containing the latest news data of a company if the request is successful;
     *         an error message if the request fails
     */
    String getCompanyNews(String ticker);
}
