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
    @Column(columnDefinition = "nvarchar")
    private String title;

    @Column(columnDefinition = "nvarchar")
    private String authorName;

    @Column(columnDefinition = "nvarchar")
    private String isbn;

    @Column(columnDefinition = "nvarchar")
    private String synopsis;

    @Column(columnDefinition = "nvarchar")
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
