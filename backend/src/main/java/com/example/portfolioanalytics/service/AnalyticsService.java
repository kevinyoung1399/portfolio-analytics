package com.example.portfolioanalytics.service;

import com.example.portfolioanalytics.model.portfolio.Investment;
import com.example.portfolioanalytics.model.portfolio.Portfolio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class AnalyticsService {

    @Value("${alpha.vantage.api.key}")
    private String apiKey;
    private final RestTemplate restTemplate;

    @Autowired
    public AnalyticsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<String> calculateSimpleMovingAverage(Portfolio portfolio, Integer timePeriod) {
        List<Investment> investmentList = portfolio.getInvestments();
        System.out.println("got the investments" + portfolio.getInvestments().toString());
        String url = "https://www.alphavantage.co/query?function=SMA&symbol={symbol}&interval=daily&time_period={timePeriod}&series_type=close&apikey={apiKey}";

        List<String> smaList = new ArrayList<>();
        for (Investment investment: investmentList) {
            String symbol = investment.getSymbol();
            Map<String, String> vars = new HashMap<>();
            vars.put("symbol", symbol);
            vars.put("timePeriod", String.valueOf(timePeriod));
            vars.put("apiKey", apiKey);
            String sma = restTemplate.getForObject(url, String.class, vars);
            System.out.println(sma);
            smaList.add(sma);
        }
        return smaList;
    }
}



