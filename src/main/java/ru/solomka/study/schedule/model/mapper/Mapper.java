package ru.solomka.study.schedule.model.mapper;

public interface Mapper<D, I> {

    D mapToDomain(I infra);

    I mapToInfra(D domain);
}
