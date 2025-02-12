CREATE SEQUENCE IF NOT EXISTS url_shortener_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE url_shortener
(
    id               BIGINT       NOT NULL,
    shortened_url_id VARCHAR(255) NOT NULL,
    url              VARCHAR(255) NOT NULL,
    ttl              VARCHAR(255),
    created_at       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    modified_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_url_shortener PRIMARY KEY (id)
);

ALTER TABLE url_shortener
    ADD CONSTRAINT uc_url_shortener_shortened_url UNIQUE (shortened_url_id);