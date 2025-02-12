package com.example.urlshortener.urlShortenerModule;

import com.example.urlshortener.urlShortenerModule.shared.DeleteShortenUrlDTO;
import com.example.urlshortener.urlShortenerModule.shared.GenerateShortenUrlDTO;
import com.example.urlshortener.urlShortenerModule.shared.GetLongUrlDTO;
import jakarta.servlet.http.HttpServletRequest;
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
    public GenerateShortenUrlDTO.output generateShortenUrl(@RequestBody @Valid GenerateShortenUrlDTO.input input, HttpServletRequest request) {
        return urlShortenerUseCase.generateShortenUrl(input, request);
    }

    @DeleteMapping("{shortenedUrlId}")
    public DeleteShortenUrlDTO.output deleteShortenUrl(@PathVariable("shortenedUrlId") String shortenedUrlId) {
        return urlShortenerUseCase.deleteShortenUrl(shortenedUrlId);
    }
}
