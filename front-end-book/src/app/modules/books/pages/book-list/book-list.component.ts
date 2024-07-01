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
}
