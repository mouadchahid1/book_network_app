import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/services/authentication.service";

@Component({
  selector: 'app-activation-account',
  templateUrl: './activation-account.component.html',
  styleUrl: './activation-account.component.scss'
})
export class ActivationAccountComponent {
  message!: string;
  submitted = false;
  isOky = true;

  constructor(private router: Router, private authService: AuthenticationService) {
  }

  goToLogin() {
    this.router.navigate(["login"]);
  }

  onCodeCompleted(token: string) {
    this.confirmActivation(token);
  }

  private confirmActivation(token: string) {
   this.authService.activateAccount({
      token
   }).subscribe({
     next :() => {
          this.message = "Token activated successfully ! click on the button to go to login" ;
          this.isOky = true;
          this.submitted = true ;
     },
     error : () => {
           this.message = "Token is not valid or expired Please try again";
           this.isOky = false ;
           this.submitted = true ;
     }
     // todo continue the video 8:00:00
   })
  }
}
