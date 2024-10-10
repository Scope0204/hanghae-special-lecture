package hhplus.lecture.infra.jpa.repository;

import hhplus.lecture.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<Users, Long> {
}
