package hhplus.lecture.domain.service;

import hhplus.lecture.api.common.exception.UserNotFoundException;
import hhplus.lecture.domain.dto.UserDto;
import hhplus.lecture.domain.entity.Users;
import hhplus.lecture.infra.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * UserService 유닛 테스트
 */
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        // Mockito 애노테이션 초기화
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("존재하지 않는 학생을 조회하는 경우 예외를 반환한다.")
    void shouldThrowExceptionWhenUserNotFound(){
        Long inValidUserId = -1L;
        // stub
        when(userRepository.findById(inValidUserId)).thenReturn(null);

        // 존재하지 않는 학생을 조회하는 경우 예외를 반환한다(예외메시지도 함께 체크)
        assertThatThrownBy(() -> userService.findUserInfo(inValidUserId))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("유저 정보를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("존재하는 학생을 조회하는 경우 학생의 정보를 반환한다.")
    void shouldReturnUserInfoWhenUserExists(){
        Long userId = 0L;
        String name = "jkcho";
        Users mockUser = mock(Users.class);
        // stub
        when(mockUser.getUserId()).thenReturn(userId);
        when(mockUser.getName()).thenReturn(name);
        when(userRepository.findById(userId)).thenReturn(mockUser);

        UserDto UserInfo = userService.findUserInfo(userId);
        assertThat(UserInfo.userId()).isEqualTo(userId);
        assertThat(UserInfo.name()).isEqualTo(name);
    }
}