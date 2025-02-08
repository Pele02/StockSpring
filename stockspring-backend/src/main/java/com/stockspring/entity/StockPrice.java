package com.stockspring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "stock_price", schema = "stockspring_schema")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stock_price_seq")
    @SequenceGenerator(name = "stock_price_seq", sequenceName = "stock_price_seq", allocationSize = 1)
    private Long stockPriceId;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "open_price", nullable = false)
    private Double openPrice;

    @Column(name = "close_price", nullable = false)
    private Double closePrice;

    @Column(name = "high_price", nullable = false)
    private Double highPrice;

    @Column(name = "low_price", nullable = false)
    private Double lowPrice;

    @Column(name = "volume", nullable = false)
    private Long volume;

    @ManyToOne
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;
}
