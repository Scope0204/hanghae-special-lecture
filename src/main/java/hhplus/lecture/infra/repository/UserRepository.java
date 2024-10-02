package hhplus.lecture.infra.repository;

import hhplus.lecture.domain.entity.Users;

public interface UserRepository {
    Users findById(Long userId);
}
