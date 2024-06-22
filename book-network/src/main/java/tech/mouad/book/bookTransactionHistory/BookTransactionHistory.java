package tech.mouad.book.bookTransactionHistory;

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
import tech.mouad.book.user.User;

@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookTransactionHistory extends BaseEntity {
    private boolean returned;
    private boolean returnApproved;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
