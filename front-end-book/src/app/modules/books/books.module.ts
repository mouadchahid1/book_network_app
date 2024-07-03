import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BooksRoutingModule } from './books-routing.module';
import { MainComponent } from './pages/main/main.component';
import { MenuComponent } from './components/menu/menu.component';
import { BookListComponent } from './pages/book-list/book-list.component';
import { BookCardComponent } from './components/book-card/book-card.component';
import { BookStarsComponent } from './components/book-stars/book-stars.component';


@NgModule({
  declarations: [
    MainComponent,
    MenuComponent,
    BookListComponent,
    BookCardComponent,
    BookStarsComponent
  ],
  imports: [
    CommonModule,
    BooksRoutingModule
  ]
})
export class BooksModule { }
