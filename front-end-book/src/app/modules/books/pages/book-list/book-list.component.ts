import {Component, OnInit} from '@angular/core';
import {BookService} from "../../../../services/services/book.service";
import {PageResponseBookResponse} from "../../../../services/models/page-response-book-response";

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrl: './book-list.component.scss'
})
export class BookListComponent implements OnInit {
  booksList: PageResponseBookResponse = {};
  page: number = 0;
  size: number = 5;

  constructor(private bookService: BookService) {
  }

  ngOnInit(): void {
    this.loadBooks();
  }

  loadBooks() {
    this.bookService.findAllBooks({
      page: this.page, size: this.size
    }).subscribe({
      next: (data) => {
        this.booksList = data;
      },
      error: (err) => {
        console.error(err);
      }
    })
  }

  get isLastPage() : boolean {
    return this.page === this.booksList.totalPage as number -1;
  }
  goToFirstPage() {
      this.page = 0 ;
      this.loadBooks();

  }

  goToPrevious() {
     this.page-- ;
     this.loadBooks();
  }

  goToPage(pageIndex: number) {
   this.page = pageIndex ;
   this.loadBooks() ;
  }

  goToNextPage() {
    this.page++;
    this.loadBooks() ;
  }

  goToLastPage() {
     this.page = this.booksList.totalPage as number - 1 ;
     this.loadBooks();
  }
}
