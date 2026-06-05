package com.acme.catchup.platform.news.infrastructure.persistence.jpa.mappers;

import com.acme.catchup.platform.news.domain.model.aggregates.FavoriteSource;
import com.acme.catchup.platform.news.infrastructure.persistence.jpa.entities.FavoriteSourceJpaEntity;

public final class FavoriteSourcePersistenceMapper {
    private FavoriteSourcePersistenceMapper() {
    }

    public static FavoriteSourceJpaEntity toJpaEntity(FavoriteSource aggregate) {
        return new FavoriteSourceJpaEntity(
                aggregate.getId(),
                aggregate.getNewsApiKey(),
                aggregate.getSourceId(),
                aggregate.getCreatedAt(),
                aggregate.getUpdatedAt()
        );
    }

    public static FavoriteSource toDomain(FavoriteSourceJpaEntity entity) {
        return FavoriteSource.rehydrate(
                entity.getId(),
                entity.getNewsApiKey(),
                entity.getSourceId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
