package hhplus.lecture.infra.repository;

import hhplus.lecture.domain.entity.Lecture;
import hhplus.lecture.domain.entity.LectureItem;

public interface LectureRepository {
    Lecture findLectureById(Long lectureId);
    LectureItem findLectureItemById(Long lectureId);
    void saveLecture(Lecture lecture);
    void saveLectureItem(LectureItem lectureItem);
}
