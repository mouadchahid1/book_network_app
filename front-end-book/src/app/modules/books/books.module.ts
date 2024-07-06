import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BooksRoutingModule } from './books-routing.module';
import { MainComponent } from './pages/main/main.component';
import { MenuComponent } from './components/menu/menu.component';
import { BookListComponent } from './pages/book-list/book-list.component';
import { BookCardComponent } from './components/book-card/book-card.component';
import { BookStarsComponent } from './components/book-stars/book-stars.component';
import { MyBooksComponent } from './pages/my-books/my-books.component';
import { BookManageComponent } from './pages/book-manage/book-manage.component';
import {FormsModule} from "@angular/forms";
import { BorrowedBookComponent } from './pages/borrowed-book/borrowed-book.component';
import { ReturnedBookComponent } from './pages/returned-book/returned-book.component';


@NgModule({
  declarations: [
    MainComponent,
    MenuComponent,
    BookListComponent,
    BookCardComponent,
    BookStarsComponent,
    MyBooksComponent,
    BookManageComponent,
    BorrowedBookComponent,
    ReturnedBookComponent
  ],
  imports: [
    CommonModule,
    BooksRoutingModule,
    FormsModule
  ]
})
export class BooksModule { }
