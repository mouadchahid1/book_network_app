import {Component, OnInit} from '@angular/core';
import {KeycloakService} from "../../services/keycloak/keycloak.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit {
  // errMsg: Array<String> = [];
  // authRequest: AuthenticateRequestDto = {email: "", password: ""};

  constructor(private keycloak: KeycloakService) {
  }

  async ngOnInit(): Promise<void> {
    await this.keycloak.init();
    await this.keycloak.login();
  }

  // login() {
  //   this.errMsg = [];// il faut vide la tableau
  //   this.authService.authenticate({
  //     body: this.authRequest
  //   }).subscribe({
  //     next: (res) => {
  //       this.tokenService.token = res.token as string;
  //       this.router.navigate(["books"]);
  //     },
  //     error: (err) => {
  //       if (err.error.validationErrors) {
  //         this.errMsg = err.error.validationErrors;
  //       } else {
  //         this.errMsg.push(err.error.error);
  //       }
  //       console.log(err);
  //     }
  //   })
  // }
  //
  // register() {
  //   this.router.navigate(["register"]);
  // }
}
