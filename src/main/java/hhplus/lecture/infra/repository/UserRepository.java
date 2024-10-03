package hhplus.lecture.infra.repository;

import hhplus.lecture.domain.entity.Users;

import java.util.List;

public interface UserRepository {
    List<Users> findAll();
    Users findById(Long userId);
    void save(Users user);
}
