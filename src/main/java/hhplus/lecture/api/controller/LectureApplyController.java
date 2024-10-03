package hhplus.lecture.api.controller;

import hhplus.lecture.api.controller.request.LectureApplyRequest;
import hhplus.lecture.api.controller.response.LectureApplyResponse;
import hhplus.lecture.application.facade.LectureApplyFacade;
import hhplus.lecture.domain.dto.LectureApplyDto;
import hhplus.lecture.domain.dto.LectureDto;
import hhplus.lecture.domain.dto.LectureItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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
        // LectureApplyRequest에서 필요한 데이터를 추출하여 LectureApplyDto로 변환
        LectureApplyDto lectureApplyDto = new LectureApplyDto(
                lectureApplyRequest.getUserId(),
                lectureApplyRequest.getLectureId()
        );
        Boolean applyResult = lectureApplyFacade.lectureApply(lectureApplyDto);
        return new LectureApplyResponse("수강 신청 완료!",applyResult);
    }

    /**
     * 날짜별로 현재 신청 가능한 특강 목록을 조회하는 API
     */
    @GetMapping("")
    public ResponseEntity<List<LectureItemDto>> getLectures(
            @RequestParam LocalDate date) {
        return ResponseEntity.ok(lectureApplyFacade.lecturesAvailable(date));
    }

    /**
     * 특강 신청 완료 목록을 조회하는 API
     */
    @GetMapping("/{userId}/users")
    public ResponseEntity<List<LectureDto>> getApplyStatus(
            @PathVariable Long userId
    ){
        return ResponseEntity.ok(lectureApplyFacade.lectureApplyStatus(userId));
    }

}
