package com.stockspring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "dividend_history", schema = "stockspring_schema")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dividend {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dividend_seq")
    @SequenceGenerator(name = "dividend_seq", sequenceName = "dividend_seq", allocationSize = 1)
    @Column(name = "dividend_id")
    private Long dividendId;

    @Column(name="ex_dividend_date", nullable = false)
    private LocalDate exDividendDate;

    @Column(name="payment_date", nullable = false)
    private LocalDate  paymentDate;

    @Column(name="dividend_amount", nullable = false)
    private Double dividendAmount;

    @ManyToOne
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;
}
