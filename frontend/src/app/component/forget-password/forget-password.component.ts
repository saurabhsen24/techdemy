import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { faUserCircle } from '@fortawesome/free-solid-svg-icons';
import { ErrorResponse } from 'src/app/models/responses/ErrorResponse.model';
import { GenericResponse } from 'src/app/models/responses/GenericResponse.model';
import { AuthService } from 'src/app/services/auth.service';
import { MessageService } from 'src/app/services/message.service';

@Component({
  selector: 'app-forget-password',
  templateUrl: './forget-password.component.html',
  styleUrls: ['./forget-password.component.css'],
})
export class ForgetPasswordComponent implements OnInit {
  faUser = faUserCircle;
  errorMessage: string = '';
  forgetPasswordForm!: FormGroup;
  isLoading = false;

  constructor(
    private authService: AuthService,
    private messageService: MessageService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.forgetPasswordForm = new FormGroup({
      email: new FormControl('', [Validators.email, Validators.required]),
    });
  }

  onSubmit() {
    const Toast = this.messageService.getToast();
    if (this.forgetPasswordForm.invalid) {
      Toast.fire({
        icon: 'error',
        iconColor: 'white',
        text: 'Please enter email',
        background: '#f27474',
        color: 'white',
      });

      return;
    }

    this.isLoading = true;

    this.authService.forgetPassword(this.forgetPasswordForm.value).subscribe(
      (response: GenericResponse) => {
        console.debug(response);
        this.messageService.showMessage('success', response.message);
        this.forgetPasswordForm.reset();
        this.router.navigateByUrl('/resetPassword');
      },
      (err: ErrorResponse) => {
        console.debug(err);
        this.messageService.showMessage('error', err.message);
        this.forgetPasswordForm.reset();
        this.isLoading = false;
      },
      () => {
        this.isLoading = false;
      }
    );
  }
}
