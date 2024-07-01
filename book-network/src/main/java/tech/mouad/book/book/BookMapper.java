package tech.mouad.book.book;

import org.springframework.stereotype.Service;
import tech.mouad.book.bookTransactionHistory.BookTransactionHistory;
import tech.mouad.book.file.FileUtils;

@Service

public class BookMapper {


    public Book toBook(BookRequest bookRequest) {
        return Book.builder()
                .id(bookRequest.id())
                .title(bookRequest.title())
                .authorName(bookRequest.authorName())
                .shareable(bookRequest.shareable())
                .isbn(bookRequest.isbn())
                .synopsis(bookRequest.synopsis())
                .build();
    }

    public BookResponse toBookResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .authorName(book.getAuthorName())
                .shareable(book.isShareable())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .synopsis(book.getSynopsis())
                .owner(book.getOwner().fullName())
                .rate(book.getRate())
                .archived(book.isArchived())
                .bookCover(FileUtils.readFileFromLocation(book.getBookCover()))
                .build();

    }


    public BorrowedBookResponse toBorrowedBookResponse(BookTransactionHistory bookTransactionHistory) {
        return BorrowedBookResponse.builder()
                .id(bookTransactionHistory.getBook().getId())
                .returned(bookTransactionHistory.isReturned())
                .returnApproved(bookTransactionHistory.isReturnApproved())
                .title(bookTransactionHistory.getBook().getTitle())
                .authorName(bookTransactionHistory.getBook().getAuthorName())
                .isbn(bookTransactionHistory.getBook().getIsbn())
                .rate(bookTransactionHistory.getBook().getRate())
                .build();
    }
}
