package tech.mouad.book.feedback;

import org.springframework.stereotype.Service;
import tech.mouad.book.book.Book;

import java.util.Objects;

@Service
public class FeedBackMapper {
    public FeedBack toFeedBack(FeedBackRequest feedBackRequest) {
        return FeedBack.builder()
                .note(feedBackRequest.note())
                .comment(feedBackRequest.comment())
                .book(Book.builder().id(feedBackRequest.bookId()).build())
                .build();
    }
    public FeedBackResponse toFeedBackResponse(FeedBack feedBack,Integer userId) {
        return FeedBackResponse.builder()
                .comment(feedBack.getComment())
                .note(feedBack.getNote())
                // ici on a mais created by pour savoir le id de personne qui a cree cela
                .ownFeedBack(Objects.equals(userId,feedBack.getCreatedBy()))
                .build();
    }
}
