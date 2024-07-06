import {Injectable} from '@angular/core';
import {JwtHelperService} from "@auth0/angular-jwt";

@Injectable({
  providedIn: 'root'
})
export class TokenService {
  constructor() {
  }


  get token() {
    return localStorage.getItem("token") as string;
  }

  set token(token: string) {
    localStorage.setItem("token", token);
  }

  isTokenNotValid() {
    return !this.tokenIsValid();
  }

  private tokenIsValid() {
    const token = this.token;
    if (!token) {
      return false;
    }
    // decode the token using npm i @auth0/angular-jwt
    const jwtHelper = new JwtHelperService();
    // check if the token are expired
    const isExpired = jwtHelper.isTokenExpired(token);
    if (isExpired) {
      localStorage.removeItem("token");
      return false;
    }
    return true;
  }
}

