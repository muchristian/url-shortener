package com.example.urlshortener.urlShortenerModule.shared.model;

import jakarta.validation.constraints.NotNull;

public record ShortenedUrlGenerateResponse(
   @NotNull String shortUrl,
   @NotNull String longUrl
) {}
