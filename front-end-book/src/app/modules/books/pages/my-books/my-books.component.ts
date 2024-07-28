import {Component, OnInit} from '@angular/core';
import {PageResponseBookResponse} from "../../../../services/models/page-response-book-response";
import {BookService} from "../../../../services/services/book.service";
import {BookResponse} from "../../../../services/models/book-response";
import {Router} from "@angular/router";

@Component({
  selector: 'app-my-books',
  templateUrl: './my-books.component.html',
  styleUrl: './my-books.component.scss'
})
export class MyBooksComponent implements OnInit {
  booksList: PageResponseBookResponse = {};
  page: number = 0;
  size: number = 5;



  constructor(private bookService: BookService,private router : Router) {
  }

  ngOnInit(): void {
    this.loadBooks();
  }

  loadBooks() {
    this.bookService.getAllBooksOwner({
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

  get isLastPage(): boolean {
    return this.page === this.booksList.totalPage as number - 1;
  }

  goToFirstPage() {
    this.page = 0;
    this.loadBooks();

  }

  goToPrevious() {
    this.page--;
    this.loadBooks();
  }

  goToPage(pageIndex: number) {
    this.page = pageIndex;
    this.loadBooks();
  }

  goToNextPage() {
    this.page++;
    this.loadBooks();
  }

  goToLastPage() {
    this.page = this.booksList.totalPage as number - 1;
    this.loadBooks();
  }

  onEdit(book: BookResponse) {
    this.router.navigate(["books", "manage", book.id]);
  }

  onShare(book: BookResponse) {
      this.bookService.updateSharableBook({
        "book_id":book.id as number
      }).subscribe({
        next : () => {
// il est modifier a la base de donne mes il faut lui modifier ici aussi
          book.shareable = !book.shareable;
        }
      })
  }

  onArchive(book: BookResponse) {
      this.bookService.updateArchivedStatus({
        "book_id" : book.id as number
      }).subscribe({
        next :() =>  {
          book.archived = !book.archived;
        }
      })
  }
}
