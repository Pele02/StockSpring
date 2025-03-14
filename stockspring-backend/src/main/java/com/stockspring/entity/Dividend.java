package com.stockspring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dividends", schema = "stockspring_schema")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dividend {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dividend_seq")
    @SequenceGenerator(name = "dividend_seq", sequenceName = "dividend_seq", allocationSize = 1)
    @Column(name = "dividend_id")
    private Long dividendId;

    @Column(name = "dividend_yield", nullable = false)
    private Double dividendYield;

    @Column(name="ex_dividend_date", nullable = false)
    private String exDividendDate;

    @Column(name="payment_date", nullable = false)
    private String  paymentDate;

    @Column(name="dividend_per_share", nullable = false)
    private Double dividendPerShare;

    @Column(name="payout_ratio", nullable = false)
    private Double payoutRatio;

    @Column(name="dividend_growth_five_years", nullable = false)
    private Double dividendGrowthFiveYears;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;
}
