package com.example.portfolioanalytics.model.portfolio;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

public class Investment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String type;
    private String symbol;
    private double quantity;
    private Date purchaseDate;
    private double purchasePrice;
    private double currentPrice;

    public Investment(
            String id,
            String symbol,
            String type, double quantity, Date purchaseDate, double purchasePrice) {
        this.id = id;
        this.type = type;
        this.symbol = symbol;
        this.quantity = quantity;
        this.purchaseDate = purchaseDate;
        this.purchasePrice = purchasePrice;
    }

    public Investment() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }
}