import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, tap, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { GenericResponse } from '../models/responses/GenericResponse.model';
import { PaymentResponse } from '../models/responses/PaymentResponse.model';

@Injectable({
  providedIn: 'root',
})
export class PurchaseService {
  private baseApi = environment.API_URL;
  private purchaseApi = this.baseApi + '/purchase';

  constructor(private http: HttpClient) {}

  purchaseCourse(totalAmount: Number): Observable<PaymentResponse> {
    return this.http
      .post<PaymentResponse>(`${this.purchaseApi}`, {
        totalAmount: totalAmount,
      })
      .pipe(
        tap((response) => console.debug(response)),
        catchError((errResponse) => throwError(errResponse.error))
      );
  }

  completePayment(purchaseRequest: PaymentRequest) {
    return this.http
      .put<GenericResponse>(`${this.purchaseApi}`, purchaseRequest)
      .pipe(
        tap((response) => console.debug(response)),
        catchError((errResponse) => throwError(errResponse.error))
      );
  }
}
