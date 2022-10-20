import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { faStar } from '@fortawesome/free-solid-svg-icons';
import { CourseResponse } from 'src/app/models/responses/CourseResponse.model';
import { CourseService } from 'src/app/services/course.service';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css'],
})
export class CourseComponent implements OnInit {
  faStarIcon = faStar;
  course: CourseResponse | null = null;
  stars = 5;

  constructor(
    private courseService: CourseService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      let courseId = params.get('courseId');
      if (courseId) {
        this.courseService
          .getCourse(+courseId)
          .subscribe((data: CourseResponse) => {
            this.course = data;
          });
      }
    });
  }
}
