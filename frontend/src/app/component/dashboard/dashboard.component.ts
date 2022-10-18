import { Component, OnInit } from '@angular/core';
import {
  faCartShopping,
  faUserGraduate,
} from '@fortawesome/free-solid-svg-icons';
import { Cart } from 'src/app/models/Cart.model';
import { CourseResponse } from 'src/app/models/responses/CourseResponse.model';
import { ErrorResponse } from 'src/app/models/responses/ErrorResponse.model';
import { GenericResponse } from 'src/app/models/responses/GenericResponse.model';
import { CartService } from 'src/app/services/cart.service';
import { CourseService } from 'src/app/services/course.service';
import { MessageService } from 'src/app/services/message.service';
import { SharedService } from 'src/app/services/shared.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
  courses: CourseResponse[] = [];
  faUserGraduate = faUserGraduate;
  faCartIcon = faCartShopping;
  carts: Cart[] | null = null;

  constructor(
    private courseService: CourseService,
    private cartService: CartService,
    private messageService: MessageService,
    private sharedService: SharedService
  ) {}

  ngOnInit(): void {
    this.courseService
      .getAllCourses()
      .subscribe((courseData: CourseResponse[]) => {
        courseData.map((course) => {
          course.addedToCart = this.findCourseInCart(
            course.courseId.toString()
          );
        });

        console.log(courseData);
        this.courses = courseData;
      });
  }

  addToCart(courseId: Number) {
    this.cartService.saveToCart(courseId).subscribe(
      (response: GenericResponse) => {
        let cartCount = this.sharedService.getCartCount();
        this.sharedService.storeCartCount(cartCount + 1);
        this.sharedService.storeInCart(courseId.toString());

        this.courses
          .filter((course) => course.courseId === courseId)
          .map((course) => (course.addedToCart = true));
        this.sharedService.cartCountSubscription.next(cartCount + 1);
        this.messageService.showToastMessage('success', response.message);
      },
      (errorResponse: ErrorResponse) => {
        this.messageService.showErrorMessage(errorResponse);
      }
    );
  }

  private findCourseInCart(courseId: string): boolean {
    const carts = this.sharedService.getCarts();
    if (carts == null) return false;
    return carts.find((cId) => cId === courseId) != null;
  }
}
