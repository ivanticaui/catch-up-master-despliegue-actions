# DDD refactor notes

This version applies a cleaner DDD-oriented separation for the `news` bounded context.

## Main corrections

- Commands were moved from `domain.model.commands` to `application.commands`.
- Queries were moved from `domain.model.queries` to `application.queries`.
- The `FavoriteSource` aggregate no longer receives an application command in its constructor.
- Aggregate creation is now expressed through `FavoriteSource.create(newsApiKey, sourceId)`.
- The domain no longer imports infrastructure JPA converters.
- Value object persistence is handled by infrastructure converters with `@Converter(autoApply = true)`.
- A domain repository port was introduced in `domain.model.repositories.FavoriteSourceRepository`.
- The JPA adapter is now `infrastructure.persistence.jpa.JpaFavoriteSourceRepository`.
- Application services depend on the repository port, not on the Spring Data infrastructure adapter.
- The main application entry point is now `public static void main`.
- Maven Java release was adjusted to Java 21 for a more realistic LTS setup.

## Layer responsibility

### Domain
Contains aggregates, value objects and repository ports. It protects business invariants and avoids depending on application or infrastructure concerns.

### Application
Contains commands, queries, command services and query services. It coordinates use cases and transactions.

### Infrastructure
Contains persistence adapters and JPA converters.

### Interfaces
Contains REST controllers, resources and assemblers.
