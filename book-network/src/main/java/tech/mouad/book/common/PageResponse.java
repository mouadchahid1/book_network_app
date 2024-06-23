package tech.mouad.book.common;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResponse<T> {
    private List<T> content;
    private short pageNumber;
    private short size ;
    private long totalElement;
    private short totalPage;
    private boolean isFirst;
    private boolean isLast;
}
