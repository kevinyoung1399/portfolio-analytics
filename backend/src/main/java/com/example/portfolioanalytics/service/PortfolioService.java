package com.example.portfolioanalytics.service;

import com.example.portfolioanalytics.model.Portfolio;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PortfolioService {

    public String savePortfolio(Portfolio portfolio) throws Exception {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection("portfolios").
                document(portfolio.getId().toString()).set(portfolio);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public Portfolio getPortfolioById(String id) throws Exception {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("portfolios").document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Portfolio portfolio = null;
        if (document.exists()) {
            portfolio = document.toObject(Portfolio.class);
        }
        return portfolio;
    }

    public List<Portfolio> getAllPortfolios() throws Exception {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = dbFirestore.collection("portfolios").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Portfolio> portfolios = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            portfolios.add(document.toObject(Portfolio.class));
        }
        return portfolios;
    }

    public String deletePortfolio(String id) throws Exception {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = dbFirestore.collection("portfolios").document(id).delete();
        return "Document with ID " + id + " has been deleted successfully";
    }



}