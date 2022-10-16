import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { faUserCircle } from '@fortawesome/free-solid-svg-icons';
import { ErrorResponse } from 'src/app/models/responses/ErrorResponse.model';
import { LoginResponse } from 'src/app/models/responses/LoginResponse.model';
import { AuthService } from 'src/app/services/auth.service';
import { MessageService } from 'src/app/services/message.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  faUser = faUserCircle;
  errorMessage: string = '';
  loginForm!: FormGroup;
  isLoading = false;
  isAdmin = false;

  constructor(
    private authService: AuthService,
    private messageService: MessageService,
    private tokenStorage: TokenStorageService,
    private router: Router
  ) {}

  ngOnInit() {
    this.loginForm = new FormGroup({
      userName: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
    });
  }

  onSubmit() {
    const Toast = this.messageService.getToast();

    if (this.loginForm.invalid) {
      Toast.fire({
        icon: 'error',
        iconColor: 'white',
        text: 'Invalid Username or Password',
        background: '#f27474',
        color: 'white',
      });
      return;
    }

    this.authService.login(this.loginForm.value).subscribe(
      (loginResponse: LoginResponse) => {
        this.tokenStorage.saveUser(loginResponse);
        this.tokenStorage.userNameListner.next(loginResponse.userName);
        this.isAdmin = this.tokenStorage.checkAdmin();
        this.tokenStorage.adminRoleListener.next(this.isAdmin);
        this.router.navigateByUrl('/');
        this.loginForm.reset();
      },
      (err: ErrorResponse) => {
        Toast.fire({
          icon: 'error',
          iconColor: 'white',
          text: `${err.message}`,
          background: '#f27474',
          color: 'white',
        });
        this.isLoading = false;
      },
      () => {
        this.isLoading = false;
        Toast.fire({
          icon: 'success',
          iconColor: 'white',
          text: 'Login Successfully',
          background: '#a5dc86',
          color: 'white',
        });
      }
    );
  }
}
