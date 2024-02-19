package com.example.portfolioanalytics.service;

import com.example.portfolioanalytics.model.portfolio.Investment;
import com.example.portfolioanalytics.model.portfolio.Portfolio;
import com.example.portfolioanalytics.model.portfolio.PortfolioReturn;
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
        for (Investment investment : investmentList) {
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

    public PortfolioReturn calculateActualRealizedReturn(Portfolio portfolio, String date) throws InterruptedException {
        double totalCurrentPrice = 0.0;

        for (Investment investment : portfolio.getInvestments()) {
            Map<String, Map<String, String>> timeSeries = fetchTimeSeries(investment.getSymbol());
            Map<String, String> dateData = timeSeries.get(date);
            double currentPrice = Double.parseDouble(dateData.get("4. close"));
            totalCurrentPrice += currentPrice * investment.getQuantity();
        }
        double totalPurchasePrice = portfolio.getPurchaseTotal();
        double totalReturnsDollars = totalCurrentPrice - totalPurchasePrice;
        double totalReturnsPercentage = (totalReturnsDollars / totalPurchasePrice) * 100;

        return new PortfolioReturn(totalReturnsDollars, totalReturnsPercentage);
    }

    public double[] calculateDailyReturns(Portfolio portfolio, String startDate) throws InterruptedException{
        Map<String, Double> dailyValues = new TreeMap<>();

        for (Investment investment : portfolio.getInvestments()) {
            Map<String, Map<String, String>> timeSeries = fetchTimeSeries(investment.getSymbol());
            timeSeries.forEach((date, dateData) -> {
                if (date.compareTo(startDate) >= 0) {
                    double closePrice = Double.parseDouble(dateData.get("4. close"));
                    dailyValues.merge(date, closePrice * investment.getQuantity(), Double::sum);
                }
            });
        }
        double[] dailyReturns = dailyValues.values().stream().mapToDouble(Double::doubleValue).toArray();
        return dailyReturns;
    }

    public double calculateVolatility (Portfolio portfolio, String startDate) throws InterruptedException{
        double[] dailyReturns = calculateDailyReturns(portfolio, startDate);
        List<Double> dailyReturnsList = Arrays.stream(dailyReturns).boxed().toList();
        if (dailyReturnsList.size() < 2) {
            throw new IllegalArgumentException("Need at least two days of prices to calculate volatility.");
        }
        double averageReturn = dailyReturnsList.stream().mapToDouble(a -> a).average().orElse(0.0);
        double variance = dailyReturnsList.stream().mapToDouble(r -> Math.pow(r - averageReturn, 2)).sum() / (dailyReturnsList.size() - 1);

        return Math.sqrt(variance);
    }

    public Map<String, Map<String, String>> fetchTimeSeries(String symbol) throws InterruptedException {
        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + symbol + "&apikey=" + apiKey + "&outputsize=full";
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response != null && response.containsKey("Time Series (Daily)")) {
            Map<String, Map<String, String>> timeSeries = (Map<String, Map<String, String>>) response.get("Time Series (Daily)");
            return timeSeries;
        } else {
            throw new InterruptedException("Portfolio is either not found or not authorized");
        }
    }

    public double getCurrentPrice(String symbol) {
        String url = String.format("https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=%s&apikey=%s", symbol, apiKey);
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        Map<String, String> globalQuote = (Map<String, String>) response.get("Global Quote");
        String priceAsString = globalQuote.get("05. price");
        return Double.parseDouble(priceAsString);
    }

    public double calculateExpectedReturn(Portfolio portfolio) {
        double totalCurrentValue = 0;
        double totalPurchaseValue = 0;

        for (Investment investment : portfolio.getInvestments()) {
            double currentPrice = getCurrentPrice(investment.getSymbol());
            totalCurrentValue += currentPrice * investment.getQuantity();
            totalPurchaseValue += investment.getPurchasePrice() * investment.getQuantity();
        }

        return (totalCurrentValue - totalPurchaseValue) / totalPurchaseValue;
    }

    public double getRiskFreeRate() {
        // TODO: need to find an API to get risk free rate, using 0.05 as placeholder.
        double riskFreeRate = 0.05;
        return riskFreeRate;
    }

    public double calculateSharpeRatio(Portfolio portfolio, String startDate) throws InterruptedException {
        double expectedReturn = calculateExpectedReturn(portfolio);
        double volatility = calculateVolatility(portfolio, startDate);
        double riskFreeRate = getRiskFreeRate();

        if (volatility == 0) {
            throw new IllegalArgumentException("Volatility cannot be zero when calculating the Sharpe Ratio.");
        }

        return (expectedReturn - riskFreeRate) / volatility;
    }
}
