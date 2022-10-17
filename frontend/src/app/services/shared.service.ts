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
}
