import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { faUserCircle } from '@fortawesome/free-solid-svg-icons';
import { ErrorResponse } from 'src/app/models/responses/ErrorResponse.model';
import { GenericResponse } from 'src/app/models/responses/GenericResponse.model';
import { AuthService } from 'src/app/services/auth.service';
import { MessageService } from 'src/app/services/message.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
})
export class SignupComponent implements OnInit {
  faUser = faUserCircle;
  errorMessage: string = '';
  signupForm!: FormGroup;
  successResponse!: GenericResponse;
  isLoading = false;

  constructor(
    private authService: AuthService,
    private messageServide: MessageService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.signupForm = new FormGroup({
      userName: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', Validators.required),
    });
  }

  onSubmit() {
    const Toast = this.messageServide.getToast();

    if (this.signupForm.invalid) {
      Toast.fire({
        icon: 'error',
        iconColor: 'white',
        text: 'Please provide valid data',
        background: '#f27474',
        color: 'white',
      });
      return;
    }

    this.isLoading = true;

    this.authService.signup(this.signupForm.value).subscribe(
      (response: GenericResponse) => {
        console.log(response);
        this.successResponse = response;
        this.router.navigateByUrl('/login');
        this.signupForm.reset();
      },
      (err: ErrorResponse) => {
        Toast.fire({
          icon: 'error',
          iconColor: 'white',
          text: `${err.message}`,
          background: '#f27474',
          color: 'white',
        });
        this.signupForm.reset();
        this.isLoading = false;
      },
      () => {
        this.isLoading = false;
        Toast.fire({
          icon: 'success',
          iconColor: 'white',
          text: `${this.successResponse.message}`,
          background: '#a5dc86',
          color: 'white',
        });
      }
    );
  }
}
