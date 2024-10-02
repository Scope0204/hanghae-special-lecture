package hhplus.lecture.infra.Impl;

import hhplus.lecture.api.common.exception.UserNotFoundException;
import hhplus.lecture.domain.entity.Users;
import hhplus.lecture.infra.jpa.repository.JpaUserRepository;
import hhplus.lecture.infra.repository.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final JpaUserRepository jpaUserRepository;

    public UserRepositoryImpl(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public Users findById(Long userId) {
        return jpaUserRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("유저 정보를 찾을 수 없습니다."));
    }
}
