package hhplus.lecture.domain.entity;

import hhplus.lecture.domain.dto.LectureItemDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "lecture_item")
public class LectureItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_item_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture;

    @Column(nullable = false)
    private final int capacity = 30;

    @Column(nullable = false)
    private int enrollmentCnt = 0;

    @Column(nullable = false)
    private LocalDate lectureDate;

    public LectureItem(Long id, Lecture lecture, LocalDate lectureDate, int enrollmentCnt) {
        this.id = id;
        this.lecture = lecture;
        this.lectureDate = lectureDate;
        this.enrollmentCnt = enrollmentCnt;
    }

    public static LectureItemDto toDto(LectureItem lectureItem) {
        return new LectureItemDto(
                lectureItem.getId(),
                lectureItem.getLecture(),
                lectureItem.getLectureDate(),
                lectureItem.getCapacity(),
                lectureItem.getEnrollmentCnt()
        );
    }

    public static LectureItem fromDto(LectureItemDto dto) {
        return new LectureItem(
                dto.lectureItemId(),
                dto.lecture(),
                dto.lectureDate(),
                dto.enrollmentCnt()
        );
    }

    public void increaseCurrentEnrollmentCount() {
        this.enrollmentCnt++;
    }
}
