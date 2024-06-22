package tech.mouad.book.book;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import tech.mouad.book.bookTransactionHistory.BookTransactionHistory;
import tech.mouad.book.common.BaseEntity;
import tech.mouad.book.feedback.FeedBack;
import tech.mouad.book.user.User;

import java.util.List;

@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Book extends BaseEntity {
    private String title;
    private String authorName;
    private String isbn;
    private String synopsis;
    private String bookCover;
    private boolean archived;
    private boolean shareable;
    @OneToMany(mappedBy = "book")
    private List<FeedBack> feedBacks;
    @ManyToOne()
    @JoinColumn(name = "owner_id")
    private User owner ;
     @OneToMany(mappedBy = "book")
    private List<BookTransactionHistory> bookTransactionHistories;
}
