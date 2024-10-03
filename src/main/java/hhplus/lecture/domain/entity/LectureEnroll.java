package hhplus.lecture.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "lecture_enroll")
public class LectureEnroll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_enroll_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "lecture_item_id", nullable = false)
    private LectureItem lectureItem;

    @ManyToOne
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture;

    @Column(nullable = false)
    private int enrollmentCnt;
}
