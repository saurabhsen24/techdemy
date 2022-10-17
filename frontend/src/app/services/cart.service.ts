import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, tap, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { GenericResponse } from '../models/responses/GenericResponse.model';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  private baseApi = environment.API_URL;
  private cartApi = this.baseApi + '/cart';

  constructor(private http: HttpClient) {}

  saveToCart(courseId: Number) {
    return this.http
      .post<GenericResponse>(`${this.cartApi}/${courseId}`, {})
      .pipe(
        tap((response) => console.log(response)),
        catchError((errResponse) => throwError(errResponse.error))
      );
  }

  deleteFromCart(courseId: Number) {
    return this.http
      .delete<GenericResponse>(`${this.cartApi}/${courseId}`)
      .pipe(
        tap((response) => console.log(response)),
        catchError((errResponse) => throwError(errResponse.error))
      );
  }

  getAllCarts() {
    return this.http.get(`${this.cartApi}/all`).pipe(
      tap((response) => console.log(response)),
      catchError((errResponse) => throwError(errResponse.error))
    );
  }
}
