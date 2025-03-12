package com.stockspring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PortfolioDTO {
    private Long portfolioId;
    private String portfolioName;
    private List<PortfolioStockDTO> portfolioStocks;
}
