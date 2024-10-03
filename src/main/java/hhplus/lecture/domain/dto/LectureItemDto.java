package hhplus.lecture.domain.dto;

import hhplus.lecture.domain.entity.Lecture;

import java.time.LocalDateTime;

public record LectureItemDto(
        Long lectureItemId,
        Lecture lecture,
        LocalDateTime lectureDate,
        int capacity,
        int enrollmentCnt
){
}
