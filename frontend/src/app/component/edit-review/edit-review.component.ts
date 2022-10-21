import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ReviewRequest } from 'src/app/models/requests/ReviewRequest.model';
import { ErrorResponse } from 'src/app/models/responses/ErrorResponse.model';
import { GenericResponse } from 'src/app/models/responses/GenericResponse.model';
import { MessageService } from 'src/app/services/message.service';
import { ReviewService } from 'src/app/services/review.service';
import { StarRatingColor } from '../star-rating/star-rating.component';

declare var $: any;

@Component({
  selector: 'app-edit-review',
  templateUrl: './edit-review.component.html',
  styleUrls: ['./edit-review.component.css'],
})
export class EditReviewComponent implements OnInit {
  @Input()
  rating: number = 0;
  starCount: number = 5;
  starColor: StarRatingColor = StarRatingColor.warn;

  courseId: Number | undefined;

  @Input('reviewRequest')
  reviewRequest = {
    reviewId: 0,
    comment: '',
    rating: 0,
  };

  @Output() updateEvent = new EventEmitter<any>();

  constructor(
    private reviewService: ReviewService,
    private messageService: MessageService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      this.courseId = +params.get('courseId')!!;
    });
  }

  onRatingChanged(rating: number) {
    this.rating = rating;
    this.reviewRequest.rating = rating;
  }

  updateReview(f: NgForm) {
    console.log(f.value);
    this.reviewService
      .updateReview(this.courseId!!, this.reviewRequest)
      .subscribe(
        (response: GenericResponse) => {
          this.messageService.showToastMessage('success', response.message);
          this.updateEvent.emit(this.reviewRequest);
          $('#reviewModel').modal('hide');
        },
        (err: ErrorResponse) => {
          console.debug(err);
          $('#reviewModel').modal('hide');
        }
      );
  }

  showReviewModel() {
    $('#reviewModel').modal('show');
  }
}
