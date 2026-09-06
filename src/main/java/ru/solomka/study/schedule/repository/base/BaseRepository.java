package ru.solomka.study.schedule.repository.base;


import java.util.List;
import java.util.Optional;

public interface BaseRepository<DOMAIN, ID> {

    List<DOMAIN> createAll(List<DOMAIN> entities);
    DOMAIN create(DOMAIN entity);

    /**
     * Обновляет сущности, у которых есть изменения
     *
     * @param entities JPA-сущности на обновление
     * @return Список сущностей, которые были обновлены
     */
    List<DOMAIN> updateAll(List<DOMAIN> entities);

    DOMAIN update(DOMAIN entity);

    int deleteAll(List<DOMAIN> entities);
    boolean delete(DOMAIN entity);

    boolean existsById(ID id);

    Optional<DOMAIN> findById(ID id);

    List<DOMAIN> findAll();
    List<DOMAIN> findAllById(List<ID> ids);
}
