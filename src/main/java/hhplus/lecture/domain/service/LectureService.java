package hhplus.lecture.domain.service;

import hhplus.lecture.api.common.exception.LectureException;
import hhplus.lecture.domain.dto.LectureDto;
import hhplus.lecture.domain.dto.LectureItemDto;
import hhplus.lecture.domain.entity.Lecture;
import hhplus.lecture.domain.entity.LectureItem;
import hhplus.lecture.infra.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LectureService {
    private final LectureRepository lectureRepository;

    @Autowired
    public LectureService(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    public LectureDto findLectureInfo(Long lectureId) {
        Lecture lecture = lectureRepository.findLectureById(lectureId);
        return Lecture.toDto(lecture);
    }

    public LectureItemDto findLectureItemInfo(Long lectureId) {
        //LectureItem lectureItem = lectureRepository.findLectureItemById(lectureId);
        LectureItem lectureItem = lectureRepository.findWithPessimisticLockById(lectureId);
        return LectureItem.toDto(lectureItem);
    }

    public void checkCurrentEnrollmentCount(LectureItemDto lectureItemDto){
        boolean isFullEnrollment = lectureItemDto.enrollmentCnt() == lectureItemDto.capacity(); // true
        if (isFullEnrollment) {
            throw new LectureException("이미 강의 정원이 가득 찼습니다");
        }
    }
    public void increaseCurrentEnrollmentCount(LectureItemDto lectureItemDto){
        LectureItem lectureItem = LectureItem.fromDto(lectureItemDto);
        lectureItem.increaseCurrentEnrollmentCount(); // 현재 등록인원을 1명 추가한다
        lectureRepository.saveLectureItem(lectureItem);
    }

    public List<LectureItemDto> findAvailableLecturesByDate(LocalDate date){
        List<LectureItem> availableLectures = lectureRepository.findAvailableLecturesByDate(date);
        return availableLectures.stream()
                .map(LectureItem::toDto)
                .collect(Collectors.toList());
    }
}
