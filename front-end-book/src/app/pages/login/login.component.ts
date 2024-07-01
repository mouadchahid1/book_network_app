import {Component} from '@angular/core';
import {AuthenticateRequestDto} from "../../services/models/authenticate-request-dto";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/services/authentication.service";
import {TokenService} from "../../token/token.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  errMsg: Array<String> = [];
  authRequest: AuthenticateRequestDto = {email: "", password: ""};

  constructor(private router: Router,
              private authService: AuthenticationService,
              private tokenService: TokenService) {
  }

  login() {
    this.errMsg = [];// il faut vide la tableau
    this.authService.authenticate({
      body: this.authRequest
    }).subscribe({
      next: (res) => {
        this.tokenService.token = res.token as string;
        this.router.navigate(["books"]);
      },
      error: (err) => {
        if (err.error.validationErrors) {
          this.errMsg = err.error.validationErrors;
        } else {
          this.errMsg.push(err.error.error);
        }
        console.log(err);
      }
    })
  }

  register() {
    this.router.navigate(["register"]);
  }
}
