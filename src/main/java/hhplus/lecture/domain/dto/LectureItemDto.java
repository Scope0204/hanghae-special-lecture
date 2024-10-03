package hhplus.lecture.domain.dto;

import hhplus.lecture.domain.entity.Lecture;

import java.time.LocalDate;

public record LectureItemDto(
        Long lectureItemId,
        Lecture lecture,
        LocalDate lectureDate,
        int capacity,
        int enrollmentCnt
){
}
