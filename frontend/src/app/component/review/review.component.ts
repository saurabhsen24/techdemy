import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { StarRatingColor } from '../star-rating/star-rating.component';

@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.css'],
})
export class ReviewComponent implements OnInit {
  rating: number = 0;
  starCount: number = 5;
  starColor: StarRatingColor = StarRatingColor.warn;

  constructor() {}

  ngOnInit(): void {}

  onRatingChanged(rating: number) {
    this.rating = rating;
  }
}
