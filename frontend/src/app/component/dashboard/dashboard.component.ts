import { Component, OnInit } from '@angular/core';
import {
  faCartShopping,
  faUserGraduate,
} from '@fortawesome/free-solid-svg-icons';
import { CourseResponse } from 'src/app/models/responses/CourseResponse.model';
import { ErrorResponse } from 'src/app/models/responses/ErrorResponse.model';
import { GenericResponse } from 'src/app/models/responses/GenericResponse.model';
import { CartService } from 'src/app/services/cart.service';
import { CourseService } from 'src/app/services/course.service';
import { MessageService } from 'src/app/services/message.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
  courses: CourseResponse[] = [];
  faUserGraduate = faUserGraduate;
  faCartIcon = faCartShopping;

  constructor(
    private courseService: CourseService,
    private cartService: CartService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.courseService
      .getAllCourses()
      .subscribe((courses: CourseResponse[]) => {
        this.courses = courses;
      });
  }

  addToCart(courseId: Number) {
    this.cartService.saveToCart(courseId).subscribe(
      (response: GenericResponse) => {
        this.messageService.showToastMessage('success', response.message);
      },
      (errorResponse: ErrorResponse) => {
        this.messageService.showErrorMessage(errorResponse);
      }
    );
  }
}
