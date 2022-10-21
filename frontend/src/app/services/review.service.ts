import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, tap, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ReviewRequest } from '../models/requests/ReviewRequest.model';
import { GenericResponse } from '../models/responses/GenericResponse.model';
import { ReviewResponse } from '../models/responses/ReviewResponse.model';

@Injectable({
  providedIn: 'root',
})
export class ReviewService {
  private baseApi = environment.API_URL;
  private reviewApi = this.baseApi + '/reviews';

  constructor(private http: HttpClient) {}

  getAllReviews(courseId: Number) {
    return this.http
      .get<ReviewResponse[]>(`${this.reviewApi}/${courseId}`)
      .pipe(
        tap((response) => console.debug(response)),
        catchError((errResponse) => throwError(errResponse.error))
      );
  }

  postReview(reviewRequest: ReviewRequest, courseId: Number) {
    return this.http
      .post<GenericResponse>(`${this.reviewApi}/${courseId}`, reviewRequest)
      .pipe(
        tap((response) => console.debug(response)),
        catchError((errResponse) => throwError(errResponse.error))
      );
  }

  updateReview(courseId: Number, reviewRequest: ReviewRequest) {
    return this.http
      .put<GenericResponse>(`${this.reviewApi}/${courseId}`, reviewRequest)
      .pipe(
        tap((response) => console.debug(response)),
        catchError((errResponse) => throwError(errResponse.error))
      );
  }

  deleteReview(courseId: Number) {
    return this.http
      .delete<GenericResponse>(`${this.reviewApi}/${courseId}`)
      .pipe(
        tap((response) => console.debug(response)),
        catchError((errResponse) => throwError(errResponse.error))
      );
  }
}
