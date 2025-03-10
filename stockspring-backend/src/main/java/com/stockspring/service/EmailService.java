package com.stockspring.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    void sendResetEmail(String email, String resetLink);
}
