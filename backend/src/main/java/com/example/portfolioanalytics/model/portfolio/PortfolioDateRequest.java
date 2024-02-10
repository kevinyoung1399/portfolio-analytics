package com.example.portfolioanalytics.model.portfolio;

public class PortfolioDateRequest {

    private Portfolio portfolio;
    private String date;

    public PortfolioDateRequest(Portfolio portfolio, String date) {
        this.portfolio = portfolio;
        this.date = date;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public String getDate() {
        return date;
    }
}
