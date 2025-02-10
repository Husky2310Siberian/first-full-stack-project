import { Component } from '@angular/core';
import {AuthenticationRequest} from "../../services/models/authentication-request";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/services/authentication.service";
import {AuthenticationResponse} from "../../services/models/authentication-response";
import {TokenService} from "../../services/token/token.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  authRequest: AuthenticationRequest = {email: '' , password: ''};
  errorMessage: Array<string> = [];

  constructor(
    private router: Router,
    private authService: AuthenticationService,
    private tokenService: TokenService

  ) {
  }

  login(): void {
    this.errorMessage = [];
    this.authService.authentication({
       body: this.authRequest
    }).subscribe({
      next: (res: AuthenticationResponse): void => {
        this.tokenService.token = res.token as string
        this.router.navigate(['books'])
      },
      error: (err): void => {
        console.log(err);
        if (err.error.validationErrors) {
          this.errorMessage = err.error.validationErros
          } else {
            this.errorMessage.push(err.error.errorMessage)
          }
        }
    })
  }

  register() {
    this.router.navigate(['register'])
  }
}
