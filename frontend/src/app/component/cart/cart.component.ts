import { Component, OnInit } from '@angular/core';
import { faTag, faTrashAlt } from '@fortawesome/free-solid-svg-icons';
import { Cart } from 'src/app/models/Cart.model';
import { ErrorResponse } from 'src/app/models/responses/ErrorResponse.model';
import { GenericResponse } from 'src/app/models/responses/GenericResponse.model';
import { CartService } from 'src/app/services/cart.service';
import { MessageService } from 'src/app/services/message.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
})
export class CartComponent implements OnInit {
  carts: Cart[] = [];

  faDeleteIcon = faTrashAlt;
  faTagIcon = faTag;
  totalPrice = 0;

  constructor(
    private cartService: CartService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.cartService.getAllCarts().subscribe(
      (carts: Cart[]) => {
        this.carts = carts;
        this.getTotalPrice(this.carts);
      },
      (errResponse: ErrorResponse) => {
        this.messageService.showErrorMessage(errResponse);
      }
    );
  }

  removeFromCart(courseId: Number) {
    const newCarts = this.carts.filter((cart) => cart.courseId !== courseId);
    this.carts = newCarts;
    this.getTotalPrice(newCarts);
    this.cartService
      .deleteFromCart(courseId)
      .subscribe((response: GenericResponse) => {
        this.messageService.showToastMessage('warning', response.message);
      });
  }

  getTotalPrice(carts: Cart[]) {
    let totalPrice = 0;
    carts.forEach((cart) => {
      totalPrice = totalPrice + Number(cart.coursePrice);
    });

    this.totalPrice = totalPrice;
  }
}
