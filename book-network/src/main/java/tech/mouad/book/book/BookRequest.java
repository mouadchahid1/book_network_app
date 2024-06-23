package tech.mouad.book.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record BookRequest(
        Integer id,
        @NotBlank(message = "100")
        @NotNull(message = "100")
        @NotEmpty(message = "100")
        String title,
        @NotBlank(message = "101")
        @NotNull(message = "101")
        @NotEmpty(message = "101")
        String authorName,
        @NotBlank(message = "102")
        @NotNull(message = "102")
        @NotEmpty(message = "102")
        String isbn,
        @NotBlank(message = "103")
        @NotNull(message = "103")
        @NotEmpty(message = "103")
        String synopsis,

        boolean shareable
) {
}
