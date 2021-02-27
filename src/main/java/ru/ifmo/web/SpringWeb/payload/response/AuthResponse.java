package ru.ifmo.web.SpringWeb.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class AuthResponse {
    private final boolean result = true;
    private String token;
}