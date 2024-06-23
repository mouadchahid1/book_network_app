package tech.mouad.book.book;

import org.springframework.stereotype.Service;

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
       return  BookResponse.builder()
                .id(book.getId())
                .authorName(book.getAuthorName())
                .shareable(book.isShareable())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .synopsis(book.getSynopsis())
                .owner(book.getOwner().fullName())
                .rate(book.getRate())
               .archived(book.isArchived())
                //todo implement .cover
                .build();

    }
}
