package ru.solomka.study.schedule.model.mapper;

import org.springframework.stereotype.Component;
import ru.solomka.study.schedule.api.model.UserAdditionalInfo;
import ru.solomka.study.schedule.model.UserAdditionalInfoJpaEntity;

@Component
public class UserAdditionalInfoJpaEntityUserAdditionalInfoMapper implements Mapper<UserAdditionalInfo, UserAdditionalInfoJpaEntity> {

    @Override
    public UserAdditionalInfo mapToDomain(UserAdditionalInfoJpaEntity infra) {
        return new UserAdditionalInfo(
                infra.getId(),
                infra.getFullName(),
                infra.getCathedra(),
                infra.getGroupId(),
                infra.getStudyPeriod(),
                infra.getLevel()
        );
    }

    @Override
    public UserAdditionalInfoJpaEntity mapToInfra(UserAdditionalInfo domain) {
        return UserAdditionalInfoJpaEntity.builder()
                .id(domain.id())
                .fullName(domain.fullName())
                .cathedra(domain.cathedra())
                .groupId(domain.groupId())
                .studyPeriod(domain.studyPeriod())
                .level(domain.levelEducation())
                .build();
    }
}
