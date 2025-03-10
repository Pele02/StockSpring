//package com.stockspring.controller;
//
//import com.stockspring.service.PortfolioService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class PortfolioController {
//
//    @Autowired
//    private PortfolioService portfolioService;
//
//    @PostMapping("/portfolios")
//    public ResponseEntity<String> createPortfolio(@RequestBody String portfolioName) {
//        try {
//            if (portfolioService.existsByName(portfolioName)) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                        .body("Portfolio already exists!");
//            }
//
//            portfolioService.addPortfolio(portfolioName);
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("An error occurred while creating the portfolio: " + e.getMessage());
//        }
//        return ResponseEntity.ok("Portfolio created successfully!");
//    }
//}
