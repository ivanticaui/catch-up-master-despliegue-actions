package com.acme.catchup.platform.news.domain.model.aggregates;

import com.acme.catchup.platform.news.domain.model.valueobjects.NewsApiKey;
import com.acme.catchup.platform.news.domain.model.valueobjects.SourceId;

import java.time.Instant;
import java.util.Objects;

/**
 * Pure domain aggregate root for a favorite news source.
 *
 * This class intentionally has no JPA, Spring, Lombok or infrastructure annotations.
 * It only protects business invariants and exposes intention-revealing behavior.
 */
public class FavoriteSource {

    private final Long id;
    private final NewsApiKey newsApiKey;
    private final SourceId sourceId;
    private final Instant createdAt;
    private final Instant updatedAt;

    private FavoriteSource(Long id, NewsApiKey newsApiKey, SourceId sourceId, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.newsApiKey = Objects.requireNonNull(newsApiKey, "favorite.source.error.newsApiKey.notBlank");
        this.sourceId = Objects.requireNonNull(sourceId, "favorite.source.error.sourceId.notBlank");
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static FavoriteSource create(NewsApiKey newsApiKey, SourceId sourceId) {
        return new FavoriteSource(null, newsApiKey, sourceId, null, null);
    }

    public static FavoriteSource rehydrate(Long id, NewsApiKey newsApiKey, SourceId sourceId, Instant createdAt, Instant updatedAt) {
        return new FavoriteSource(id, newsApiKey, sourceId, createdAt, updatedAt);
    }

    public Long getId() {
        return id;
    }

    public NewsApiKey getNewsApiKey() {
        return newsApiKey;
    }

    public SourceId getSourceId() {
        return sourceId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
