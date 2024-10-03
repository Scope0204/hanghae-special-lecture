package hhplus.lecture.infra.Impl;

import hhplus.lecture.api.common.exception.LectureException;
import hhplus.lecture.domain.entity.Lecture;
import hhplus.lecture.domain.entity.LectureItem;
import hhplus.lecture.infra.jpa.repository.JpaLectureItemRepository;
import hhplus.lecture.infra.jpa.repository.JpaLectureRepository;
import hhplus.lecture.infra.repository.LectureRepository;
import org.springframework.stereotype.Repository;

@Repository
public class LectureRepositoryImpl implements LectureRepository {
    private final JpaLectureRepository jpaLectureRepository;
    private final JpaLectureItemRepository jpaLectureItemRepository;

    public LectureRepositoryImpl(JpaLectureRepository jpaLectureRepository, JpaLectureItemRepository jpaLectureItemRepository) {
        this.jpaLectureRepository = jpaLectureRepository;
        this.jpaLectureItemRepository = jpaLectureItemRepository;
    }

    @Override
    public Lecture findLectureById(Long lectureId) {
        return jpaLectureRepository.findById(lectureId)
                .orElseThrow(() -> new LectureException("강의 정보를 찾을 수 없습니다."));
    }

    @Override
    public LectureItem findLectureItemById(Long lectureId) {
        return jpaLectureItemRepository.findById(lectureId)
                .orElseThrow(() -> new LectureException("강의 정보를 찾을 수 없습니다."));
    }

    @Override
    public void saveLecture(Lecture lecture) {
        jpaLectureRepository.save(lecture);
    }

    @Override
    public void saveLectureItem(LectureItem lectureItem) {
        jpaLectureItemRepository.save(lectureItem);
    }
}
