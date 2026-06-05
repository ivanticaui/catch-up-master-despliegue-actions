# DDD refactor: dominio separado de infraestructura

## Cambios realizados

- `FavoriteSource` dejó de ser una entidad JPA.
- El dominio ya no importa `jakarta.persistence`, `org.springframework.data.jpa`, converters, repositories JPA ni clases de infraestructura.
- `FavoriteSource` ahora es un Aggregate Root de dominio puro con factories:
  - `create(...)` para crear nuevos agregados.
  - `rehydrate(...)` para reconstruir el agregado desde persistencia.
- Se creó `FavoriteSourceJpaEntity` en infraestructura para representar la tabla y las anotaciones JPA.
- Se creó `SpringDataFavoriteSourceJpaRepository` como repositorio técnico de Spring Data JPA.
- Se creó `FavoriteSourcePersistenceAdapter` como adaptador que implementa el puerto `FavoriteSourceRepository` del dominio.
- Se creó `FavoriteSourcePersistenceMapper` para convertir entre:
  - `FavoriteSourceJpaEntity` → `FavoriteSource`
  - `FavoriteSource` → `FavoriteSourceJpaEntity`
- `NewsApiKeyAttributeConverter` y `SourceIdAttributeConverter` permanecen en infraestructura.

## Regla aplicada

El dominio contiene reglas de negocio y value objects. La infraestructura contiene persistencia, JPA, converters, entidades técnicas, repositorios Spring Data y detalles de base de datos.

## Estructura resultante

```text
news/
  domain/
    model/
      aggregates/FavoriteSource.java
      valueobjects/NewsApiKey.java
      valueobjects/SourceId.java
      repositories/FavoriteSourceRepository.java
  application/
    commands/
    queries/
    commandservices/
    queryservices/
  infrastructure/
    persistence/jpa/
      entities/FavoriteSourceJpaEntity.java
      repositories/SpringDataFavoriteSourceJpaRepository.java
      mappers/FavoriteSourcePersistenceMapper.java
      FavoriteSourcePersistenceAdapter.java
      converters/
  interfaces/
    rest/
```
