package tech.mouad.book.book;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import tech.mouad.book.bookTransactionHistory.BookTransactionHistory;
import tech.mouad.book.bookTransactionHistory.BookTransactionHistoryRepository;
import tech.mouad.book.common.PageResponse;
import tech.mouad.book.user.User;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookTransactionHistoryRepository historyRepository;

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

    public PageResponse<BorrowedBookResponse> findAllBorrowedBooks(short page, byte size, Authentication currentUser) {
        User user = (User) currentUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<BookTransactionHistory> bookTransactionHistories = historyRepository.findAllBorrowedBooks(pageable, user.getId());
        List<BorrowedBookResponse> bookResponses = bookTransactionHistories.map(bookMapper::toBorrowedBookResponse).stream().toList();
        return new PageResponse<>(
                bookResponses,
                (short) bookTransactionHistories.getNumber(),
                (short) bookTransactionHistories.getSize(),
                bookTransactionHistories.getTotalElements(),
                (short) bookTransactionHistories.getTotalPages(),
                bookTransactionHistories.isFirst(),
                bookTransactionHistories.isLast()
        );
    }

    public PageResponse<BorrowedBookResponse> findAllReturnedBooks(short page, byte size, Authentication currentUser) {
        User user = (User) currentUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<BookTransactionHistory> bookTransactionHistories = historyRepository.findAllReturnedBooks(pageable, user.getId());
        List<BorrowedBookResponse> bookResponses = bookTransactionHistories.map(bookMapper::toBorrowedBookResponse).stream().toList();
        return new PageResponse<>(
                bookResponses,
                (short) bookTransactionHistories.getNumber(),
                (short) bookTransactionHistories.getSize(),
                bookTransactionHistories.getTotalElements(),
                (short) bookTransactionHistories.getTotalPages(),
                bookTransactionHistories.isFirst(),
                bookTransactionHistories.isLast()
        );
    }

    public Integer updateSharableBook(Integer bookId, Authentication currentUser) {
        User user = (User) currentUser.getPrincipal();
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("The book doesn't exits with id" + bookId));
        if (!Objects.equals(book.getOwner().getId(), user.getId())) {
            // apres chaque ajouter d'une nouvelle exception il faut lui ajouter dans les exception handle
            throw new OperationNotPermittedException("Only The owner can do this action");
        }
        book.setShareable(!book.isShareable());
        bookRepository.save(book);
        return bookId;

    }

    public Integer updateArchivedStatus(Integer bookId, Authentication currentUser) {
        User user = (User) currentUser.getPrincipal();
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("The book doesn't exits with id" + bookId));
        if (!Objects.equals(book.getOwner().getId(), user.getId())) {
            // apres chaque ajouter d'une nouvelle exception il faut lui ajouter dans les exception handle
            throw new OperationNotPermittedException("Only The owner can do this action");
        }
        book.setArchived(!book.isArchived());
        bookRepository.save(book);
        return bookId;

    }

//    public boolean checkIfTheBookIsArchiveOrSharable(Integer bookId) {
//        Book book = bookRepository.findById(bookId)
//                .orElseThrow(() -> new EntityNotFoundException("The book doesn't exists with Id :" + bookId));
//        return !book.isArchived() || book.isShareable();
//    }

    public Integer borrowBook(Integer bookId, Authentication currentUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("The book doesn't exists with Id :" + bookId));
        User user = (User) currentUser.getPrincipal();
        // il faut que le book ne sois pas archive et ne sois pas
        if (book.isArchived() || !book.isShareable()) {
            throw new OperationNotPermittedException("The book is archived or Not sharable");
        }
        if (Objects.equals(book.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("The user can't borrow Her book");
        }
        final boolean isBookAlreadyBorrow = bookRepository.isBookBorrow(bookId, user.getId());
        if (isBookAlreadyBorrow) {
            throw new OperationNotPermittedException("The book is already borrow");
        }
        return historyRepository.save(BookTransactionHistory.builder()
                .book(book)
                .user(user)
                .returnApproved(false)
                .returned(false)
                .build()).getId();

    }
}
