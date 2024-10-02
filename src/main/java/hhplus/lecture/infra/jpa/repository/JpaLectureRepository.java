package hhplus.lecture.infra.jpa.repository;

import hhplus.lecture.domain.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLectureRepository extends JpaRepository<Lecture, Long> {
}
