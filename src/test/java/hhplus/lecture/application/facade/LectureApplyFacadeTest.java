package hhplus.lecture.application.facade;

import hhplus.lecture.api.common.exception.ApplyHistoryException;
import hhplus.lecture.api.common.exception.LectureException;
import hhplus.lecture.domain.dto.LectureApplyDto;
import hhplus.lecture.domain.dto.LectureDto;
import hhplus.lecture.domain.dto.LectureItemDto;
import hhplus.lecture.domain.dto.UserDto;
import hhplus.lecture.domain.entity.Lecture;
import hhplus.lecture.domain.service.ApplyHistoryService;
import hhplus.lecture.domain.service.LectureService;
import hhplus.lecture.domain.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;

/**
 * LectureApplyFacade 단위 테스트
 */
class LectureApplyFacadeTest {
    @Mock
    private UserService userService;

    @Mock
    private LectureService lectureService;

    @Mock
    private ApplyHistoryService applyHistoryService;

    @InjectMocks
    private LectureApplyFacade lectureApplyFacade;

    private UserDto userDto;
    private LectureDto lectureDto;
    private LectureItemDto lectureItemDto;

    @BeforeEach
    void setUp() {
        // Mockito 애노테이션 초기화
        MockitoAnnotations.openMocks(this);
        userDto = new UserDto(1L, "jkcho"); // 유저 정보 초기화
        lectureDto = new LectureDto(1L, "TDD", "허재"); // 강의 정보 초기화
        lectureItemDto = new LectureItemDto(1L, Lecture.fromDto(lectureDto), LocalDateTime.now(), 30, 0); // 강의 항목 정보 초기화
    }

    @Test
    @DisplayName("수강 신청에 성공한다")
    void testLectureApplySuccess(){
        // given
        Long userId = 1L;
        Long lectureId = 1L;
        LectureApplyDto lectureApplyDto = new LectureApplyDto(userId, lectureId);

        // when
        when(userService.findUserInfo(userId)).thenReturn(userDto);
        when(lectureService.findLectureInfo(lectureId)).thenReturn(lectureDto);
        when(lectureService.findLectureItemInfo(lectureId)).thenReturn(lectureItemDto);
        doNothing().when(applyHistoryService).checkApplyStatus(userId, lectureId); // 유저가 강의를 등록한 이력이 없다고 가정
        doNothing().when(lectureService).checkCurrentEnrollmentCount(any()); // 현재 강의가 가득 차지 않았다고 가정

        // then
        boolean result = lectureApplyFacade.lectureApply(lectureApplyDto);
        assertThat(result).isTrue();
        verify(lectureService).increaseCurrentEnrollmentCount(lectureItemDto);
        verify(applyHistoryService).save(userDto, lectureDto, true);
    }


    @Test
    @DisplayName("유저가 이미 해당 강의를 신청한 이력이 있다면 강의 신청에 실패한다")
    void testLectureApplyFailUserAlreadyApplied(){
        // given
        Long userId = 1L;
        Long lectureId = 1L;
        LectureApplyDto lectureApplyDto = new LectureApplyDto(userId, lectureId);

        // when
        when(userService.findUserInfo(1L)).thenReturn(userDto);
        when(lectureService.findLectureInfo(1L)).thenReturn(lectureDto);
        when(lectureService.findLectureItemInfo(1L)).thenReturn(lectureItemDto);
        doThrow(new ApplyHistoryException("이미 등록된 강의가 있습니다"))
                .when(applyHistoryService).checkApplyStatus(1L, 1L); // 유저가 강의를 이미 등록한 이력을 가정하여 예외를 던지도록 설정

        // then
        boolean result = lectureApplyFacade.lectureApply(lectureApplyDto);
        assertThat(result).isFalse();
        verify(applyHistoryService).save(userDto, lectureDto, false);
    }

    @Test
    @DisplayName("수강 신청인원이 이미 꽉 찬 경우 강의 신청에 실패한다")
    void testLectureApplyFailFullEnrollment(){
        // given
        Long userId = 1L;
        Long lectureId = 1L;
        LectureApplyDto lectureApplyDto = new LectureApplyDto(userId, lectureId);

        // when
        when(userService.findUserInfo(userId)).thenReturn(userDto);
        when(lectureService.findLectureInfo(lectureId)).thenReturn(lectureDto);
        when(lectureService.findLectureItemInfo(lectureId)).thenReturn(lectureItemDto);
        doNothing().when(applyHistoryService).checkApplyStatus(userId, lectureId); // 유저가 강의를 등록한 이력이 없다고 가정
        doThrow(new LectureException("이미 강의 정원이 가득 찼습니다"))
                .when(lectureService).checkCurrentEnrollmentCount(lectureItemDto); // 현재 강의가 가득 차지 않았다고 가정

        boolean result = lectureApplyFacade.lectureApply(lectureApplyDto);

        // then
        assertThat(result).isFalse();
        verify(applyHistoryService).save(userDto, lectureDto, false);
    }
}