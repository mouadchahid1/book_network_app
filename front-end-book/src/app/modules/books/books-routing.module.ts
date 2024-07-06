import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MainComponent} from "./pages/main/main.component";
import {BookListComponent} from "./pages/book-list/book-list.component";
import {MyBooksComponent} from "./pages/my-books/my-books.component";
import {BookManageComponent} from "./pages/book-manage/book-manage.component";
import {BorrowedBookComponent} from "./pages/borrowed-book/borrowed-book.component";
import {ReturnedBookComponent} from "./pages/returned-book/returned-book.component";

const routes: Routes = [
  {
    path: "",
    component: MainComponent,
    children: [
      {path: "", component: BookListComponent}, // c est le chemin : /books
      {path: "my-books", component: MyBooksComponent},
      {path: "my-borrowed-books", component: BorrowedBookComponent},
      {path: "my-returned-books", component: ReturnedBookComponent},
      {path: "manage", component: BookManageComponent},
      {path: "manage/:id", component: BookManageComponent}
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BooksRoutingModule {
}
