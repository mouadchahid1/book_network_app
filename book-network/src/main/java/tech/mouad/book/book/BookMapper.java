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
}
