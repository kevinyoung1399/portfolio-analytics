package com.example.portfolioanalytics.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userId;
    private String name;

    public Long getId() {
        return id;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<Stock> stocks = new ArrayList<>();

}