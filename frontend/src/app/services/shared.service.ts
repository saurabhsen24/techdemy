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
    window.sessionStorage.setItem(Constants.CART_COUNT, count.toString());
  }

  getCartCount() {
    let cartCount = window.sessionStorage.getItem(Constants.CART_COUNT);
    return cartCount == null ? 0 : +cartCount;
  }

  storeInCart(cart: string) {
    let carts = this.getCarts();
    if (!carts) {
      carts = [];
    }

    carts.push(cart);
    window.sessionStorage.setItem(Constants.CARTS, JSON.stringify(carts));
  }

  removeFromCart(cart: string) {
    let carts = this.getCarts();
    let newCarts = carts?.filter((c) => c != cart);
    if (!newCarts) {
      newCarts = [];
    }
    window.sessionStorage.setItem(Constants.CARTS, JSON.stringify(newCarts));
  }

  getCarts(): string[] | null {
    const carts = window.sessionStorage.getItem(Constants.CARTS);
    return carts ? JSON.parse(carts) : null;
  }
}
