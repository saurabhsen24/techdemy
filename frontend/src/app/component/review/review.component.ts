import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import {
  faEdit,
  faStar,
  faTrashAlt,
  faUserCircle,
} from '@fortawesome/free-solid-svg-icons';
import { ReviewRequest } from 'src/app/models/requests/ReviewRequest.model';
import { ErrorResponse } from 'src/app/models/responses/ErrorResponse.model';
import { GenericResponse } from 'src/app/models/responses/GenericResponse.model';
import { ReviewResponse } from 'src/app/models/responses/ReviewResponse.model';
import { MessageService } from 'src/app/services/message.service';
import { ReviewService } from 'src/app/services/review.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';
import Swal from 'sweetalert2';
import { EditReviewComponent } from '../edit-review/edit-review.component';
import { StarRatingColor } from '../star-rating/star-rating.component';

@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.css'],
})
export class ReviewComponent implements OnInit {
  rating: number = 0;
  starCount: number = 5;
  courseId: number;
  starColor: StarRatingColor = StarRatingColor.warn;

  faUserIcon = faUserCircle;
  faDeleteIcon = faTrashAlt;
  faUpdateIcon = faEdit;
  faStarIcon = faStar;

  numberOfStars: number = 0;

  reviews: ReviewResponse[] = [];

  reviewRequest: ReviewRequest = {
    comment: '',
    rating: 0,
  };

  @Input()
  editReviewRequest = {
    reviewId: 0,
    comment: '',
    rating: 0,
  };

  loggedInUser: string | null = null;

  @ViewChild(EditReviewComponent) editReviewComponent:
    | EditReviewComponent
    | undefined;

  constructor(
    private reviewService: ReviewService,
    private messageService: MessageService,
    private tokenStorage: TokenStorageService,
    private route: ActivatedRoute
  ) {
    this.courseId = -1;
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      this.courseId = +params.get('courseId')!!;
      this.reviewService
        .getAllReviews(this.courseId)
        .subscribe((data: ReviewResponse[]) => {
          this.reviews = data;
          console.debug(data);
        });
    });

    this.loggedInUser = this.tokenStorage.getUser()!!.userName;
  }

  onRatingChanged(rating: number) {
    this.rating = rating;
    this.reviewRequest.rating = rating;
  }

  onSubmit(reviewForm: NgForm) {
    this.rating = 0;
    const newReviews = this.reviews;
    newReviews.push({
      reviewId: -1,
      reviewComment: this.reviewRequest.comment,
      rating: this.reviewRequest.rating,
      userName: this.loggedInUser!!,
    });

    this.reviews = newReviews;
    this.reviewService.postReview(this.reviewRequest, this.courseId).subscribe(
      (response: GenericResponse) => {
        this.messageService.showToastMessage('success', response.message);
        reviewForm.reset();
      },
      (errResponse: ErrorResponse) => {
        Swal.fire({
          icon: 'error',
          text: 'You have already posted review',
          background: '#000',
          color: '#fff',
        });
      }
    );
  }

  deleteReview(reviewId: number) {
    const newReviews = this.reviews.filter((r) => r.reviewId !== reviewId);
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
        this.reviews = newReviews;
        this.reviewService.deleteReview(this.courseId).subscribe(
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

  editReview(reviewId: number) {
    const review: ReviewResponse[] = this.reviews.filter(
      (r) => r.reviewId === reviewId
    );
    this.editReviewRequest = {
      reviewId: review[0].reviewId,
      comment: review[0].reviewComment,
      rating: review[0].rating,
    };
    this.editReviewComponent?.showReviewModel();
  }

  updateReview(review: { reviewId: number; comment: string; rating: number }) {
    const index = this.reviews.findIndex((r) => r.reviewId === review.reviewId);

    let updatedReview = this.reviews[index];
    updatedReview.rating = review.rating;
    updatedReview.reviewComment = review.comment;
    this.reviews[index] = updatedReview;
  }

  getArrayOfNumber(n: number) {
    return new Array(n);
  }
}
