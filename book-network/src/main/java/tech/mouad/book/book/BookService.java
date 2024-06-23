package tech.mouad.book.book;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import tech.mouad.book.common.PageResponse;
import tech.mouad.book.user.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public Integer saveBook(BookRequest bookRequest, Authentication currentUser) {
        User user = (User) currentUser.getPrincipal();
        Book book = bookMapper.toBook(bookRequest);
        book.setOwner(user);
        book.setArchived(false);
        return bookRepository.save(book).getId();
    }

    public BookResponse findBookById(Integer id) {
        return bookRepository.findById(id).map(bookMapper::toBookResponse).orElseThrow(
                () -> new EntityNotFoundException("Book not found with the id " + id)
        );

    }

    public PageResponse<BookResponse> findAllBooks(short page, byte size, Authentication currentUser) {
        User user = (User) currentUser.getPrincipal();
        // il faut faire attention c est Pageable de domain
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAllDisplayableBooks(pageable, user.getId());
        List<BookResponse> bookResponses = books.map(bookMapper::toBookResponse).toList();
        return new PageResponse<BookResponse>(bookResponses,
                (short) books.getNumber(),
                (short) books.getSize(),
                books.getTotalElements(),
                (short) books.getTotalPages(), books.isFirst(), books.isLast()
        );

    }

    public PageResponse<BookResponse> findAllBooksOwner(short page, byte size, Authentication currentUser) {
        User user = (User) currentUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAll(BookSpecification.withOwnerId(user.getId()), pageable);
        List<BookResponse> bookResponses = books.map(bookMapper::toBookResponse).toList();
        return new PageResponse<BookResponse>(bookResponses,
                (short) books.getNumber(),
                (short) books.getSize(),
                books.getTotalElements(),
                (short) books.getTotalPages(), books.isFirst(), books.isLast()
        );
    }
}
