package hhplus.lecture.api.common.exception.handler;

import hhplus.lecture.api.common.exception.ErrorResponse;
import hhplus.lecture.api.common.exception.LectureException;
import hhplus.lecture.api.common.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class LectureApplyControllerAdvice  extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("400", "유저 정보를 찾을 수 없습니다."));
    }
    @ExceptionHandler(value = LectureException.class)
    public ResponseEntity<ErrorResponse> handleLectureException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("400", "특강 정보를 찾을 수 없습니다."));
    }
}
