import { Component, OnInit } from '@angular/core';
import { faUserGraduate } from '@fortawesome/free-solid-svg-icons';
import { CourseResponse } from 'src/app/models/responses/CourseResponse.model';
import { CourseService } from 'src/app/services/course.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
  courses: CourseResponse[] = [];
  faUserGraduate = faUserGraduate;

  constructor(private courseService: CourseService) {}

  ngOnInit(): void {
    this.courseService
      .getAllCourses()
      .subscribe((courses: CourseResponse[]) => {
        this.courses = courses;
      });
  }
}
