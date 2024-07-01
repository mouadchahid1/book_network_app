package tech.mouad.book.book;

import jakarta.persistence.*;
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
    @Column()
    private String title;

    @Column()
    private String authorName;

    @Column()
    private String isbn;

    @Column()
    private String synopsis;

    @Column()
    private String bookCover;
    private boolean archived;
    private boolean shareable;

    @OneToMany(mappedBy = "book")
    private List<FeedBack> feedBacks;


    @ManyToOne()
    @JoinColumn(name = "owner_id")
    private User owner;


    @OneToMany(mappedBy = "book")
    private List<BookTransactionHistory> bookTransactionHistories;

    public double getRate() {
        // always check if is null or empty ;
        if (feedBacks == null || feedBacks.isEmpty()) {
            return 0.0;
        }
        double average = feedBacks.stream().mapToDouble(FeedBack::getNote)
                .average().orElse(0.0);
        return Math.round((average * 10.0) / 10.0);
    }
}
