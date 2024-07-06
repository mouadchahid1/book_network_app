import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MainComponent} from "./pages/main/main.component";
import {BookListComponent} from "./pages/book-list/book-list.component";
import {MyBooksComponent} from "./pages/my-books/my-books.component";
import {BookManageComponent} from "./pages/book-manage/book-manage.component";
import {BorrowedBookComponent} from "./pages/borrowed-book/borrowed-book.component";
import {ReturnedBookComponent} from "./pages/returned-book/returned-book.component";
import {authGuard} from "../../services/guards/auth.guard";

const routes: Routes = [
  {
    path: "",
    component: MainComponent,
    canActivate: [authGuard],
    children: [
      {path: "", component: BookListComponent, canActivate: [authGuard]}, // c est le chemin : /books
      {path: "my-books", component: MyBooksComponent, canActivate: [authGuard]},
      {path: "my-borrowed-books", component: BorrowedBookComponent, canActivate: [authGuard]},
      {path: "my-returned-books", component: ReturnedBookComponent, canActivate: [authGuard]},
      {path: "manage", component: BookManageComponent, canActivate: [authGuard]},
      {path: "manage/:id", component: BookManageComponent, canActivate: [authGuard]}
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BooksRoutingModule {
}
