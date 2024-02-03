package com.example.portfolioanalytics.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String symbol;
    private double quantity;
    private double purchasePrice;
    private double currentPrice;
    private Date purchaseDate;

    public Stock(String id, String symbol, double quantity, double purchasePrice, double currentPrice, Date purchaseDate) {
        this.id = id;
        this.symbol = symbol;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.currentPrice = currentPrice;
        this.purchaseDate = purchaseDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}