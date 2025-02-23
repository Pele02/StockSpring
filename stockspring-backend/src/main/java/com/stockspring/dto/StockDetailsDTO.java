package com.stockspring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockDetailsDTO {
    private Long stockDetailsId;
    private LocalDateTime lastUpdated;
    private Double price;
    private Double beta;
    private Double peRatio;
    private Double pbRatio;
    private Double eps;
    private Long marketCap;
    private Double freeCashFlow;
    private Double debtToEBITDA;
    private Double debtToMarketCap;
}
