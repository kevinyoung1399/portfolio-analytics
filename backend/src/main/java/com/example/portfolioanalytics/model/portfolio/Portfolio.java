package com.example.portfolioanalytics.model.portfolio;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id; // Firestore document ID
    private String userId; // Firebase user UID
    private String name;
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<Investment> investments = new ArrayList<>();

    public Portfolio(String id, String userId, String name, List<Investment> investments) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.investments = investments;
    }

    public Portfolio() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Investment> getInvestments() {
        return investments;
    }

    public void setInvestments(List<Investment> investments) {
        this.investments = investments;
    }

}