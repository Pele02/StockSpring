package com.stockspring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockDTO {
    private Long stockId;
    private String symbol;
    private String companyName;
    private String sector;
    private String industry;
    private String description;
    private List<StockDetailsDTO> stockDetails;
    private List<DividendDTO> dividend;
}

