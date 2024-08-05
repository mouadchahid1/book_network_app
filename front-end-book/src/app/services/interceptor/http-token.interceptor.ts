import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {catchError, Observable} from 'rxjs';
import {TokenService} from '../../token/token.service';
import {KeycloakService} from "../keycloak/keycloak.service";

@Injectable()
export class HttpTokenInterceptor implements HttpInterceptor {

  constructor(private keycloakService: KeycloakService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.keycloakService.keycloak.token;
    if (req.url.includes("auth")) {
      return next.handle(req);
    }

    const newReq = req.clone({
      headers: req.headers.set('Authorization', `Bearer ${token}`)
    });
    return next.handle(newReq).pipe(
      catchError(error => {
        console.log(error);
        throw error;
      })
    );

  }
}
