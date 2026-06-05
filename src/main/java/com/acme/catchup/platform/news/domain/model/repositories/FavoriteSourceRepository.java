package com.acme.catchup.platform.news.domain.model.repositories;

import com.acme.catchup.platform.news.domain.model.aggregates.FavoriteSource;
import com.acme.catchup.platform.news.domain.model.valueobjects.NewsApiKey;
import com.acme.catchup.platform.news.domain.model.valueobjects.SourceId;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository port for FavoriteSource aggregates.
 *
 * The application layer depends on this abstraction. The persistence technology
 * is supplied by an infrastructure adapter.
 */
public interface FavoriteSourceRepository {
    FavoriteSource save(FavoriteSource favoriteSource);

    Optional<FavoriteSource> findById(Long id);

    List<FavoriteSource> findAllByNewsApiKey(NewsApiKey newsApiKey);

    boolean existsByNewsApiKeyAndSourceId(NewsApiKey newsApiKey, SourceId sourceId);

    Optional<FavoriteSource> findByNewsApiKeyAndSourceId(NewsApiKey newsApiKey, SourceId sourceId);
}
