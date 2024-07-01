package tech.mouad.book.feedback;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedBackResponse {
    private double note;
    private String comment;
    private boolean ownFeedBack;
}
