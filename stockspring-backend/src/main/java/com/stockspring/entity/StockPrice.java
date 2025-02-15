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

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "open_price")
    private Double openPrice;

    @Column(name = "close_price")
    private Double closePrice;

    @Column(name = "high_price")
    private Double highPrice;

    @Column(name = "low_price")
    private Double lowPrice;

    @Column(name = "volume")
    private Long volume;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;
}
