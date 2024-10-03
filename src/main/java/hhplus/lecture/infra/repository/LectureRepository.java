package hhplus.lecture.infra.repository;

import hhplus.lecture.domain.entity.Lecture;
import hhplus.lecture.domain.entity.LectureItem;

import java.time.LocalDate;
import java.util.List;

public interface LectureRepository {
    Lecture findLectureById(Long lectureId);
    LectureItem findLectureItemById(Long lectureId);
    LectureItem findWithPessimisticLockById(Long lectureId);
    List<LectureItem> findAvailableLecturesByDate(LocalDate date);
    void saveLecture(Lecture lecture);
    void saveLectureItem(LectureItem lectureItem);
}
