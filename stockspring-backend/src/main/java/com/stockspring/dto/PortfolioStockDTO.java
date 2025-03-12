package com.stockspring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PortfolioStockDTO {
    private Long portfolioStockId;
    private Integer sharesOwned;
    private Double costPerShare;
    private Double averagePrice;
    private Double profit;
    private StockDTO stock;
}
