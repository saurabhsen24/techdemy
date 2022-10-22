import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgForm } from '@angular/forms';
import { CourseResponse } from 'src/app/models/responses/CourseResponse.model';
import { ErrorResponse } from 'src/app/models/responses/ErrorResponse.model';
import { GenericResponse } from 'src/app/models/responses/GenericResponse.model';
import { CourseService } from 'src/app/services/course.service';
import { MessageService } from 'src/app/services/message.service';

declare var $: any;

@Component({
  selector: 'app-edit-course',
  templateUrl: './edit-course.component.html',
  styleUrls: ['./edit-course.component.css'],
})
export class EditCourseComponent implements OnInit {
  @Input()
  courseResponse: CourseResponse | undefined;

  isLoading = true;

  @Output() editCourseEvent = new EventEmitter<CourseResponse>();

  courseRequest = {
    courseName: '',
    category: '',
    courseDescription: '',
    coursePrice: 0.0,
  };

  constructor(
    private courseService: CourseService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {}

  updateCourse(f: NgForm) {
    if (f.invalid) return;
    this.courseService
      .updateCourse(this.courseResponse!!.courseId, f.value)
      .subscribe(
        (response: GenericResponse) => {
          this.isLoading = false;
          this.messageService.showToastMessage('success', response.message);
          let editCourseResponse = Object.assign({}, this.courseResponse);
          this.editCourseEvent.emit(editCourseResponse);
          f.reset();
          $('#editCourseModal').modal('hide');
        },
        (err: ErrorResponse) => {
          this.isLoading = false;
          this.messageService.showErrorMessage(err);
          $('#editCourseModal').modal('hide');
        }
      );
  }

  showEditCourseModal() {
    $('#editCourseModal').modal('show');
  }
}
