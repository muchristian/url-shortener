package com.example.urlshortener.urlShortenerModule;


import com.example.urlshortener.urlShortenerModule.shared.GenerateShortenUrlDTO;
import com.example.urlshortener.urlShortenerModule.shared.GetLongUrlDTO;
import jakarta.validation.constraints.NotNull;

public interface UrlShortenerUseCase {
    GetLongUrlDTO.output getLongUrl(@NotNull String shortenedUrlId);

    GenerateShortenUrlDTO.output generateShortenUrl(@NotNull GenerateShortenUrlDTO.input input);

    void deleteShortenUrl(@NotNull String shortenedUrlId);
}
