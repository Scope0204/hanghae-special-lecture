package hhplus.lecture.infra.repository;

import hhplus.lecture.domain.entity.ApplyHistory;
import hhplus.lecture.domain.entity.Users;

import java.util.List;

public interface ApplyHistoryRepository {
    List<ApplyHistory> findByUserIdAndLectureId(Long userId, Long lectureId);
    List<ApplyHistory> findByUser(Users user);
    List<ApplyHistory> findAll();
    ApplyHistory save(ApplyHistory applyHistory);
}
