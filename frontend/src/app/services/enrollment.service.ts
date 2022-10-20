import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, tap, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CourseResponse } from '../models/responses/CourseResponse.model';

@Injectable({
  providedIn: 'root',
})
export class EnrollmentService {
  private baseApi = environment.API_URL;
  private enrollApi = this.baseApi + '/enrolled/courses';
  constructor(private http: HttpClient) {}

  getAllCourses() {
    return this.http.get<CourseResponse[]>(`${this.enrollApi}`).pipe(
      tap((response) => console.debug(response)),
      catchError((errResponse) => throwError(errResponse.error))
    );
  }

  addEnrolledCourses(courseIds: []) {
    let courses = courseIds.map((courseId) => Number(courseId));
    let enrollmentReq = { courseIds: courses };
    console.log(enrollmentReq);
    return this.http.post(`${this.enrollApi}`, enrollmentReq).pipe(
      tap((response) => console.debug(response)),
      catchError((errResponse) => throwError(errResponse.error))
    );
  }
}
