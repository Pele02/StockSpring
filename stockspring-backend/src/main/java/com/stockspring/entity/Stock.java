package com.stockspring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Represents a Stock entity in the StockSpring application.
 * This class is mapped to the "stocks" table in the "stockspring_schema" database schema.
 *
 * <p>
 * Each Stock has an auto-generated ID, a unique symbol, company name, current price,
 * market capitalization, sector, industry, P/E ratio, and dividend yield.
 * </p>
 *
 * @version 1.0
 */
@Entity
@Table(name = "stocks", schema = "stockspring_schema")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stock_seq")
    @SequenceGenerator(name = "stock_seq", sequenceName = "stock_seq", allocationSize = 1)
    @Column(name = "stock_id")
    private Long stockId;

    @Column(name = "symbol", nullable = false, unique = true)
    private String symbol;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "current_price", nullable = false)
    private Double currentPrice;

    @Column(name = "market_cap", nullable = false)
    private Double marketCap;

    @Column(name = "sector", nullable = false)
    private String sector;

    @Column(name = "pe_ratio", nullable = false)
    private Double peRatio;

    @Column(name = "dividend_yield", nullable = true)
    private Double dividendYield;

    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PortfolioStock> portfolioStocks;

    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StockPrice> stockPrices;

    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dividend> dividendHistory =new ArrayList<>();

}
