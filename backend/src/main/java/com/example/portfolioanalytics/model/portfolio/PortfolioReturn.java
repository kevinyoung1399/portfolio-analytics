package com.example.portfolioanalytics.model.portfolio;

public class PortfolioReturn {

    private double totalReturnDollars;
    private double totalReturnPercentage;

    public PortfolioReturn(double totalReturnDollars, double totalReturnPercentage) {
        this.totalReturnDollars = totalReturnDollars;
        this.totalReturnPercentage = totalReturnPercentage;
    }

    public double getTotalReturnDollars() {
        return totalReturnDollars;
    }

    public void setTotalReturnDollars(double totalReturnDollars) {
        this.totalReturnDollars = totalReturnDollars;
    }

    public double getTotalReturnPercentage() {
        return totalReturnPercentage;
    }

    public void setTotalReturnPercentage(double totalReturnPercentage) {
        this.totalReturnPercentage = totalReturnPercentage;
    }
}
