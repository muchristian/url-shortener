package com.example.urlshortener.urlShortenerModule;

import com.example.urlshortener.urlShortenerModule.shared.DeleteShortenUrlDTO;
import com.example.urlshortener.urlShortenerModule.shared.GenerateShortenUrlDTO;
import com.example.urlshortener.urlShortenerModule.shared.GetLongUrlDTO;
import com.example.urlshortener.urlShortenerModule.shared.enums.TimeUnit;
import com.example.urlshortener.urlShortenerModule.shared.model.LongUrlResponse;
import com.example.urlshortener.urlShortenerModule.shared.model.ShortenedUrlGenerateResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
public class UrlShortenerUseCaseImpl implements UrlShortenerUseCase {
    private final UrlShortenerRepository urlShortenerRepository;
    public UrlShortenerUseCaseImpl(
       UrlShortenerRepository urlShortenerRepository
    ) {
        this.urlShortenerRepository = urlShortenerRepository;
    }

    @Override
    public GetLongUrlDTO.output getLongUrl(@NotNull String shortenedUrlId) {
        val shortenedUrl = urlShortenerRepository.findShortenedUrlIdOrThrowException(shortenedUrlId);
        log.info("Long url for this {} short url retrieved successfully", shortenedUrl);
        return new GetLongUrlDTO.output(new LongUrlResponse(shortenedUrl.getUrl()));
    }


    @Override
    public GenerateShortenUrlDTO.output generateShortenUrl(@NotNull GenerateShortenUrlDTO.input input, HttpServletRequest request) {
        var shortenUrlId = input.getShortenUrlId();
        if (shortenUrlId != null && urlShortenerRepository.existsByShortenedUrlId(shortenUrlId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Short URL already exists");
        }

        shortenUrlId = shortenUrlId == null ? generateRandomShortenUrl() : shortenUrlId;
        var ttl = input.getTtl() != null ? ttlConverter(input.getTtl()) : null;
        UrlShortenerEntity urlShortenerEntity = new UrlShortenerEntity(
                shortenUrlId,
                input.getUrl(),
                ttl
        );
        urlShortenerRepository.save(urlShortenerEntity);

        String protocol = request.getScheme();
        String host = request.getServerName();
        int port = request.getServerPort();
        var shortUrl = protocol + "://" + host + ":" + port + "/" + urlShortenerEntity.getShortenedUrlId();

        log.info("Short url, generated successfully with this data provided {}", urlShortenerEntity);
        return new GenerateShortenUrlDTO.output("Short url generated successfully", new ShortenedUrlGenerateResponse(shortUrl, urlShortenerEntity.getUrl()));
    }

    @Override
    public DeleteShortenUrlDTO.output deleteShortenUrl(@NotNull String shortenedUrlId) {
        val shortenedUrl = urlShortenerRepository.findShortenedUrlIdOrThrowException(shortenedUrlId);
        urlShortenerRepository.deleteById(shortenedUrl.getId());
        log.info("Deletion of this short url is successful {}", shortenedUrl);
        return new DeleteShortenUrlDTO.output("Short url deleted successfully");
    }

    @Scheduled(cron = "*/30 * * * * ?")
    private void deleteExpiredShortenedUrls() {
        Instant now = Instant.now();
        urlShortenerRepository.deleteExpiredShortenedUrls(now);
        log.info("Successfully deleted expired short urls");
    }

    private String generateRandomShortenUrl() {
        var random = UUID.randomUUID().toString();
        return random.replace("-", "").substring(0, 5);
    }

    private String ttlConverter(String ttl) {
        String[] splittedTtl = ttl.split("_");
        var timeSpec = Long.parseLong(splittedTtl[0]);
        TimeUnit timeUnit = TimeUnit.valueOf(splittedTtl[1]);

        return String.format("%s %s", timeSpec, timeUnit);
    }
}
