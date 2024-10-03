package hhplus.lecture.infra.Impl;

import hhplus.lecture.api.common.exception.LectureException;
import hhplus.lecture.domain.entity.Lecture;
import hhplus.lecture.infra.jpa.repository.JpaLectureRepository;
import hhplus.lecture.infra.repository.LectureRepository;
import org.springframework.stereotype.Repository;

@Repository
public class LectureRepositoryImpl implements LectureRepository {
    private final JpaLectureRepository jpaLectureRepository;

    public LectureRepositoryImpl(JpaLectureRepository jpaLectureRepository) {
        this.jpaLectureRepository = jpaLectureRepository;
    }

    @Override
    public Lecture findById(Long lectureId) {
        return jpaLectureRepository.findById(lectureId)
                .orElseThrow(() -> new LectureException("유저 정보를 찾을 수 없습니다."));
    }

    @Override
    public void save(Lecture lecture) {
        jpaLectureRepository.save(lecture);
    }
}
