import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import {
  faEdit,
  faTrashAlt,
  faUser,
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

  reviews: ReviewResponse[] = [];

  reviewRequest: ReviewRequest = {
    comment: '',
    rating: 0,
  };

  loggedInUser: string | null = null;

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
          console.log(data);
        });
    });

    this.loggedInUser = this.tokenStorage.getUser()!!.userName;
  }

  onRatingChanged(rating: number) {
    this.rating = rating;
    this.reviewRequest.rating = rating;
  }

  onSubmit(reviewForm: NgForm) {
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
}
