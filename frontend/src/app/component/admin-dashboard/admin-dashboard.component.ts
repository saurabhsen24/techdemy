import { Component, OnInit } from '@angular/core';
import {
  faEdit,
  faStar,
  faTag,
  faTrashAlt,
} from '@fortawesome/free-solid-svg-icons';
import { CourseResponse } from 'src/app/models/responses/CourseResponse.model';
import { CourseService } from 'src/app/services/course.service';

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

  constructor(private courseService: CourseService) {}

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
  }

  getArray(n: number): Array<number> {
    return Array(n);
  }
}
