import {Component} from '@angular/core';
import {RegisterRequestDto} from "../../services/models/register-request-dto";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/services/authentication.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  registerRequest: RegisterRequestDto = {firstName: "", lastName: "", email: "", password: ""};
  errMsg: Array<string> = [];

  constructor(private router: Router, private authService: AuthenticationService) {
  }

  login() {
    this.router.navigate(["login"]);
  }

  register() {
    this.authService.register({
      body: this.registerRequest
    }).subscribe({
      next: () => {
        this.router.navigate(["activation-account"]);
      },
      error: (err) => {
        this.errMsg = err.error.validationErrors;
      }
    })
  }
}
