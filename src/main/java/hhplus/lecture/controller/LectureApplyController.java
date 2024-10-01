package hhplus.lecture.controller;

import hhplus.lecture.application.facade.LectureApplyFacade;
import hhplus.lecture.controller.request.LectureApplyRequest;
import hhplus.lecture.controller.response.LectureApplyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/lectures")
public class LectureApplyController {

    private final LectureApplyFacade lectureApplyFacade; // 특강 요청을 처리

    /**
     * 선착순으로 제공되는 특강을 신청하는 API
     * @param lectureApplyRequest
     * @return LectureApplyResponse
     */
    @PostMapping("/apply")
    public LectureApplyResponse lectureApply(
            @RequestBody LectureApplyRequest lectureApplyRequest
    ) {
        return new LectureApplyResponse("수강 신청 완료!",true);
    }
}
