package com.stockspring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents a Portfolio entity in the StockSpring application.
 * This class is mapped to the "portfolios" table in the "stockspring_schema" database schema.
 *
 * <p>
 * Each Portfolio has an auto-generated ID, a reference to the owning User, and a name.
 * </p>
 *
 * @version 1.0
 */
@Data
@Table(name = "portfolios", schema = "stockspring_schema")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "portfolio_seq")
    @SequenceGenerator(name = "portfolio_seq", sequenceName = "portfolio_seq", allocationSize = 1)
    @Column(name = "portfolio_id")
    private Long portfolioId;

    @Column(name = "portfolio_name",  nullable = false)
    private String portfolioName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User userPortfolio;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PortfolioStock> portfolioStocks =new ArrayList<>();
}
