package tech.mouad.book.book;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tech.mouad.book.common.PageResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
@Tag(name = "Book", description = "Book Controller implementation")
public class BookController {

    private final BookService bookService;

    @PostMapping("")
    public ResponseEntity<Integer> saveBook(
            @RequestBody @Valid BookRequest bookRequest,
            Authentication currentUser
    ) {
        return ResponseEntity.ok(bookService.saveBook(bookRequest, currentUser));
    }

    @GetMapping("/{book_id}")
    public ResponseEntity<BookResponse> findBookById(@PathVariable(name = "book_id") Integer id) {
        return ResponseEntity.ok(bookService.findBookById(id));
    }

    @GetMapping("")
    public ResponseEntity<PageResponse<BookResponse>> findAllBooks(
            // j'ai passe le currentUser pour ne pas affiche avec les books global les books de person authenticate
            @RequestParam(name = "page", defaultValue = "0", required = false) short page,
            @RequestParam(name = "size", defaultValue = "10", required = false) byte size,
            Authentication currentUser
    ) {
        return ResponseEntity.ok(bookService.findAllBooks(page, size, currentUser));
    }

    @GetMapping("/owner")
    public ResponseEntity<PageResponse<BookResponse>> getAllBooksOwner(
            @RequestParam(name = "page", defaultValue = "0", required = false) short page,
            @RequestParam(name = "size", defaultValue = "10", required = false) byte size,
            Authentication currentUser
    ) {
        return ResponseEntity.ok(bookService.findAllBooksOwner(page, size, currentUser));
    }

    @GetMapping("/borrowed")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllBorrowedBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) short page,
            @RequestParam(name = "size", defaultValue = "10", required = false) byte size,
            Authentication currentUser) {

        return ResponseEntity.ok(bookService.findAllBorrowedBooks(page, size, currentUser));
    }

    @GetMapping("/returned")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllReturnedBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) short page,
            @RequestParam(name = "size", defaultValue = "10", required = false) byte size,
            Authentication currentUser
    ) {
        return ResponseEntity.ok(bookService.findAllReturnedBooks(page, size, currentUser));
    }

    @PatchMapping("/sharable/{book_id}")
    public ResponseEntity<Integer>  updateSharableBook(
            @PathVariable(name = "book_id") Integer bookId,
            Authentication currentUser
    ) {
      return ResponseEntity.ok(bookService.updateSharableBook(bookId,currentUser));
    }

    @PatchMapping("/archived/{book_id}")
    public ResponseEntity<Integer> updateArchivedStatus(
            @PathVariable(name = "book_id") Integer book_id ,
            Authentication currentUser
    ) {
    return ResponseEntity.ok(bookService.updateArchivedStatus(book_id,currentUser));
    }

    @PostMapping("/borrowBook/{book_id}")
    public ResponseEntity<Integer> borrowBook(
            @PathVariable(name = "book_id") Integer bookId,
            Authentication currentUser
    ) {
      return ResponseEntity.ok(bookService.borrowBook(bookId,currentUser));
    }
}

