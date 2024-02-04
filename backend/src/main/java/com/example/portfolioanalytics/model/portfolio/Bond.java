package com.example.portfolioanalytics.model.portfolio;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.Date;

@Entity
@DiscriminatorValue("BOND")
public class Bond extends Investment {
    private double faceValue;
    private double interestRate;
    private Date maturityDate;

    public Bond(
            String id,
            String type,
            String symbol,
            double quantity, Date purchaseDate, double purchasePrice, double interestRate, Date matureityDate) {
        super(id, type, symbol, quantity, purchaseDate, purchasePrice);
    }

    public double getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(double faceValue) {
        this.faceValue = faceValue;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public Date getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(Date maturityDate) {
        this.maturityDate = maturityDate;
    }
}