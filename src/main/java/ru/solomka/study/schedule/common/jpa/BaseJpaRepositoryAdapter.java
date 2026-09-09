package ru.solomka.study.schedule.common.jpa;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.solomka.study.schedule.common.Identifiable;
import ru.solomka.study.schedule.exception.EntityAlreadyExistsException;
import ru.solomka.study.schedule.model.mapper.Mapper;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Класс-адаптер для JPA-репозиториев
 *
 * @param <DOMAIN>  тип доменной модели
 * @param <INFRA>  тип JPA-сущности, реализует Identifiable<ID>
 * @param <ID>      тип идентификатора (например, Long)
 */
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public class BaseJpaRepositoryAdapter<DOMAIN extends Identifiable<ID>, INFRA extends Identifiable<ID>, ID>
        implements BaseRepository<DOMAIN, ID> {

    JpaRepository<INFRA, ID> repository;
    Mapper<DOMAIN, INFRA> mapper;

    protected BaseJpaRepositoryAdapter(JpaRepository<INFRA, ID> repository,
                                       Mapper<DOMAIN, INFRA> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<DOMAIN> createAll(List<DOMAIN> entities) {
        if (entities == null || entities.isEmpty()) {
            return Collections.emptyList();
        }

        List<INFRA> infraEntities = entities.stream()
                .map(mapper::mapToInfra)
                .toList();

        List<ID> incomingIds = infraEntities.stream()
                .map(Identifiable::getId)
                .filter(Objects::nonNull)
                .toList();

        if (!incomingIds.isEmpty()) {
            List<INFRA> existingEntities = repository.findAllById(incomingIds);

            if (!existingEntities.isEmpty()) {
                List<ID> conflictingIds = existingEntities.stream()
                        .map(Identifiable::getId)
                        .toList();

                throw new EntityAlreadyExistsException(
                        "Entities with the following IDs already exist in the database: %s".formatted(conflictingIds)
                );
            }
        }

        List<INFRA> savedEntities = repository.saveAll(infraEntities);

        return savedEntities.stream()
                .map(mapper::mapToDomain)
                .toList();
    }

    @Override
    public DOMAIN create(DOMAIN entity) {
        if (entity == null)
            return null;

        if(entity.getId() != null && this.existsById(entity.getId()))
            throw new EntityAlreadyExistsException(
                    "Entity with id '%s' already exists".formatted(entity.getId())
            );

        INFRA saved = repository.save(mapper.mapToInfra(entity));
        return mapper.mapToDomain(saved);
    }

    @Override
    public List<DOMAIN> updateAll(List<DOMAIN> entities) {
        if (entities == null || entities.isEmpty())
            return Collections.emptyList();

        List<INFRA> incomingInfra = entities.stream()
                .map(mapper::mapToInfra)
                .toList();

        List<ID> ids = incomingInfra.stream().map(Identifiable::getId).toList();
        List<INFRA> existingInfra = repository.findAllById(ids);

        Map<ID, INFRA> existingMap = existingInfra.stream()
                .collect(Collectors.toMap(Identifiable::getId, Function.identity()));

        List<INFRA> toUpdate = incomingInfra.stream().filter(incoming -> {
            INFRA existing = existingMap.get(incoming.getId());

            if (existing == null) {
                throw new IllegalArgumentException(
                        "Object with id %s not found in DB".formatted(incoming.getId())
                );
            }

            return !this.isIdentical(existing, incoming);
        }).toList();

        if (toUpdate.isEmpty())
            return Collections.emptyList();

        List<INFRA> savedInfra = repository.saveAll(toUpdate);

        return savedInfra.stream()
                .map(mapper::mapToDomain)
                .toList();
    }

    @Override
    public DOMAIN update(DOMAIN entity) {
        if (entity == null)
            return null;

        INFRA saved = repository.save(mapper.mapToInfra(entity));
        return mapper.mapToDomain(saved);
    }

    @Override
    public int deleteAll(List<DOMAIN> entities) {
        if (entities == null || entities.isEmpty())
            return 0;

        List<INFRA> infraEntities = entities.stream()
                .map(mapper::mapToInfra)
                .toList();
        int size = infraEntities.size();
        repository.deleteAllInBatch(infraEntities);
        return size;
    }

    @Override
    public boolean delete(DOMAIN entity) {
        if (entity == null)
            return false;

        INFRA infraEntity = mapper.mapToInfra(entity);
        repository.delete(infraEntity);

        return true;
    }

    @Override
    public boolean existsById(ID id) {
        return repository.existsById(id);
    }

    @Override
    public Optional<DOMAIN> findById(ID id) {
        return repository.findById(id)
                .map(mapper::mapToDomain);
    }

    @Override
    public List<DOMAIN> findAll() {
        return repository.findAll().stream()
                .map(mapper::mapToDomain)
                .toList();
    }

    @Override
    public List<DOMAIN> findAllById(List<ID> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }
        return repository.findAllById(ids).stream()
                .map(mapper::mapToDomain)
                .toList();
    }

    protected boolean isIdentical(INFRA existing, INFRA incoming) {
        return existing.equals(incoming);
    }
}