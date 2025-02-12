package com.example.urlshortener.urlShortenerModule;

import com.example.urlshortener.urlShortenerModule.shared.GenerateShortenUrlDTO;
import com.example.urlshortener.urlShortenerModule.shared.GetLongUrlDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlShortenerController {
    private final UrlShortenerUseCase urlShortenerUseCase;
    public UrlShortenerController(UrlShortenerUseCase urlShortenerUseCase) {
        this.urlShortenerUseCase = urlShortenerUseCase;
    }

    @GetMapping("{shortenedUrlId}")
    public GetLongUrlDTO.output getLongUrl(@PathVariable("shortenedUrlId") String shortenedUrlId) {
        return urlShortenerUseCase.getLongUrl(shortenedUrlId);
    }

    @PostMapping("/shortener")
    public GenerateShortenUrlDTO.output generateShortenUrl(@RequestBody @Valid GenerateShortenUrlDTO.input input) {
        return urlShortenerUseCase.generateShortenUrl(input);
    }

    @DeleteMapping("{shortenedUrlId}")
    public void deleteShortenUrl(@PathVariable("shortenedUrlId") String shortenedUrlId) {
        urlShortenerUseCase.deleteShortenUrl(shortenedUrlId);
    }
}
