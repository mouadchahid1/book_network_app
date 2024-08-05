import {APP_INITIALIZER, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from "@angular/common/http";
import {LoginComponent} from './pages/login/login.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {RegisterComponent} from './pages/register/register.component';
import {ActivationAccountComponent} from './pages/activation-account/activation-account.component';
import {CodeInputModule} from "angular-code-input";
import {HttpTokenInterceptor} from "./services/interceptor/http-token.interceptor";
import {ApiModule} from "./services/api.module";
import {KeycloakService} from "./services/keycloak/keycloak.service";


export function  kcFactory (kcService:KeycloakService) {
  return ()=> {
    kcService.init();
  }
}

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    ActivationAccountComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    ReactiveFormsModule,
    CodeInputModule,
    // here we should add the address ip of our vps or the domain name
    ApiModule.forRoot({rootUrl:"http://localhost:8089/api/v1"})
  ],
  providers: [HttpClient,
    {provide: HTTP_INTERCEPTORS, useClass: HttpTokenInterceptor, multi: true},
    {provide:APP_INITIALIZER,deps:[KeycloakService],useFactory:kcFactory,multi:true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
