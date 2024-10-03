package hhplus.lecture.domain.entity;

import hhplus.lecture.domain.dto.LectureDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "lecture")
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String lecturer;

    public Lecture(Long id, String title, String lecturer) {
        this.id = id;
        this.title = title;
        this.lecturer = lecturer;
    }
    public Lecture(String title, String lecturer) {
        this.title = title;
        this.lecturer = lecturer;
    }

    public static LectureDto toDto(Lecture lecture) {
        return new LectureDto(
                lecture.getId(),
                lecture.getTitle(),
                lecture.getLecturer()
        );
    }

    public static Lecture fromDto(LectureDto dto) {
        return new Lecture(dto.lectureId(), dto.title(), dto.lecturer());
    }
}

