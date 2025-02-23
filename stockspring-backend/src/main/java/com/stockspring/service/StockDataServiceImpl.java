package com.stockspring.service;

import com.stockspring.dto.StockDTO;
import com.stockspring.entity.Stock;
import com.stockspring.repository.StockRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of the StockDataService interface.
 * Provides methods to fetch stock-related data from external APIs.
 */
@Service
public class StockDataServiceImpl implements StockDataService{

    @Autowired
    StockRepository stockRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ExternalStockApiService externalStockApiService;

    /**
     * Fetches the latest news related to stocks.
     *
     * @return a string containing the latest news data if the request is successful;
     *         an error message if the request fails
     */
    @Override
    public String getLatestNews(){
        return externalStockApiService.getLatestNews();

    }

    /**
     * Fetches the latest news related to a specified stock.
     *
     * @param ticker the stock ticker symbol
     *
     * @return a string containing the latest news data of a company if the request is successful;
     * an error message if the request fails
     */
    @Override
    public String getCompanyNews(String ticker) {

        return externalStockApiService.getCompanyNews(ticker);

    }

    /**
     * Fetches the latest stock data.
     *
     * @param symbol the stock ticker symbol
     * @return a string containing the latest stock data if the request is successful;
     * an error message if the request fails
     */
    @Override
    public StockDTO getStockData(String symbol) {

        Optional<Stock> stock = stockRepository.findBySymbol(symbol);

        if (stock.isPresent()) {
            return modelMapper.map(stock.get(), StockDTO.class);
        }

        return externalStockApiService.fetchStockData(symbol);

    }

}
