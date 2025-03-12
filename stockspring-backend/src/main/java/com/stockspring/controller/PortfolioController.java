package com.stockspring.controller;

import com.stockspring.dto.PortfolioDTO;
import com.stockspring.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/portfolio")
@RestController
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @PostMapping("/create")
    public ResponseEntity<PortfolioDTO> createPortfolio(@RequestBody String portfolioName) {

        if (portfolioService.existsByName(portfolioName)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        PortfolioDTO portfolioDTO = portfolioService.addPortfolio(portfolioName);

        return new ResponseEntity<>(portfolioDTO, HttpStatus.CREATED);
    }

    @GetMapping("/all-portfolios")
    public ResponseEntity<List<PortfolioDTO>> getAllPortfolios() {
        List<PortfolioDTO> portfolios = portfolioService.getAllPortfolios();
        return new ResponseEntity<>(portfolios, HttpStatus.OK);

    }

    @PutMapping("/{portfolioId}/name")
    public ResponseEntity<PortfolioDTO> updatePortfolioName(@PathVariable Long portfolioId, @RequestBody String newPortfolioName) {
        PortfolioDTO updatedPortfolio = portfolioService.updatePortfolioName(portfolioId, newPortfolioName);
        return ResponseEntity.ok(updatedPortfolio);
    }

    @DeleteMapping("/{portfolioId}/delete")
    public ResponseEntity<?> deletePortfolio(@PathVariable Long portfolioId) {
        portfolioService.deletePortfolio(portfolioId);
        return ResponseEntity.ok().body("Portfolio deleted successfully!");
    }
}
