package com.example.urlshortener.urlShortenerModule.shared;

import com.example.urlshortener.urlShortenerModule.shared.model.LongUrlResponse;
import com.example.urlshortener.urlShortenerModule.shared.model.ShortenedUrlGenerateResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetLongUrlDTO {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class output {
        private LongUrlResponse longUrlResponse;
    }

}
