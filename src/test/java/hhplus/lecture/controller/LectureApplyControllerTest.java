package hhplus.lecture.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hhplus.lecture.controller.request.LectureApplyRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class LectureApplyControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // lectures/apply
    @Test
    @DisplayName("특강 신청을 성공한다.")
    void successLectureApply() throws Exception {
        // given
        Long userId = 0L;
        Long lectureId = 0L;
        LectureApplyRequest applyRequest = new LectureApplyRequest(userId, lectureId);

        // when & then : patch 요청 수행 후 응답 상태와 반환되는 값 검증
        mockMvc.perform(post("/lectures/apply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(applyRequest)))
                .andExpect(status().isOk()) // response 200
                .andExpect(jsonPath("$.message").value("수강 신청 완료!"))
                .andExpect(jsonPath("$.result").value(true));
    }
}