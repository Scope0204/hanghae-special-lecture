package hhplus.lecture.infrastruture.repository;

import hhplus.lecture.domain.entity.ApplyHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyHistoryRepository extends JpaRepository<ApplyHistory, Long> {
}
