package com.stockspring.service;

import com.stockspring.dto.PortfolioDTO;
import com.stockspring.entity.Portfolio;
import com.stockspring.exception.APIException;
import com.stockspring.repository.PortfolioRepository;
import com.stockspring.repository.UserRepository;
import com.stockspring.utility.AuthUtility;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PortfolioServiceImpl implements PortfolioService {
    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthUtility authUtility;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public PortfolioDTO addPortfolio(String portfolioName) {
        Portfolio portfolio = new Portfolio();
        portfolio.setPortfolioName(portfolioName);
        portfolio.setUserPortfolio(authUtility.loggedInUser());
        Portfolio savedPortfolio = portfolioRepository.save(portfolio);
        return modelMapper.map(savedPortfolio, PortfolioDTO.class);
    }

    @Override
    public List<PortfolioDTO> getAllPortfolios() {
        Long userId = authUtility.loggedInUser().getId();

        return portfolioRepository.findByUserPortfolioId(userId)
                .stream()
                .map(portfolio -> modelMapper.map(portfolio, PortfolioDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public PortfolioDTO updatePortfolioName(Long portfolioId, String newPortfolioName) {
        if (portfolioRepository.findByPortfolioName(newPortfolioName).isPresent()) {
            throw new APIException("Portfolio with name " + newPortfolioName + " already exists", HttpStatus.CONFLICT.value());
        }

        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new APIException("Portfolio not found", HttpStatus.NOT_FOUND.value()));

        portfolio.setPortfolioName(newPortfolioName);
        portfolioRepository.save(portfolio);

        return modelMapper.map(portfolio, PortfolioDTO.class);
    }


    @Override
    public void deletePortfolio(Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new APIException("Portfolio not found", HttpStatus.NOT_FOUND.value()));

        portfolioRepository.delete(portfolio);
    }

    @Override
    public boolean existsByName(String portfolioName) {
        return portfolioRepository.findByPortfolioName(portfolioName).isPresent();
    }
}
