import {
  Component,
  EventEmitter,
  OnInit,
  Output,
  ViewChild,
} from '@angular/core';
import {
  faEdit,
  faStar,
  faTag,
  faTrashAlt,
} from '@fortawesome/free-solid-svg-icons';
import { CourseResponse } from 'src/app/models/responses/CourseResponse.model';
import { ErrorResponse } from 'src/app/models/responses/ErrorResponse.model';
import { GenericResponse } from 'src/app/models/responses/GenericResponse.model';
import { CourseService } from 'src/app/services/course.service';
import { MessageService } from 'src/app/services/message.service';
import { SharedService } from 'src/app/services/shared.service';
import Swal from 'sweetalert2';
import { EditCourseComponent } from '../edit-course/edit-course.component';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css'],
})
export class AdminDashboardComponent implements OnInit {
  courses: CourseResponse[] = [];
  faDeleteIcon = faTrashAlt;
  faUpdateIcon = faEdit;
  faStarIcon = faStar;
  faTagIcon = faTag;
  isLoading = true;

  @Output()
  public courseEvent = new EventEmitter<CourseResponse>();

  courseResponse: CourseResponse | undefined;

  @ViewChild(EditCourseComponent) editCourseComponent:
    | EditCourseComponent
    | undefined;

  constructor(
    private courseService: CourseService,
    private sharedService: SharedService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.courseService.getAllCourses().subscribe(
      (courses: CourseResponse[]) => {
        this.courses = courses;
        this.isLoading = false;
      },
      (err) => {
        this.isLoading = false;
      }
    );

    this.sharedService.courseResponseSubject.subscribe(
      (data: CourseResponse) => {
        this.courses.push(data);
      }
    );
  }

  deleteCourse(courseId: number) {
    const newCourses = this.courses.filter(
      (course) => course.courseId !== courseId
    );
    Swal.fire({
      title: 'Are you sure?',
      text: "You won't be able to revert this!",
      icon: 'question',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete it!',
      background: '#000',
      color: '#fff',
    }).then((result) => {
      if (result.isConfirmed) {
        this.courses = newCourses;
        this.courseService.deleteCourse(courseId).subscribe(
          (response: GenericResponse) => {
            this.messageService.showToastMessage('success', response.message);
          },
          (err: ErrorResponse) => {
            this.messageService.showErrorMessage(err);
          }
        );
      }
    });
  }

  editCourse(course: CourseResponse) {
    this.courseResponse = Object.assign({}, course);
    this.courseEvent.emit(this.courseResponse);
    this.editCourseComponent?.showEditCourseModal();
  }

  onEditCourseEvent(editCourseResponse: CourseResponse) {
    const index = this.courses.findIndex(
      (c) => c.courseId === editCourseResponse.courseId
    );
    this.courses[index] = editCourseResponse;
  }

  getArray(n: number): Array<number> {
    return Array(Math.round(n));
  }
}
