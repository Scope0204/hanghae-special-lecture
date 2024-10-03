package hhplus.lecture.application.facade;

import hhplus.lecture.domain.dto.LectureApplyDto;
import hhplus.lecture.domain.dto.LectureDto;
import hhplus.lecture.domain.dto.LectureItemDto;
import hhplus.lecture.domain.dto.UserDto;
import hhplus.lecture.domain.entity.Users;
import hhplus.lecture.domain.service.ApplyHistoryService;
import hhplus.lecture.domain.service.LectureService;
import hhplus.lecture.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LectureApplyFacade {
    private final UserService userService;
    private final LectureService lectureService;
    private final ApplyHistoryService applyHistoryService;

    @Autowired
    public LectureApplyFacade(UserService userService, LectureService lectureService, ApplyHistoryService applyHistoryService) {
        this.userService = userService;
        this.lectureService = lectureService;
        this.applyHistoryService = applyHistoryService;
    }
    @Transactional
    public boolean lectureApply(LectureApplyDto lectureApplyDto){
        // 유저를 조회
        UserDto user = userService.findUserInfo(lectureApplyDto.userId());
        // 강의를 조회
        LectureDto lecture = lectureService.findLectureInfo(lectureApplyDto.lectureId());
        LectureItemDto lectureItem = lectureService.findLectureItemInfo(lectureApplyDto.lectureId());
        try{
            // 강의 신청 가능한지에 대한 유효성을 검증
            validateApplicationForLecture(lectureApplyDto.userId(), lecture.lectureId(), lectureItem);
            // 선착순으로 강의 신청을 한다. (동시성 고려)
            applyLecture(user,lecture,lectureItem);
        }
        catch(Exception e){
            // 강의신청 기록을 저장한다. (ApplyHistory 실패)
            saveApplyFailedHistory(user,lecture);
            return false;
        }
        return true;
    }

    /**
     * 강의 신청 가능한지에 대한 유효성을 검증한다
     * @param userId
     * @param lectureId
     */
    private void validateApplicationForLecture(Long userId, Long lectureId, LectureItemDto lectureItemDto){
        // 1. 유저가 강의를 이미 등록했는지 찾는다.(ApplyHistory applyStatus)
        applyHistoryService.checkApplyStatus(userId,lectureId);
        // 2. 현재 강의가 꽉 찼는지 확인한다.
        lectureService.checkCurrentEnrollmentCount(lectureItemDto);
    }

    /**
     * 선착순으로 강의를 신청한다(동시성을 고려할 수 있도록 한다)
     * @param user
     * @param lecture
     */
    private void applyLecture(UserDto user, LectureDto lecture, LectureItemDto lectureItem) {
        // lectureItem 의 currentEnrollmentCount 을 1 증가 시킨다.
        lectureService.increaseCurrentEnrollmentCount(lectureItem);
        // 강의신청 기록에 저장한다. (ApplyHistory 성공)
        applyHistoryService.save(user,lecture,true);
    }

    /**
     * 다음 강의 신청 실패 케이스에 해당되는경우 history 에 false 로 저장한다
     * 1. 이미 학생이 강의를 등록했는데 중복해서 강의 수강 신청을 하는 경우
     * 2. 선착순에 들지 못해서 강의신청을 하지 못한 경우
     * @param user
     * @param lecture
     */
    private void saveApplyFailedHistory(UserDto user, LectureDto lecture) {
        // 강의신청 기록에 저장한다. (ApplyHistory 실패)
        applyHistoryService.save(user,lecture,false);
    }

    // TODO : 강의 목록 조회
    /**
     * 수강 신청 완료 여부를 확인한.특정 userId 로 신청 완료된 특강 목록을 조회
     * 각 항목은 특강 ID 및 이름, 강연자 정보를 담고 있어야 합니다.
     */
    public List<LectureDto> lectureApplyStatus(Long userId){
        // 1. 존재하는 유저인지 검증한다.
        UserDto user = userService.findUserInfo(userId);
        // 2. 신청 이력에서 해당 유저가 신청한 이력을 가져온다
        return applyHistoryService.findApplyHistoryStatusTrue(Users.fromDto(user));
    }
}
