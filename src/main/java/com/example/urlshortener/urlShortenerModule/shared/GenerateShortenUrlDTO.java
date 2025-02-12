package com.example.urlshortener.urlShortenerModule.shared;

import com.example.urlshortener.urlShortenerModule.shared.model.ShortenedUrlGenerateResponse;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GenerateShortenUrlDTO {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class input {
        @Size(min = 5) @Size(max = 12) String shortenUrlId;
        @NotNull String url;
        String ttl;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class output {
        private ShortenedUrlGenerateResponse shortenedUrlResponse;
    }

}
