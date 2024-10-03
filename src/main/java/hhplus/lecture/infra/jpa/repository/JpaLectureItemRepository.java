package hhplus.lecture.infra.jpa.repository;

import hhplus.lecture.domain.entity.LectureItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface JpaLectureItemRepository extends JpaRepository<LectureItem, Long> {
    @Query("select l from LectureItem l where l.enrollmentCnt < l.capacity and l.lectureDate = :date")
    List<LectureItem> findAvailableLectures(@Param("date") LocalDate date);
}
