package hhplus.lecture.domain.service;

import hhplus.lecture.domain.dto.LectureDto;
import hhplus.lecture.domain.entity.Lecture;
import hhplus.lecture.infra.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LectureService {
    private final LectureRepository lectureRepository;

    @Autowired
    public LectureService(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    public LectureDto findLectureInfo(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId);
        return new LectureDto(lecture.getLectureId(), lecture.getTitle(), lecture.getLecturer());
    }
}
