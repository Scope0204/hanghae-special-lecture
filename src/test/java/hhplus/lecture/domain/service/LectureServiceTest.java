package hhplus.lecture.domain.service;

import hhplus.lecture.api.common.exception.LectureException;
import hhplus.lecture.domain.dto.LectureDto;
import hhplus.lecture.domain.dto.LectureItemDto;
import hhplus.lecture.domain.entity.Lecture;
import hhplus.lecture.domain.entity.LectureItem;
import hhplus.lecture.infra.repository.LectureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * LectureService 유닛 테스트
 */
class LectureServiceTest {

    @Mock
    private LectureRepository lectureRepository;

    @InjectMocks
    private LectureService lectureService;

    @BeforeEach
    void setUp() {
        // Mockito 애노테이션 초기화
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("특강 정보를 반환한다")
    void testFindLectureInfo() {
        // given
        Lecture lecture = new Lecture(1L, "TDD", "허재");
        LectureItem lectureItem = new LectureItem(1L, lecture, LocalDateTime.now(), 30, 0);
        when(lectureRepository.findLectureItemById(1L)).thenReturn(lectureItem);

        // when
        LectureDto result = lectureService.findLectureInfo(1L);

        // then
        assertThat(result.lectureId()).isEqualTo(1L);
        assertThat(result.title()).isEqualTo("TDD");
        assertThat(result.lecturer()).isEqualTo("허재");
        verify(lectureRepository).findLectureItemById(1L);
    }

    @Test
    @DisplayName("특강 상세 정보를 반환한다")
    void testFindLectureItemInfo() {
        // given
        Lecture lecture = new Lecture(1L, "TDD", "허재");
        LectureItem lectureItem = new LectureItem(1L, lecture, LocalDateTime.now(), 30, 0);
        when(lectureRepository.findLectureItemById(1L)).thenReturn(lectureItem);

        // when
        LectureItemDto result = lectureService.findLectureItemInfo(1L);

        // then
        assertThat(result.lectureItemId()).isEqualTo(1L);
        assertThat(result.capacity()).isEqualTo(30);
        assertThat(result.enrollmentCnt()).isEqualTo(0);
        verify(lectureRepository).findLectureItemById(1L);
    }

    @Test
    @DisplayName("특강 등록 인원 수가 최대인 경우 예외를 반환한다.")
    void checkCurrentEnrollmentCount_thenEnrollmentIsFull_shouldThrowException() {
        // given
        Lecture lecture = new Lecture(1L, "TDD", "허재");
        LectureItem lectureItem = new LectureItem(1L, lecture, LocalDateTime.now(), 30, 30);
        LectureItemDto lectureItemDto = LectureItem.toDto(lectureItem);

        // when / then
        assertThatThrownBy(() -> lectureService.checkCurrentEnrollmentCount(lectureItemDto))
                .isInstanceOf(LectureException.class)
                .hasMessage("이미 강의 정원이 가득 찼습니다");
    }
}