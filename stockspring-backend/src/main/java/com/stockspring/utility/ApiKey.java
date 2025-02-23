package com.stockspring.utility;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;

@Component
public class ApiKey {
    private static final Dotenv dotenv = Dotenv.load();

    public static String getFinnhubAPIKey(){
        return dotenv.get("FINNHUB_API_KEY");
    }

    public static String getNewsAPIKey(){
        return dotenv.get("NEWS_API_KEY");
    }

    public static String getFmpAPIKey(){
        return dotenv.get("FMP_API_KEY");
    }

    public static String getPolygonAPIKey(){
        return dotenv.get("POLYGON_API_KEY");
    }

}
