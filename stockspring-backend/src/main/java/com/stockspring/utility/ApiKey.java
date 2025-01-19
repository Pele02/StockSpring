package com.stockspring.utility;

import io.github.cdimascio.dotenv.Dotenv;

public class ApiKey {
    private static final Dotenv dotenv = Dotenv.load();

    public static String getFinnhubAPIKey(){
        return dotenv.get("FINNHUB_API_KEY");
    }

    public static String getNewsAPIKey(){
        return dotenv.get("NEWS_API_KEY");
    }

}
