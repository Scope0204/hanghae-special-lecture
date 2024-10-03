package hhplus.lecture.domain.service;

import hhplus.lecture.api.common.exception.ApplyHistoryException;
import hhplus.lecture.domain.dto.LectureDto;
import hhplus.lecture.domain.dto.UserDto;
import hhplus.lecture.domain.entity.ApplyHistory;
import hhplus.lecture.infra.repository.ApplyHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class ApplyHistoryServiceTest {

    @Mock
    private ApplyHistoryRepository applyHistoryRepository;

    @InjectMocks
    private ApplyHistoryService applyHistoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("유저가 이미 등록된 강의가 있는 경우 예외가 발생한다")
    void testCheckApplyStatus_userAlreadyApplied_shouldThrowException() {
        Long userId = 1L;
        Long lectureId = 1L;
        ApplyHistory existingApplyHistory = mock(ApplyHistory.class);
        // stub
        when(existingApplyHistory.isApplyStatus()).thenReturn(true);
        when(applyHistoryRepository.findByUserIdAndLectureId(userId, lectureId))
                .thenReturn(Collections.singletonList(existingApplyHistory));

        assertThatThrownBy(() -> applyHistoryService.checkApplyStatus(userId, lectureId))
                .isInstanceOf(ApplyHistoryException.class)
                .hasMessage("이미 등록된 강의가 있습니다");
    }

    @Test
    @DisplayName("유저가 등록된 강의가 없으면 예외가 발생하지 않는다")
    void testCheckApplyStatus_userNotApplied_shouldNotThrowException() {
        // given
        Long userId = 1L;
        Long lectureId = 1L;

        when(applyHistoryRepository.findByUserIdAndLectureId(userId, lectureId))
                .thenReturn(Collections.emptyList());

        // when, then
        assertDoesNotThrow(() -> applyHistoryService.checkApplyStatus(userId, lectureId));
    }

    @Test
    @DisplayName("ApplyHistory 를 저장한다")
    void saveApplyHistory() {
        // given
        UserDto userDto = new UserDto(1L, "홍길동");
        LectureDto lectureDto = new LectureDto(1L, "강의 제목", "강사");

        // when, then
        assertDoesNotThrow(() -> applyHistoryService.save(userDto, lectureDto, true));
        verify(applyHistoryRepository).save(any(ApplyHistory.class)); // save 메서드 호출 검증
    }
}