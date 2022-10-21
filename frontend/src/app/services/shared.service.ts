import { NumberFormatStyle } from '@angular/common';
import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { Constants } from '../constants/Constants';

@Injectable({
  providedIn: 'root',
})
export class SharedService {
  public cartCountSubscription = new Subject<number>();

  constructor() {}

  storeCartCount(count: Number) {
    localStorage.setItem(Constants.CART_COUNT, count.toString());
  }

  getCartCount() {
    let cartCount = localStorage.getItem(Constants.CART_COUNT);
    return cartCount == null ? 0 : +cartCount;
  }

  storeInCart(cart: string) {
    let carts: string[] = this.getCarts();
    if (!carts) {
      carts = [];
    }

    carts.push(cart);
    localStorage.setItem(Constants.CARTS, JSON.stringify(carts));
  }

  removeFromCart(cart: string) {
    let carts = this.getCarts();
    let newCarts = carts?.filter((c) => c != cart);
    if (!newCarts) {
      newCarts = [];
    }
    localStorage.setItem(Constants.CARTS, JSON.stringify(newCarts));
  }

  deleteCart() {
    localStorage.removeItem(Constants.CARTS);
    localStorage.removeItem(Constants.CART_COUNT);
  }

  getCarts(): [] {
    const carts = localStorage.getItem(Constants.CARTS);
    return carts ? JSON.parse(carts) : [];
  }

  storeEnrolledCourses(enrolledCourses: Number[]) {
    if (!enrolledCourses || enrolledCourses.length == 0) {
      enrolledCourses = [];
    }
    localStorage.setItem(
      Constants.ENROLLED_COURSES,
      JSON.stringify(enrolledCourses)
    );
  }

  getEnrolledCourses(): string[] {
    const courses = localStorage.getItem(Constants.ENROLLED_COURSES);
    return courses ? JSON.parse(courses) : [];
  }

  convertStrinArrayToNumberArray(stringArray: string[]) {
    const numberArray = stringArray.map((str) => Number(str));
    return numberArray;
  }

  numberOfSequence(n: Number): Array<Number> {
    return Array(n);
  }
}
