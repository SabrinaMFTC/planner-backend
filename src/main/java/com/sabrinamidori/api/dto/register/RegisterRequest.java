package com.sabrinamidori.api.dto.register;

public record RegisterRequest(
        String name,
        String email,
        String password
) {}
