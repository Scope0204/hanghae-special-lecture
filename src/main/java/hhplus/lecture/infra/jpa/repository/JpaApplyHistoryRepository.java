package hhplus.lecture.infra.jpa.repository;

import hhplus.lecture.domain.entity.ApplyHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaApplyHistoryRepository extends JpaRepository<ApplyHistory, Long> {
    List<ApplyHistory> findByUserIdAndLectureId(Long UserId, Long lectureId);
    List<ApplyHistory> findByUserId(Long UserId);
}
