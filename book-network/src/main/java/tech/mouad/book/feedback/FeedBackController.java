package tech.mouad.book.feedback;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tech.mouad.book.common.PageResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feedback")
@Tag(name = "Feedback", description = "The controller of the feedBack")
public class FeedBackController {
    private final FeedBackService feedBackService;
    @PostMapping("/")
    public ResponseEntity<Integer> saveFeedBack(
            @RequestBody @Valid FeedBackRequest feedBackRequest,
            Authentication currentUser
    ) {
        return ResponseEntity.ok(feedBackService.saveFeedBack(currentUser,feedBackRequest));
    }

    @GetMapping("/feedBack/{book_id}")
    public ResponseEntity<PageResponse<FeedBackResponse>> getFeedBackBook(
            @RequestParam(name = "page" , defaultValue = "0" , required = false) int page ,
            @RequestParam(name = "size",defaultValue = "10",required = false) int size ,
            @PathVariable("book_id") Integer bookId,
            Authentication currentUser
    ) {
          return ResponseEntity.ok(feedBackService.findFeedBackByBookId(bookId,currentUser,page,size));
    }
}
