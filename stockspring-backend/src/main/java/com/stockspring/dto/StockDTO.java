package com.stockspring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockDTO {

    private String symbol;

    private String companyName;

    private Long marketCap;

    private String sector;

    private Double peRatio;

    private Double dividendYield;

}
