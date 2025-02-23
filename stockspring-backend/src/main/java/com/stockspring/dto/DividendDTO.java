package com.stockspring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DividendDTO {
    private Long dividendId;
    private Double dividendYield;
    private String exDividendDate;
    private String paymentDate;
    private Double dividendPerShare;
    private Double payoutRatio;
    private Double dividendGrowthFiveYears;
}
