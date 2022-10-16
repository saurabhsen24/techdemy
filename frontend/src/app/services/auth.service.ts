import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { ForgetPasswordRequest } from '../models/requests/ForgetPasswordRequest.model';
import { LoginRequest } from '../models/requests/LoginRequest.model';
import { ResetPasswordRequest } from '../models/requests/ResetPasswordRequest.model';
import { SignupRequest } from '../models/requests/SignupRequest.model';
import { GenericResponse } from '../models/responses/GenericResponse.model';
import { LoginResponse } from '../models/responses/LoginResponse.model';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private baseUrl = environment.API_URL;
  private authApi = this.baseUrl + '/auth';

  constructor(private http: HttpClient) {}

  login(loginRequest: LoginRequest): Observable<LoginResponse> {
    return this.http
      .post<LoginResponse>(`${this.authApi}/login`, loginRequest)
      .pipe(
        tap((response) => console.log(response)),
        catchError((errResponse) => throwError(errResponse.error))
      );
  }

  signup(signupRequest: SignupRequest): Observable<GenericResponse> {
    return this.http
      .post<GenericResponse>(`${this.authApi}/signup`, signupRequest)
      .pipe(
        tap((response) => console.log(response)),
        catchError((errResponse) => throwError(errResponse.error))
      );
  }

  forgetPassword(
    forgetPasswordRequest: ForgetPasswordRequest
  ): Observable<GenericResponse> {
    return this.http
      .post<GenericResponse>(
        `${this.authApi}/forgetPassword`,
        forgetPasswordRequest
      )
      .pipe(
        tap((response) => console.log(response)),
        catchError((errResponse) => throwError(errResponse.error))
      );
  }

  resetPassword(
    resetPasswordRequest: ResetPasswordRequest
  ): Observable<GenericResponse> {
    return this.http
      .put<GenericResponse>(
        `${this.authApi}/resetPassword`,
        resetPasswordRequest
      )
      .pipe(
        tap((response) => console.log(response)),
        catchError((errResponse) => throwError(errResponse.error))
      );
  }
}
