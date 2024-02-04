package com.example.portfolioanalytics.service;

//import com.example.portfolioanalytics.model.portfolio.Bond;
import com.example.portfolioanalytics.model.portfolio.Investment;
import com.example.portfolioanalytics.model.portfolio.Portfolio;
import com.example.portfolioanalytics.model.portfolio.Stock;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class PortfolioService {

    private static final String PORTFOLIOS_COLLECTION = "portfolios";

    private Firestore getFirestore() {
        return FirestoreClient.getFirestore();
    }

    public String createPortfolio(Portfolio portfolio, String userId) throws InterruptedException, ExecutionException {
        DocumentReference documentReference = getFirestore().collection(PORTFOLIOS_COLLECTION).document();
        // Ensure the userId is set for the new portfolio
        portfolio.setUserId(userId);
        portfolio.setId(documentReference.getId()); // Set the Firestore-generated ID to the portfolio object
        for (Investment investment : portfolio.getInvestments()) {
            String type = investment.getType();
            if (!"Bond".equals(type) && !"ETF".equals(type) && !"Stock".equals(type)) {
                throw new IllegalArgumentException("Invalid investment type: " + type);
            }
            String uniqueID = UUID.randomUUID().toString();
            investment.setId(uniqueID);
        }
        ApiFuture<WriteResult> collectionsApiFuture = documentReference.set(portfolio);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    // TODO: handle the polymorphism for Firestore when introducing Bond type investment
    public Portfolio getPortfolioById(
            String portfolioId, String userId) throws InterruptedException, ExecutionException {
        DocumentReference documentReference = getFirestore().collection(PORTFOLIOS_COLLECTION).document(portfolioId);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        if (document.exists() && userId.equals(document.getString("userId"))) {
            System.out.println(document.toObject(Portfolio.class));
            return document.toObject(Portfolio.class);
        } else {
            throw new InterruptedException("Portfolio is either not found or not authorized");
        }
    }

    public List<Portfolio> getUserPortfolios(String userId) throws InterruptedException, ExecutionException {
        CollectionReference portfoliosCollection = getFirestore().collection(PORTFOLIOS_COLLECTION);
        Query query = portfoliosCollection.whereEqualTo("userId", userId);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        List<Portfolio> portfolios = new ArrayList<>();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            portfolios.add(document.toObject(Portfolio.class));
        }
        return portfolios;
    }

    public String updatePortfolio(
            String portfolioId,
            Portfolio portfolioUpdate, String userId) throws InterruptedException, ExecutionException {
        DocumentReference documentReference = getFirestore().collection(PORTFOLIOS_COLLECTION).document(portfolioId);
        DocumentSnapshot document = documentReference.get().get();
        if (document.exists() && userId.equals(document.getString("userId"))) {
            portfolioUpdate.setId(portfolioId); // Ensure the portfolio retains its ID
            documentReference.set(portfolioUpdate); // Overwrite the document
            return portfolioId;
        } else {
            throw new InterruptedException("Portfolio is either not found or not authorized");
        }
    }

    public boolean deletePortfolio(String portfolioId, String userId) throws InterruptedException, ExecutionException {
        DocumentReference documentReference = getFirestore().collection(PORTFOLIOS_COLLECTION).document(portfolioId);
        DocumentSnapshot document = documentReference.get().get();
        if (document.exists() && userId.equals(document.getString("userId"))) {
            documentReference.delete();
            return true;
        } else {
            throw new InterruptedException("Portfolio is either not found or not authorized");
        }
    }

}