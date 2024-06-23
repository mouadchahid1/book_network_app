package tech.mouad.book.book;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import tech.mouad.book.user.User;
@Service
@RequiredArgsConstructor
public class BookService {
    private final  BookRepository bookRepository ;
    private final BookMapper bookMapper ;
    public Integer saveBook(BookRequest bookRequest, Authentication currentUser) {
        User user = (User) currentUser.getPrincipal();
        Book book =  bookMapper.toBook(bookRequest);
        book.setOwner(user);
        book.setArchived(false);
        return bookRepository.save(book).getId();
    }
}
