package tech.mouad.book.feedback;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import tech.mouad.book.book.Book;
import tech.mouad.book.common.BaseEntity;

@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FeedBack extends BaseEntity {
    private double note;
    private String comment;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
