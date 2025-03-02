package com.stockspring.service;

import org.springframework.stereotype.Service;

@Service
public interface PortfolioService {

    void addPortfolio(String portfolioName);

    boolean existsByName(String portfolioName);
}
