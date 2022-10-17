import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, tap, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CourseRequest } from '../models/requests/CourseRequest.model';
import { Category } from '../models/responses/Category.model';
import { CourseResponse } from '../models/responses/CourseResponse.model';
import { GenericResponse } from '../models/responses/GenericResponse.model';

@Injectable({
  providedIn: 'root',
})
export class CourseService {
  private baseUrl = environment.API_URL;
  private courseApi = this.baseUrl + '/course';

  constructor(private http: HttpClient) {}

  saveCourse(courseRequest: CourseRequest) {
    return this.http
      .post<GenericResponse>(`${this.courseApi}/saveCourse`, courseRequest)
      .pipe(
        tap((res) => console.log(res)),
        catchError((errResponse) => throwError(errResponse.error))
      );
  }

  getCourse(courseId: Number) {
    return this.http.get<CourseResponse>(`${this.courseApi}/${courseId}`).pipe(
      tap((response) => console.debug(response)),
      catchError((errResponse) => throwError(errResponse.error))
    );
  }

  updateCourse(courseId: Number, courseRequest: CourseRequest) {
    return this.http
      .put<GenericResponse>(`${this.courseApi}/${courseId}`, courseRequest)
      .pipe(
        tap((res) => console.log(res)),
        catchError((errResponse) => throwError(errResponse.error))
      );
  }

  deleteCourse(courseId: Number) {
    return this.http
      .delete<GenericResponse>(`${this.courseApi}/${courseId}`)
      .pipe(
        tap((response) => console.debug(response)),
        catchError((errResponse) => throwError(errResponse.error))
      );
  }

  getAllCategories() {
    return this.http.get<Category[]>(`${this.courseApi}/categories`).pipe(
      tap((response) => console.debug(response)),
      catchError((errResponse) => throwError(errResponse.error))
    );
  }

  getCoursesByCategory(category: string) {
    return this.http
      .get<CourseResponse[]>(`${this.courseApi}/categories/${category}`)
      .pipe(
        tap((response) => console.debug(response)),
        catchError((errResponse) => throwError(errResponse.error))
      );
  }

  getAllCourses() {
    return this.http.get<CourseResponse[]>(`${this.courseApi}/all`).pipe(
      tap((response) => console.debug(response)),
      catchError((errResponse) => throwError(errResponse.error))
    );
  }
}
