package com.example.portfolioanalytics.controller;

import com.example.portfolioanalytics.model.portfolio.Portfolio;
import com.example.portfolioanalytics.model.portfolio.PortfolioDateRequest;
import com.example.portfolioanalytics.model.portfolio.PortfolioReturn;
import com.example.portfolioanalytics.service.AnalyticsService;
import com.example.portfolioanalytics.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.sound.sampled.Port;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/dev")
public class DevController {

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private AnalyticsService analyticsService;

    @PostMapping("/returns")
    public ResponseEntity<PortfolioReturn> calculateReturn(@RequestBody PortfolioDateRequest request)
            throws InterruptedException {
        Portfolio portfolio = request.getPortfolio();
        String date = request.getDate();
        System.out.println(portfolio.toString() + date);
        PortfolioReturn result = analyticsService.calculateReturn(portfolio, date);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/returns/time")
    public ResponseEntity<double[]> calculateSimpleMovingAverage(@RequestBody PortfolioDateRequest request) throws InterruptedException {
        Portfolio portfolio = request.getPortfolio();
        String date = request.getDate();
        System.out.println(portfolio.toString() + date);
        double[] result = analyticsService.calculateReturnOverTime(portfolio, date);
        return ResponseEntity.ok(result);
    }

}