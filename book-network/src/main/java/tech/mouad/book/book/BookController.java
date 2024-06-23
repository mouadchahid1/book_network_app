package tech.mouad.book.book;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
@Tag(name = "Book", description = "Book Controller implementation")
public class BookController {
     private final BookService bookService ;
    @PostMapping("")
    public ResponseEntity<Integer> saveBook(
            @RequestBody @Valid BookRequest bookRequest ,
            Authentication currentUser
                     ) {
        return ResponseEntity.ok(bookService.saveBook(bookRequest,currentUser));
    }
}
