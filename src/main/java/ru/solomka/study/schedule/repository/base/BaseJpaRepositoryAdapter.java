package ru.solomka.study.schedule.repository.base;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.solomka.study.schedule.model.Identifiable;
import ru.solomka.study.schedule.model.mapper.Mapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Класс-адаптер для JPA-репозиториев
 *
 * @param <DOMAIN>  тип доменной модели
 * @param <ENTITY>  тип JPA-сущности, реализует Identifiable<ID>
 * @param <ID>      тип идентификатора (например, Long)
 */
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public class BaseJpaRepositoryAdapter<DOMAIN, ENTITY extends Identifiable<ID>, ID>
        implements BaseRepository<DOMAIN, ID> {

    JpaRepository<ENTITY, ID> repository;
    Mapper<DOMAIN, ENTITY> mapper;

    protected BaseJpaRepositoryAdapter(JpaRepository<ENTITY, ID> repository,
                                       Mapper<DOMAIN, ENTITY> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<DOMAIN> createAll(List<DOMAIN> entities) {
        if (entities == null || entities.isEmpty()) {
            return List.of();
        }

        List<ENTITY> infraEntities = entities.stream()
                .map(mapper::mapToInfra)
                .toList();

        List<ENTITY> savedEntities = repository.saveAll(infraEntities);

        return savedEntities.stream()
                .map(mapper::mapToDomain)
                .toList();
    }

    @Override
    public DOMAIN create(DOMAIN entity) {
        if (entity == null) {
            return null;
        }
        ENTITY infraEntity = mapper.mapToInfra(entity);
        ENTITY saved = repository.save(infraEntity);
        return mapper.mapToDomain(saved);
    }

    @Override
    public List<DOMAIN> updateAll(List<DOMAIN> entities) {
        if (entities == null || entities.isEmpty()) {
            return Collections.emptyList();
        }

        List<ENTITY> incomingInfra = entities.stream()
                .map(mapper::mapToInfra)
                .toList();

        List<ID> ids = incomingInfra.stream().map(Identifiable::getId).toList();
        List<ENTITY> existingInfra = repository.findAllById(ids);

        Map<ID, ENTITY> existingMap = existingInfra.stream()
                .collect(Collectors.toMap(Identifiable::getId, Function.identity()));

        List<ENTITY> toUpdate = incomingInfra.stream().filter(incoming -> {
            ENTITY existing = existingMap.get(incoming.getId());

            if (existing == null) {
                throw new IllegalArgumentException("Object with id " + incoming.getId() + " not found in DB");
            }

            return !this.isIdentical(existing, incoming);
        }).toList();

        if (toUpdate.isEmpty())
            return Collections.emptyList();

        List<ENTITY> savedInfra = repository.saveAll(toUpdate);

        return savedInfra.stream()
                .map(mapper::mapToDomain)
                .toList();
    }

    @Override
    public DOMAIN update(DOMAIN entity) {
        if (entity == null) {
            return null;
        }
        ENTITY infraEntity = mapper.mapToInfra(entity);
        ENTITY saved = repository.save(infraEntity);
        return mapper.mapToDomain(saved);
    }

    @Override
    public int deleteAll(List<DOMAIN> entities) {
        if (entities == null || entities.isEmpty())
            return 0;

        List<ENTITY> infraEntities = entities.stream()
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

        ENTITY infraEntity = mapper.mapToInfra(entity);
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

    protected boolean isIdentical(ENTITY existing, ENTITY incoming) {
        return existing.equals(incoming);
    }
}