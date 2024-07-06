import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./pages/login/login.component";
import {RegisterComponent} from "./pages/register/register.component";
import {ActivationAccountComponent} from "./pages/activation-account/activation-account.component";
import {authGuard} from "./services/guards/auth.guard";

const routes: Routes = [
  {path: "login", component: LoginComponent},
  {path: "register", component: RegisterComponent},
  {path: "activation-account", component: ActivationAccountComponent},
  {
    path: "books", loadChildren: () => import('./modules/books/books.module').then(m => m.BooksModule),
    canActivate: [authGuard]
  },
];

// dans angular et dans js en global en mais => et no ->

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
