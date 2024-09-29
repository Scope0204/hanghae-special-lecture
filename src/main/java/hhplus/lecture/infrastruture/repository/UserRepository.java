package hhplus.lecture.infrastruture.repository;

import hhplus.lecture.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
}
