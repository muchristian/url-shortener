package com.example.urlshortener.urlShortenerModule;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Null;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface UrlShortenerRepository extends JpaRepository<UrlShortenerEntity, Long> {
    Optional<UrlShortenerEntity> findByShortenedUrlId(String shortenedUrlId);
    default UrlShortenerEntity findShortenedUrlIdOrThrowException(String shortenedUrlId) {
        Optional<UrlShortenerEntity> ShortenedUrlId = findByShortenedUrlId(shortenedUrlId);
        return ShortenedUrlId.orElseThrow(() -> new RestClientException("Shortened url not found"));
    }

    boolean existsByShortenedUrlId(String shortenedUrlId);

    @Modifying
    @Transactional
    @Query(value = "delete from url_shortener e where (e.created_at + CAST(e.ttl AS INTERVAL) ) < :now", nativeQuery = true)
    void deleteExpiredShortenedUrls(
            @Param("now") @Null Instant now
    );
}
