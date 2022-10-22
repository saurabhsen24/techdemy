import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { CourseRequest } from 'src/app/models/requests/CourseRequest.model';
import { CourseResponse } from 'src/app/models/responses/CourseResponse.model';
import { ErrorResponse } from 'src/app/models/responses/ErrorResponse.model';
import { GenericResponse } from 'src/app/models/responses/GenericResponse.model';
import { CourseService } from 'src/app/services/course.service';
import { MessageService } from 'src/app/services/message.service';
import { SharedService } from 'src/app/services/shared.service';

declare var $: any;

@Component({
  selector: 'app-add-course',
  templateUrl: './add-course.component.html',
  styleUrls: ['./add-course.component.css'],
})
export class AddCourseComponent implements OnInit {
  courseRequest: CourseRequest = {
    courseName: '',
    category: '',
    courseDescription: '',
    coursePrice: 0.0,
    file: null,
  };

  constructor(
    private courseService: CourseService,
    private sharedService: SharedService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {}

  showCreateCourseModal() {
    $('#addCourseModal').modal('show');
  }

  uploadFile(event: any) {
    const file = <File>event.target.files[0];
    if (!file) {
      this.messageService.showMessage('error', 'Course Image is required');
      return;
    }
    this.courseRequest.file = file;
  }

  onSubmit(f: NgForm) {
    this.courseService.saveCourse(f.value, this.courseRequest.file!!).subscribe(
      (response: CourseResponse) => {
        this.sharedService.courseResponseSubject.next(response);
        f.reset();
        this.messageService.showToastMessage(
          'success',
          'Course successfully saved'
        );
        $('#addCourseModal').modal('hide');
      },
      (err: ErrorResponse) => {
        $('#addCourseModal').modal('hide');
        this.messageService.showErrorMessage(err);
      }
    );
  }
}
