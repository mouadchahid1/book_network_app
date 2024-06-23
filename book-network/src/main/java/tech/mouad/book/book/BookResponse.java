package tech.mouad.book.book;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookResponse {
    private Integer id;
    private String title;
    private String authorName;
    private String isbn;
    private String synopsis;
    private byte[] bookCover;
    private String owner ;
    private double rate ;
    private boolean archived;
    private boolean shareable;
}
