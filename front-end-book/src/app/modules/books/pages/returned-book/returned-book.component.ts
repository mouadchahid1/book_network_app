import {Component, OnInit} from '@angular/core';
import {PageResponseBorrowedBookResponse} from "../../../../services/models/page-response-borrowed-book-response";
import {BookService} from "../../../../services/services/book.service";
import {BorrowedBookResponse} from "../../../../services/models/borrowed-book-response";

@Component({
  selector: 'app-returned-book',
  templateUrl: './returned-book.component.html',
  styleUrl: './returned-book.component.scss'
})
export class ReturnedBookComponent implements OnInit {
  page: number = 0;
  size: number = 6;
  returnedBooks: PageResponseBorrowedBookResponse = {};
  level!: string;
  message: string = "";
  approved : boolean = false ;

  ngOnInit(): void {
    this.loadReturnedBook();
  }

  constructor(private bookService: BookService,
  ) {
  }

  loadReturnedBook() {
    this.bookService.findAllReturnedBooks({
      page: this.page,
      size: this.size
    }).subscribe({
      next: (borrowedBooksResponse) => {
        this.returnedBooks = borrowedBooksResponse;
      }
    });
  }


  // la partie de pagination
  get isLastPage(): boolean {
    return this.page === this.returnedBooks.totalPage as number - 1;
  }

  goToFirstPage() {
    this.page = 0;
    this.loadReturnedBook();

  }

  goToPrevious() {
    this.page--;
    this.loadReturnedBook();
  }

  goToPage(pageIndex: number) {
    this.page = pageIndex;
    this.loadReturnedBook();
  }

  goToNextPage() {
    this.page++;
    this.loadReturnedBook();
  }

  goToLastPage() {
    this.page = this.returnedBooks.totalPage as number - 1;
    this.loadReturnedBook();
  }


  approvedReturnBook(returnedBook: BorrowedBookResponse) {
    // yes should make a test frist to check if the returnedBook exist
    if (!returnedBook) {
      // il faut toujeours fire ce test pour que l'application sois rapide
      return;
    }
    this.bookService.returnApprovedBorrowBook({
      "book_id": returnedBook.id as number
    }).subscribe({
      next: () => {
        this.level = "success";
        this.message = "Returned Approved successfully";
        this.approved = true ;
      },
      error: (err) => {
        this.level = "error";
        this.message = "The book is not returned yet";
      }
    })
  }
}
