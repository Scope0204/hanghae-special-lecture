package hhplus.lecture.infra.Impl;

import hhplus.lecture.domain.entity.ApplyHistory;
import hhplus.lecture.domain.entity.Users;
import hhplus.lecture.infra.jpa.repository.JpaApplyHistoryRepository;
import hhplus.lecture.infra.repository.ApplyHistoryRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ApplyHistoryRepositoryImpl implements ApplyHistoryRepository {
    private final JpaApplyHistoryRepository jpaApplyHistoryRepository;

    public ApplyHistoryRepositoryImpl(JpaApplyHistoryRepository jpaApplyHistoryRepository) {
        this.jpaApplyHistoryRepository = jpaApplyHistoryRepository;
    }

    @Override
    public List<ApplyHistory> findByUserIdAndLectureId(Long userId, Long lectureId) {
        return jpaApplyHistoryRepository.findByUserIdAndLectureId(userId, lectureId);
    }

    @Override
    public List<ApplyHistory> findByUser(Users user) {
        return jpaApplyHistoryRepository.findByUser(user);
    }

    @Override
    public List<ApplyHistory> findAll() {
        return jpaApplyHistoryRepository.findAll();
    }

    @Override
    public ApplyHistory save(ApplyHistory applyHistory) {
        return jpaApplyHistoryRepository.save(applyHistory);
    }
}
