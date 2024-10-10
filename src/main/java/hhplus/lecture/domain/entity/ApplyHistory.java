package hhplus.lecture.domain.entity;

import hhplus.lecture.domain.dto.ApplyHistoryDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "apply_history")
public class ApplyHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture;

    @Column(nullable = false)
    private boolean applyStatus;

    public ApplyHistory(Users user, Lecture lecture, Boolean applyStatus){
        this.user = user;
        this.lecture = lecture;
        this.applyStatus = applyStatus;
    }

    public static ApplyHistoryDto toDto(ApplyHistory applyHistory) {
        return new ApplyHistoryDto(
                applyHistory.getId(),
                applyHistory.getUser(),
                applyHistory.getLecture(),
                applyHistory.isApplyStatus()
        );
    }
}
