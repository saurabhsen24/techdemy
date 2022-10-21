import { Component, OnInit } from '@angular/core';
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

  constructor(
    private enrollmentService: EnrollmentService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.enrollmentService.getAllCourses().subscribe(
      (data: CourseResponse[]) => {
        this.enrolledCourses = data;
        console.log(data);
      },
      (errorResponse: ErrorResponse) => {
        this.messageService.showErrorMessage(errorResponse);
      }
    );
  }
}
