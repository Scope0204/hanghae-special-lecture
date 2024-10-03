package hhplus.lecture.infra.jpa.repository;

import hhplus.lecture.domain.entity.LectureItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLectureItemRepository extends JpaRepository<LectureItem, Long> {
}
