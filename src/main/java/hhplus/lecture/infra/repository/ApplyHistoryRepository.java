package hhplus.lecture.infra.repository;

import hhplus.lecture.domain.entity.ApplyHistory;

import java.util.List;

public interface ApplyHistoryRepository {
    List<ApplyHistory> findByUserIdAndLectureId(Long userId, Long lectureId);
    List<ApplyHistory> findByUserId(Long userId);
    ApplyHistory save(ApplyHistory applyHistory);
}
