package hhplus.lecture;

import hhplus.lecture.application.facade.LectureApplyFacade;
import hhplus.lecture.domain.dto.LectureApplyDto;
import hhplus.lecture.domain.entity.ApplyHistory;
import hhplus.lecture.domain.entity.Lecture;
import hhplus.lecture.domain.entity.LectureItem;
import hhplus.lecture.domain.entity.Users;
import hhplus.lecture.domain.service.ApplyHistoryService;
import hhplus.lecture.domain.service.LectureService;
import hhplus.lecture.domain.service.UserService;
import hhplus.lecture.infra.repository.ApplyHistoryRepository;
import hhplus.lecture.infra.repository.LectureRepository;
import hhplus.lecture.infra.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class LectureApplyConcurrencyTest {

    @Autowired
    private LectureApplyFacade lectureApplyFacade;
    @Autowired
    private UserService userService;
    @Autowired
    private LectureService lectureService;
    @Autowired
    private ApplyHistoryService applyHistoryService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private ApplyHistoryRepository applyHistoryRepository;

    @BeforeEach
    void setUp() {
        Lecture lecture = new Lecture("lecture","hanghae");
        lectureRepository.saveLecture(lecture);
        lectureRepository.saveLectureItem(new LectureItem(1L,lecture,LocalDate.now(),0));

        for (int i = 1; i <= 40; i++) {
            userRepository.save(new Users("User_" +i));
        }
    }

    @Test
    @DisplayName("동시에 동일한 특강에 대해 40명이 신청했을 때, 30명만 성공하는 것을 검증한다.")
    void shouldAllowOnly30EnrollmentsWhen40ApplySimultaneously() throws InterruptedException {
        int threadCount = 40;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        Long lectureId = 1L;
        List<Users> users = userRepository.findAll();

        for (int i = 0; i < threadCount; i++) {
            final int index = i;
            executorService.submit(() -> {
                try {
                    LectureApplyDto lectureApplyDto = new LectureApplyDto(users.get(index).getId(), lectureId);
                    lectureApplyFacade.lectureApply(lectureApplyDto);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        // then
        LectureItem result = lectureRepository.findLectureItemById(lectureId);
        assertThat(result.getEnrollmentCnt()).isEqualTo(30); // 특강 정원이 30명인지 확인

        List<ApplyHistory> histories = applyHistoryRepository.findAll();
        assertThat(histories.size()).isEqualTo(40);
        assertThat(histories.stream().filter(history -> history.isApplyStatus() == true).count()).isEqualTo(30);
        assertThat(histories.stream().filter(history -> history.isApplyStatus() == false).count()).isEqualTo(10);
    }

    @Test
    @DisplayName("동일한 회원이 동시에 강의 5번 신청 시, 1번만 강의 신청 되는 것을 검증한다")
    void shouldAllowSingleEnrollmentWhenStudentApplies5TimesSimultaneously() throws InterruptedException {
        Long userId = 1L;
        Long lectureId = 1L;

        int threadCount = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 1; i <= threadCount; i++) {
            executorService.submit(() -> {
                try {
                    LectureApplyDto lectureApplyDto = new LectureApplyDto(userId, lectureId);
                    lectureApplyFacade.lectureApply(lectureApplyDto);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        // then
        // applyHistoryRepository에서 특정 userId와 lectureId로 저장된 신청 기록을 조회
        List<ApplyHistory> histories = applyHistoryRepository.findByUserIdAndLectureId(userId, lectureId);

        // 신청 기록이 1건만 성공했는지 확인
        long successCount = histories.stream()
                .filter(history -> history.isApplyStatus() == true)
                .count();

        long failCount = histories.stream()
                .filter(history -> history.isApplyStatus() == false)
                .count();

        // 성공한 신청이 1건이어야 함
        assertThat(successCount).isEqualTo(1);

        // 실패한 신청이 4건이어야 함
        assertThat(failCount).isEqualTo(4);
    }

}
