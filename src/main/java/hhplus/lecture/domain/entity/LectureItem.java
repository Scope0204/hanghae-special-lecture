package hhplus.lecture.domain.entity;

import hhplus.lecture.domain.dto.LectureItemDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "lecture_item")
public class LectureItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture;

    @Column(nullable = false)
    private int capacity = 30;

    @Column(nullable = false)
    private int enrollmentCnt = 0;

    @Column(nullable = false)
    private LocalDateTime lectureDate;

    public LectureItem(Long id, Lecture lecture, LocalDateTime lectureDate, int capacity, int enrollmentCnt) {
        this.id = id;
        this.lecture = lecture;
        this.lectureDate = lectureDate;
        this.capacity = capacity;
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
                dto.capacity(),
                dto.enrollmentCnt()
        );
    }

    public void increaseCurrentEnrollmentCount() {
        this.enrollmentCnt++;
    }
}
