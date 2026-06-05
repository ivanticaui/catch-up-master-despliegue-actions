package com.acme.catchup.platform.news.infrastructure.persistence.jpa;

import com.acme.catchup.platform.news.domain.model.aggregates.FavoriteSource;
import com.acme.catchup.platform.news.domain.model.valueobjects.NewsApiKey;
import com.acme.catchup.platform.news.domain.model.valueobjects.SourceId;
import com.acme.catchup.platform.news.infrastructure.persistence.jpa.repositories.SpringDataFavoriteSourceJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class FavoriteSourcePersistenceAdapterTest {

    @Autowired
    private SpringDataFavoriteSourceJpaRepository springDataRepository;

    private FavoriteSourcePersistenceAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new FavoriteSourcePersistenceAdapter(springDataRepository);
    }

    @Test
    void shouldSaveFavoriteSource() {
        var favoriteSource = FavoriteSource.create(
                new NewsApiKey("api-key-123"),
                new SourceId("bbc-news")
        );

        var saved = adapter.save(favoriteSource);

        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals(new NewsApiKey("api-key-123"), saved.getNewsApiKey());
        assertEquals(new SourceId("bbc-news"), saved.getSourceId());
    }

    @Test
    void shouldFindFavoriteSourceById() {
        var favoriteSource = FavoriteSource.create(
                new NewsApiKey("api-key-123"),
                new SourceId("bbc-news")
        );

        var saved = adapter.save(favoriteSource);

        var result = adapter.findById(saved.getId());

        assertTrue(result.isPresent());
        assertEquals(saved.getId(), result.get().getId());
        assertEquals(saved.getNewsApiKey(), result.get().getNewsApiKey());
        assertEquals(saved.getSourceId(), result.get().getSourceId());
    }

    @Test
    void shouldReturnEmptyWhenFavoriteSourceDoesNotExistById() {
        var result = adapter.findById(999L);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldFindAllByNewsApiKey() {
        var newsApiKey = new NewsApiKey("api-key-123");

        adapter.save(FavoriteSource.create(newsApiKey, new SourceId("bbc-news")));
        adapter.save(FavoriteSource.create(newsApiKey, new SourceId("cnn")));
        adapter.save(FavoriteSource.create(new NewsApiKey("other-key"), new SourceId("reuters")));

        var result = adapter.findAllByNewsApiKey(newsApiKey);

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(item -> item.getNewsApiKey().equals(newsApiKey)));
    }

    @Test
    void shouldReturnTrueWhenFavoriteSourceExistsByNewsApiKeyAndSourceId() {
        var newsApiKey = new NewsApiKey("api-key-123");
        var sourceId = new SourceId("bbc-news");

        adapter.save(FavoriteSource.create(newsApiKey, sourceId));

        var exists = adapter.existsByNewsApiKeyAndSourceId(newsApiKey, sourceId);

        assertTrue(exists);
    }

    @Test
    void shouldReturnFalseWhenFavoriteSourceDoesNotExistByNewsApiKeyAndSourceId() {
        var exists = adapter.existsByNewsApiKeyAndSourceId(
                new NewsApiKey("api-key-123"),
                new SourceId("bbc-news")
        );

        assertFalse(exists);
    }

    @Test
    void shouldFindByNewsApiKeyAndSourceId() {
        var newsApiKey = new NewsApiKey("api-key-123");
        var sourceId = new SourceId("bbc-news");

        var saved = adapter.save(FavoriteSource.create(newsApiKey, sourceId));

        var result = adapter.findByNewsApiKeyAndSourceId(newsApiKey, sourceId);

        assertTrue(result.isPresent());
        assertEquals(saved.getId(), result.get().getId());
        assertEquals(newsApiKey, result.get().getNewsApiKey());
        assertEquals(sourceId, result.get().getSourceId());
    }

    @Test
    void shouldReturnEmptyWhenFavoriteSourceDoesNotExistByNewsApiKeyAndSourceId() {
        var result = adapter.findByNewsApiKeyAndSourceId(
                new NewsApiKey("api-key-123"),
                new SourceId("bbc-news")
        );

        assertTrue(result.isEmpty());
    }

}