package com.example.portfolioanalytics.model.portfolio;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.Date;

@Entity
@DiscriminatorValue("STOCK")
public class Stock extends Investment {
    private double purchasePrice;
    private double currentPrice;

    public Stock(
            String id,
            String type,
            String symbol, double quantity, Date purchaseDate, double purchasePrice) {
        super(id, type, symbol, quantity, purchaseDate, purchasePrice);
    }

    public Stock() {}
}
