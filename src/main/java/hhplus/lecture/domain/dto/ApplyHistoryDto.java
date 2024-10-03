package hhplus.lecture.domain.dto;

import hhplus.lecture.domain.entity.Lecture;
import hhplus.lecture.domain.entity.Users;

public record ApplyHistoryDto(
        Long historyId,
        Users user,
        Lecture lecture,
        boolean applyStatus
){
}
