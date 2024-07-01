package tech.mouad.book.feedback;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import tech.mouad.book.book.Book;
import tech.mouad.book.book.BookService;
import tech.mouad.book.book.OperationNotPermittedException;
import tech.mouad.book.common.PageResponse;
import tech.mouad.book.user.User;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FeedBackService {
    private final BookService bookService;
    private final FeedBackMapper feedBackMapper;
    private final FeedBackRepository feedBackRepository;

    public Integer saveFeedBack(Authentication currentUser, FeedBackRequest feedBackRequest) {
        Book book = bookService.findById(feedBackRequest.bookId());
        User user = (User) currentUser.getPrincipal();
        if (book.isArchived() || !book.isShareable()) {
            throw new EntityNotFoundException("The book is archived or is not shareable");
        }
        if (Objects.equals(book.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("The user can't create feedback on here own book");
        }
        FeedBack feedBack = feedBackMapper.toFeedBack(feedBackRequest);
        return feedBackRepository.save(feedBack).getId();

    }


    public PageResponse<FeedBackResponse> findFeedBackByBookId(Integer bookId, Authentication currentUser, int page, int size) {
        Book book = bookService.findById(bookId);
        User user = (User) currentUser.getPrincipal();
        if (book.isArchived() || !book.isShareable()) {
            throw new EntityNotFoundException("The book is archived or is not shareable");
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<FeedBack> feedBacks = feedBackRepository.findByBookId(bookId, pageable);
        // transforme en feedbackResponse
        List<FeedBackResponse> feedBackResponses = feedBacks
                .map(feedback -> feedBackMapper.toFeedBackResponse(feedback, user.getId())).toList();

        return new PageResponse<FeedBackResponse>(
                feedBackResponses,
                (short) feedBacks.getNumber(),
                (short) feedBacks.getSize(),
                feedBacks.getTotalElements(),
                (short) feedBacks.getTotalPages(),
                feedBacks.isFirst(),
                feedBacks.isLast()
        );
    }
}
