package com.example.urlshortener.urlShortenerModule.shared.model;

import jakarta.validation.constraints.NotNull;

public record LongUrlResponse(
   @NotNull String longUrl
) {}
