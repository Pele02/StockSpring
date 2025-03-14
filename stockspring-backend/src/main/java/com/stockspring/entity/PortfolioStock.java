package com.stockspring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "portfolio_stocks", schema = "stockspring_schema")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioStock {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "portfolio_stock_seq")
    @SequenceGenerator(name = "portfolio_stock_seq", sequenceName = "portfolio_stock_seq", allocationSize = 1)
    @Column(name = "portfolio_stock_id")
    private Long portfolioStockId;

    @Column(name = "shares_owned", nullable = false)
    private Integer sharesOwned;

    @Column(name = "cost_share", nullable = false)
    private Double costPerShare;

    @Column(name = "average_price", nullable = false)
    private Double averagePrice;

    @Column(name = "profit", nullable = true)
    private Double profit;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

}
