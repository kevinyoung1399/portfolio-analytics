package com.example.portfolioanalytics.controller;

import com.example.portfolioanalytics.model.portfolio.Portfolio;
import com.example.portfolioanalytics.service.AnalyticsService;
import com.example.portfolioanalytics.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private AnalyticsService analyticsService;

    @PostMapping("/sma/{portfolioId}")
    public ResponseEntity<List<String>> calculateSimpleMovingAverage(@PathVariable String portfolioId,
                                                                     @RequestBody Map<String, Object> requestBody,
                                                                     Authentication authentication) {
        String userId = authentication.getName();
        int timePeriod = (int) requestBody.get("timePeriod");
        try {
            Portfolio portfolio = portfolioService.getPortfolioById(portfolioId, userId);
            List<String> result = analyticsService.calculateSimpleMovingAverage(portfolio, timePeriod);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
