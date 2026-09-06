package ru.solomka.study.schedule.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.solomka.study.schedule.api.model.assessment.Assessment;
import ru.solomka.study.schedule.api.model.assessment.AssessmentType;
import ru.solomka.study.schedule.api.repository.AssessmentRepository;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AssessmentService {

    AssessmentRepository assessmentRepository;

    public AssessmentService(AssessmentRepository assessmentRepository) {
        this.assessmentRepository = assessmentRepository;
    }

    public List<Assessment> findAllAssessmentByGroupId(String groupId, List<AssessmentType> types) {
        return assessmentRepository.findAllAssessmentByGroupId(groupId, types);
    }
}
