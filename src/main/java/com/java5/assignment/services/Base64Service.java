package com.java5.assignment.services;

import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class Base64Service {
    public String encode(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    public String decode(String encodedInput) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedInput);
        return new String(decodedBytes);
    }
}
