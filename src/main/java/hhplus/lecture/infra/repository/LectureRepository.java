package hhplus.lecture.infra.repository;

import hhplus.lecture.domain.entity.Lecture;

public interface LectureRepository {
    Lecture findById(Long lectureId);
    void save(Lecture lecture);
}
