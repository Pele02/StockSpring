package com.stockspring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock_details", schema = "stockspring_schema")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stock_details_seq")
    @SequenceGenerator(name = "stock_details_seq", sequenceName = "stock_details_seq", allocationSize = 1)
    private Long stockDetailsId;

    @Column(name="last_updated", nullable = false)
    private LocalDateTime lastUpdated;

    //Price
    @Column(name = "price", nullable = false)
    private Double price;


    //Valuation Metrics
    @Column(name = "beta", nullable = false)
    private Double beta;

    @Column(name = "pe_ratio", nullable = false)
    private Double peRatio;

    @Column(name = "pb_ratio", nullable = false)
    private Double pbRatio;

    @Column(name = "earnings_per_share", nullable = false) //netIncomePerShareTTM
    private Double eps;

    @Column(name = "market_capitalization", nullable = false)
    private Long marketCap;

    @Column(name="free_cash_flow", nullable = false)
    private Double freeCashFlow;


    //Debt
    @Column(name="debt_to_EBITDA", nullable = false)
    private Double debtToEBITDA;

    @Column(name="debt_to_market_cap", nullable = false)
    private Double debtToMarketCap;


    @ManyToOne
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

}
