package com.stockspring.utility;

import org.springframework.stereotype.Component;

@Component
public class FinancialUtility {

    public double roundToTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
