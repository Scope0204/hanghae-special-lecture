package hhplus.lecture.domain.entity;

import hhplus.lecture.domain.dto.UserDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    public Users(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static UserDto toDto(Users user){
        return new UserDto(
                user.getId(),
                user.getName()
        );
    }
    public static Users fromDto(UserDto dto) {
        return new Users(dto.userId(), dto.name());
    }
}
