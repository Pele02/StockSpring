package com.stockspring.utility;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class FinancialUtility {

    public double roundToTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    public static double dividendGrowth(JSONArray dividendArray) {
        // Get the current year
        int currentYear = LocalDate.now().getYear();
        Map<Integer, Double> annualDividends = new HashMap<>();

        // Iterate through the dividend records
        for (int i = 0; i < dividendArray.length(); i++) {
            JSONObject dividend = dividendArray.getJSONObject(i);
            String payDateStr = dividend.optString("paymentDate", null); // Use optString to avoid exceptions
            double cashAmount = dividend.optDouble("adjDividend", -1); // Use optDouble to avoid exceptions

            // Check if pay_date is present and cash_amount is valid
            if (payDateStr != null && cashAmount >= 0) {
                // Parse the pay date
                int payYear = LocalDate.parse(payDateStr).getYear();

                if (payYear < currentYear - 5) {
                    break; // Skip invalid pay dates
                }

                // Sum the cash amount for the corresponding year
                if (payYear >= (currentYear - 5) && payYear < currentYear) {
                    annualDividends.put(payYear, annualDividends.getOrDefault(payYear, 0.0) + cashAmount);
                }
            }
        }

        // Calculate the dividend growth for the most recent year
        double totalGrowth = 0.0;
        int count = 0;

        for (int year = currentYear - 1; year >= currentYear - 5; year--) {
            if (annualDividends.containsKey(year) && annualDividends.containsKey(year + 1)) {
                double currentDividend = annualDividends.get(year+ 1);
                double previousDividend = annualDividends.get(year);
                double growth = (currentDividend / previousDividend) - 1;
                totalGrowth += growth; // Accumulate total growth
                count++; // Count the number of growth calculations
            }
        }

        // Return the average growth percentage
        return count > 0 ? (totalGrowth / count) * 100 : 0.0;
    }
}
