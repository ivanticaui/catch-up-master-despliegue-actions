package com.acme.catchup.platform.news.infrastructure.persistence.jpa;

import com.acme.catchup.platform.news.domain.model.aggregates.FavoriteSource;
import com.acme.catchup.platform.news.domain.model.repositories.FavoriteSourceRepository;
import com.acme.catchup.platform.news.domain.model.valueobjects.NewsApiKey;
import com.acme.catchup.platform.news.domain.model.valueobjects.SourceId;
import com.acme.catchup.platform.news.infrastructure.persistence.jpa.mappers.FavoriteSourcePersistenceMapper;
import com.acme.catchup.platform.news.infrastructure.persistence.jpa.repositories.SpringDataFavoriteSourceJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class FavoriteSourcePersistenceAdapter implements FavoriteSourceRepository {

    private final SpringDataFavoriteSourceJpaRepository repository;

    public FavoriteSourcePersistenceAdapter(SpringDataFavoriteSourceJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public FavoriteSource save(FavoriteSource favoriteSource) {
        var entity = FavoriteSourcePersistenceMapper.toJpaEntity(favoriteSource);
        var savedEntity = repository.save(entity);
        return FavoriteSourcePersistenceMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<FavoriteSource> findById(Long id) {
        return repository.findById(id).map(FavoriteSourcePersistenceMapper::toDomain);
    }

    @Override
    public List<FavoriteSource> findAllByNewsApiKey(NewsApiKey newsApiKey) {
        return repository.findAllByNewsApiKey(newsApiKey).stream()
                .map(FavoriteSourcePersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByNewsApiKeyAndSourceId(NewsApiKey newsApiKey, SourceId sourceId) {
        return repository.existsByNewsApiKeyAndSourceId(newsApiKey, sourceId);
    }

    @Override
    public Optional<FavoriteSource> findByNewsApiKeyAndSourceId(NewsApiKey newsApiKey, SourceId sourceId) {
        return repository.findByNewsApiKeyAndSourceId(newsApiKey, sourceId)
                .map(FavoriteSourcePersistenceMapper::toDomain);
    }
}
