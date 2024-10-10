package hhplus.lecture.domain.service;

import hhplus.lecture.domain.dto.UserDto;
import hhplus.lecture.domain.entity.Users;
import hhplus.lecture.infra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto findUserInfo(Long userId) {
        Users user = userRepository.findById(userId);
        return Users.toDto(user);
    }
}
