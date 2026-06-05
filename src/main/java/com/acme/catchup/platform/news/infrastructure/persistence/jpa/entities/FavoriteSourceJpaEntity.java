package com.acme.catchup.platform.news.infrastructure.persistence.jpa.entities;

import com.acme.catchup.platform.news.domain.model.valueobjects.NewsApiKey;
import com.acme.catchup.platform.news.domain.model.valueobjects.SourceId;
import com.acme.catchup.platform.news.infrastructure.persistence.jpa.converters.NewsApiKeyAttributeConverter;
import com.acme.catchup.platform.news.infrastructure.persistence.jpa.converters.SourceIdAttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "favorite_source", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"news_api_key", "source_id"}, name = FavoriteSourceJpaEntity.NEWS_API_KEY_SOURCE_ID_UNIQUE_CONSTRAINT)
})
public class FavoriteSourceJpaEntity {

    public static final String NEWS_API_KEY_SOURCE_ID_UNIQUE_CONSTRAINT = "uk_favorite_source_news_api_key_source_id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "news_api_key", nullable = false, length = 256)
    @Convert(converter = NewsApiKeyAttributeConverter.class)
    private NewsApiKey newsApiKey;

    @Column(name = "source_id", nullable = false, length = 256)
    @Convert(converter = SourceIdAttributeConverter.class)
    private SourceId sourceId;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Instant createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private Instant updatedAt;

    public FavoriteSourceJpaEntity(Long id, NewsApiKey newsApiKey, SourceId sourceId, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.newsApiKey = newsApiKey;
        this.sourceId = sourceId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
