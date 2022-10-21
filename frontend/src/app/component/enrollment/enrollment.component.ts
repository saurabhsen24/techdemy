import { Component, OnInit } from '@angular/core';
import { faStar, faTag } from '@fortawesome/free-solid-svg-icons';
import { CourseResponse } from 'src/app/models/responses/CourseResponse.model';
import { ErrorResponse } from 'src/app/models/responses/ErrorResponse.model';
import { EnrollmentService } from 'src/app/services/enrollment.service';
import { MessageService } from 'src/app/services/message.service';

@Component({
  selector: 'app-enrollment',
  templateUrl: './enrollment.component.html',
  styleUrls: ['./enrollment.component.css'],
})
export class EnrollmentComponent implements OnInit {
  enrolledCourses: CourseResponse[] = [];
  isLoading = true;
  faTagIcon = faTag;
  faStarIcon = faStar;
  constructor(
    private enrollmentService: EnrollmentService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.enrollmentService.getAllCourses().subscribe(
      (data: CourseResponse[]) => {
        this.enrolledCourses = data;
        this.isLoading = false;
        console.debug(data);
      },
      (errorResponse: ErrorResponse) => {
        this.isLoading = false;
        this.messageService.showErrorMessage(errorResponse);
      }
    );
  }

  getArray(n: number): Array<number> {
    return Array(n);
  }
}
