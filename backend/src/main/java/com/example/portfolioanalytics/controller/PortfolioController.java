package com.example.portfolioanalytics.controller;

import com.example.portfolioanalytics.model.portfolio.Portfolio;
import com.example.portfolioanalytics.service.AnalyticsService;
import com.example.portfolioanalytics.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @PostMapping
    public ResponseEntity<String> createPortfolio(@RequestBody Portfolio portfolio, Authentication authentication) {
        String userId = authentication.getName(); // Firebase UID as the principal's name
        try {
            String portfolioId = portfolioService.createPortfolio(portfolio, userId);
            return ResponseEntity.ok("Portfolio created successfully with ID: " + portfolioId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating portfolio: " + e.getMessage());
        }
    }

    @GetMapping("/{portfolioId}")
    public ResponseEntity<Portfolio> getPortfolioById(@PathVariable String portfolioId,
                                                      Authentication authentication) {
        String userId = authentication.getName();
        System.out.println("Fetching portfolio with ID: " + portfolioId);
        try {
            Portfolio portfolio = portfolioService.getPortfolioById(portfolioId, userId);
            if (portfolio != null) {
                return ResponseEntity.ok(portfolio);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Portfolio>> getUserPortfolios(Authentication authentication) {
        String userId = authentication.getName();
        try {
            List<Portfolio> portfolios = portfolioService.getUserPortfolios(userId);
            return ResponseEntity.ok(portfolios);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{portfolioId}")
    public ResponseEntity<String> updatePortfolio(
            @PathVariable String portfolioId,
            @RequestBody Portfolio portfolio,
            Authentication authentication) {
        String userId = authentication.getName();
        try {
            String updatedPortfolioId = portfolioService.updatePortfolio(portfolioId, portfolio, userId);
            if (updatedPortfolioId != null) {
                return ResponseEntity.ok("Portfolio updated successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating portfolio: " + e.getMessage());
        }
    }

    @DeleteMapping("/{portfolioId}")
    public ResponseEntity<String> deletePortfolio(@PathVariable String portfolioId, Authentication authentication) {
        String userId = authentication.getName();
        try {
            boolean deleted = portfolioService.deletePortfolio(portfolioId, userId);
            if (deleted) {
                return ResponseEntity.ok("Portfolio deleted successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting portfolio: " + e.getMessage());
        }
    }

}
