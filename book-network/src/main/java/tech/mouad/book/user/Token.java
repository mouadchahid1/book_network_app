package tech.mouad.book.user;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Token {
    @Id
    @GeneratedValue
    private Integer id;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime validatedAt;
    private LocalDateTime expiredAt;
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

}
