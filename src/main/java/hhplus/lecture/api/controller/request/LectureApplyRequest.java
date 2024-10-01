package hhplus.lecture.api.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LectureApplyRequest {
    private Long userId;
    private Long lectureId;
}
