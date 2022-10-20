import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { faStar } from '@fortawesome/free-solid-svg-icons';
import { CourseResponse } from 'src/app/models/responses/CourseResponse.model';
import { CourseService } from 'src/app/services/course.service';
import { ReviewService } from 'src/app/services/review.service';
import { SharedService } from 'src/app/services/shared.service';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css'],
})
export class CourseComponent implements OnInit {
  faStarIcon = faStar;
  course: CourseResponse | null = null;
  numberOfStars: Array<Number> = new Array();

  constructor(
    private courseService: CourseService,
    private sharedService: SharedService,
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
            this.numberOfStars = this.sharedService.numberOfSequence(
              data.courseRating
            );

            console.log(data.courseRating);
          });
      }
    });
  }
}
