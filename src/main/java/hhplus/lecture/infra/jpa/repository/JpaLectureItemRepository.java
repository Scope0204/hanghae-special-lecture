package hhplus.lecture.infra.jpa.repository;

import hhplus.lecture.domain.entity.LectureItem;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface JpaLectureItemRepository extends JpaRepository<LectureItem, Long> {
    @Query("select l from LectureItem l where l.enrollmentCnt < l.capacity and l.lectureDate = :date")
    List<LectureItem> findAvailableLectures(@Param("date") LocalDate date);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT l FROM LectureItem l WHERE l.id = :lectureId")
    LectureItem findWithPessimisticLockById(@Param("lectureId") Long lectureId);
}
