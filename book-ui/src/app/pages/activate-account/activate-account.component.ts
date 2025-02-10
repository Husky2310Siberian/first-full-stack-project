import { Component } from '@angular/core';
import {AuthenticationService} from "../../services/services/authentication.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-activate-account',
  templateUrl: './activate-account.component.html',
  styleUrls: ['./activate-account.component.scss']
})
export class ActivateAccountComponent {

  message: string = '';
  isAllWright: boolean = true;
  submitted: boolean = false;

  constructor(
    private router: Router,
    private authService: AuthenticationService
  ) {

  }

  onCodeCompleted(token: string) {
    this.confirmAccount(token)

  }

  redirectToLogin():void {
    this.router.navigate(['login'])
  }

  private confirmAccount(token: string) {
    this.authService.confirm({
          token
    }).subscribe({
      next: ():void => {
        this.message = " Your account has been successfully activated, please continue to login"
        this.submitted = true;
        this.isAllWright= true;
      },
      error:():void => {
        this.message = 'Token has been expired or is invalid'
        this.submitted = true;
        this.isAllWright = false;
      }
    });


  }
}
