package tech.mouad.book.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {
    @Query("""
             select book from Book book where book.archived = false
             and
             book.shareable=true
             and
             book.owner != :userId
            """)
    Page<Book> findAllDisplayableBooks(Pageable pageable, Integer userId);

     @Query("""
              select (count(*)>0) as IsBorrow
              from BookTransactionHistory history
              where history.user.id =:userId
              and history.book.id=:bookId
              and history.returnApproved=false
             """)
    boolean isBookBorrow(Integer bookId, Integer userId);
}
