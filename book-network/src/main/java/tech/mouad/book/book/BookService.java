package tech.mouad.book.book;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tech.mouad.book.bookTransactionHistory.BookTransactionHistory;
import tech.mouad.book.bookTransactionHistory.BookTransactionHistoryRepository;
import tech.mouad.book.common.PageResponse;
import tech.mouad.book.file.StorageFileService;
import tech.mouad.book.user.User;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookTransactionHistoryRepository historyRepository;
    private final StorageFileService storageFileService;
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

    public Book findById(Integer bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("The book doesn't exists with Id :" + bookId));

    }

    public PageResponse<BookResponse> findAllBooks(short page, short size, Authentication currentUser) {
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

    public PageResponse<BookResponse> findAllBooksOwner(short page, short size, Authentication currentUser) {
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

    public PageResponse<BorrowedBookResponse> findAllBorrowedBooks(short page, short size, Authentication currentUser) {
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

    public PageResponse<BorrowedBookResponse> findAllReturnedBooks(short page, short size, Authentication currentUser) {
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
        Book book = findById(bookId);
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
        Book book = findById(bookId);
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
        Book book = findById(bookId);
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

    public Integer returnBorrowBook(Integer bookId, Authentication currentUser) {
        // 1 check if the book exists
        // 2 check if the book is not archived or no published
        // 3 check if the borrowed book is not the book of the currentUser
        // 4 check in the history that this user is already borrowed this book
        // 5 update
        Book book = findById(bookId);
        User user = (User) currentUser.getPrincipal();
        if (book.isArchived() || !book.isShareable()) {
            throw new OperationNotPermittedException("YOU can not do this action the book is archived or is not shareable");
        }
        if (Objects.equals(book.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("The owner can not borrow his book ");
        }
        BookTransactionHistory bookTransactionHistory = historyRepository.findByBookIdAndUserId(bookId, user.getId())
                .orElseThrow(() -> new OperationNotPermittedException("The book is not borrowed by this user"));
        bookTransactionHistory.setReturned(true);
        return historyRepository.save(bookTransactionHistory).getId();

    }

    public Integer returnApprovedBorrowBook(Integer bookId, Authentication currentUser) {
        Book book = findById(bookId);
        User user = (User) currentUser.getPrincipal();
        if (book.isArchived() || !book.isShareable()) {
            throw new OperationNotPermittedException("YOU can not do this action the book is archived or is not shareable");
        }
        if (!Objects.equals(book.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("The owner can not borrow his book ");
        }
        BookTransactionHistory bookTransactionHistory = historyRepository.findByBookIdAndUserIdAndReturn(bookId, user.getId())
                .orElseThrow(() -> new OperationNotPermittedException("The book is not borrowed by this user"));
        bookTransactionHistory.setReturnApproved(true);
        return historyRepository.save(bookTransactionHistory).getId();

    }

    public void uploadCoverBook(MultipartFile file, Integer bookId, Authentication currentUser) {
        Book book = findById(bookId);
        User user =(User) currentUser.getPrincipal();
        var coverPath = storageFileService.saveFile(file,user.getId());
        book.setBookCover(coverPath);
        bookRepository.save(book);
    }
}
