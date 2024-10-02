package hhplus.lecture.infra.jpa.repository;

import hhplus.lecture.domain.entity.ApplyHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaApplyHistoryRepository extends JpaRepository<ApplyHistory, Long> {
}
