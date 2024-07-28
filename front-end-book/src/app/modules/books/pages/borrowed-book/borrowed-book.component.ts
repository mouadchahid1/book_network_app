import {Component, OnInit} from '@angular/core';
import {BookService} from "../../../../services/services/book.service";
import {PageResponseBorrowedBookResponse} from "../../../../services/models/page-response-borrowed-book-response";
import {BorrowedBookResponse} from "../../../../services/models/borrowed-book-response";
import {FeedBackRequest} from "../../../../services/models/feed-back-request";
import {FeedbackService} from "../../../../services/services/feedback.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-borrowed-book',
  templateUrl: './borrowed-book.component.html',
  styleUrl: './borrowed-book.component.scss'
})
export class BorrowedBookComponent implements OnInit {
  page: number = 0;
  size: number = 6;
  borrowedBooks: PageResponseBorrowedBookResponse = {};
  selectedBorrowBook: BorrowedBookResponse | undefined = undefined;
  feedBackRequest: FeedBackRequest = {bookId: 0, comment: "", note: 0};

  ngOnInit(): void {
    this.loadBorrowedBook();
  }

  constructor(private bookService: BookService, private feedBackService: FeedbackService,
              private router: Router) {
  }

  loadBorrowedBook() {
    this.bookService.findAllBorrowedBooks({
      page: this.page,
      size: this.size
    }).subscribe({
      next: (borrowedBooksResponse) => {
        this.borrowedBooks = borrowedBooksResponse;
      }
    });
  }

  returnBorrowBook(borrowBook: BorrowedBookResponse) {
    this.selectedBorrowBook = borrowBook;
    this.feedBackRequest.bookId = borrowBook.id as number;

  }

  handleReturnBorrowBook(withFeedBack: boolean) {

    this.bookService.returnBorrowBook({
      "book_id": this.selectedBorrowBook?.id as number
    }).subscribe({
      next: () => {
        if (withFeedBack) {
          this.saveFeedBack();
          this.selectedBorrowBook = undefined;
          this.loadBorrowedBook();
        }
       // il faut load pour que les elements soit a jour ;
      },
      error: (err) => {
        console.log(err.error);
      }
    })
  }


  // il faut toujours travail avec acceped que les fonctions ne soient pas dans la meme line
  saveFeedBack() {
    this.feedBackService.saveFeedBack({
      body: this.feedBackRequest
    }).subscribe({
      next: () => {
        this.router.navigate(["books", "my-borrowed-books"]);
        alert("Feed Back added successfully");
      },
      error: (err) => {
        console.log(err.error);
      }
    })
  }


  // la partie de pagination
  get isLastPage(): boolean {
    return this.page === this.borrowedBooks.totalPage as number - 1;
  }

  goToFirstPage() {
    this.page = 0;
    this.loadBorrowedBook();

  }

  goToPrevious() {
    this.page--;
    this.loadBorrowedBook();
  }

  goToPage(pageIndex: number) {
    this.page = pageIndex;
    this.loadBorrowedBook();
  }

  goToNextPage() {
    this.page++;
    this.loadBorrowedBook();
  }

  goToLastPage() {
    this.page = this.borrowedBooks.totalPage as number - 1;
    this.loadBorrowedBook();
  }
}
