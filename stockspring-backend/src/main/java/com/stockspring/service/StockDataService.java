package com.stockspring.service;


import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Interface for fetching stock-related data.
 */
public interface StockDataService {

    /**
     * Fetches the latest news related to stocks.
     *
     * @return a string containing the latest news data if the request is successful;
     *         an error message if the request fails
     * @throws URISyntaxException if the URI is invalid
     * @throws IOException if an I/O error occurs
     * @throws InterruptedException if the operation is interrupted
     */
    public String getLatestNews() throws URISyntaxException, IOException, InterruptedException;

}
