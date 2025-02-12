package com.example.urlshortener.urlShortenerModule;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Entity
@Table(name="url_shortener")
@NoArgsConstructor
@Getter
public class UrlShortenerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id", nullable = false)
    private Long id;

    @NotNull
    @Column(name="shortened_url_id", unique = true, nullable = false)
    private String shortenedUrlId;

    @NotNull
    @Column(name="url", nullable = false)
    private String url;

    @Column(name="ttl")
    private String ttl;

    @Column(name="created_at", nullable = false)
    private Instant createdAt;

    @Column(name="modified_at", nullable = false)
    private Instant modifiedAt;

    public UrlShortenerEntity(
            @NotNull String shortenedUrlId,
            @NotNull String url,
            String ttl
            ) {
        this.shortenedUrlId = shortenedUrlId;
        this.url = url;
        this.ttl = ttl;
        this.createdAt = Instant.now();
        this.modifiedAt = createdAt;
    }
}
