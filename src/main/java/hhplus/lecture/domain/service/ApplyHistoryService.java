package hhplus.lecture.domain.service;

import hhplus.lecture.api.common.exception.ApplyHistoryException;
import hhplus.lecture.domain.dto.LectureDto;
import hhplus.lecture.domain.dto.UserDto;
import hhplus.lecture.domain.entity.ApplyHistory;
import hhplus.lecture.domain.entity.Lecture;
import hhplus.lecture.domain.entity.Users;
import hhplus.lecture.infra.repository.ApplyHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ApplyHistoryService {
    private final ApplyHistoryRepository applyHistoryRepository;

    @Autowired
    public ApplyHistoryService(ApplyHistoryRepository applyHistoryRepository) {
        this.applyHistoryRepository = applyHistoryRepository;
    }
    public List<LectureDto> findApplyHistoryStatusTrue(Users user){
        List<ApplyHistory> applyHistories = applyHistoryRepository.findByUser(user);

        // applyStatus가 true인 ApplyHistory만 필터링하고, ApplyHistoryDto로 변환
        return Optional.ofNullable(applyHistories)
                .orElse(Collections.emptyList()) // null일 경우 빈 리스트 반환
                .stream()
                .filter(ApplyHistory::isApplyStatus) // applyStatus가 true인 항목만 필터링
                .map(ApplyHistory::getLecture) // ApplyHistory에서 Lecture 객체 추출
                .map(Lecture::toDto)
                .collect(Collectors.toList());
    }

    public void checkApplyStatus(Long userId, Long lectureId){
        if (userId == null || lectureId == null) {
            throw new IllegalArgumentException("유저 ID와 강의 ID는 null일 수 없습니다.");
        }
        // applyStatus true인 경우 예외 발생
        boolean hasAppliedStatus = applyHistoryRepository.findByUserIdAndLectureId(userId,lectureId)
                .stream().anyMatch(ApplyHistory::isApplyStatus);
        if (hasAppliedStatus) {
            throw new ApplyHistoryException("이미 등록된 강의가 있습니다");
        }
    }
    public void save(UserDto userDto, LectureDto lectureDto, Boolean applyStatus){
        Users user = Users.fromDto(userDto);
        Lecture lecture = Lecture.fromDto(lectureDto);
        ApplyHistory applyHistory = new ApplyHistory(user,lecture,applyStatus);
        applyHistoryRepository.save(applyHistory);
    }
}
