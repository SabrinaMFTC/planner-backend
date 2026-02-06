package com.sabrinamidori.api.dto.login;

public record LoginRequest(
        String email,
        String password
) {}
