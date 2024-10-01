package hhplus.lecture.application.facade;

import hhplus.lecture.domain.service.LectureService;
import hhplus.lecture.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LectureApplyFacade {
    private final UserService userService;
    private final LectureService lectureService;

    @Autowired
    public LectureApplyFacade(UserService userService, LectureService lectureService) {
        this.userService = userService;
        this.lectureService = lectureService;
    }
    public void lectureApply(){
    }

}
