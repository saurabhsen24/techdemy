import { Component, HostListener, OnInit } from '@angular/core';
import { faTag, faTrashAlt } from '@fortawesome/free-solid-svg-icons';
import { Cart } from 'src/app/models/Cart.model';
import { ErrorResponse } from 'src/app/models/responses/ErrorResponse.model';
import { GenericResponse } from 'src/app/models/responses/GenericResponse.model';
import { PaymentResponse } from 'src/app/models/responses/PaymentResponse.model';
import { CartService } from 'src/app/services/cart.service';
import { MessageService } from 'src/app/services/message.service';
import { PurchaseService } from 'src/app/services/purchase.service';
import { SharedService } from 'src/app/services/shared.service';

declare var Razorpay: any;

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
    private messageService: MessageService,
    private purchaseService: PurchaseService,
    private sharedService: SharedService
  ) {}

  ngOnInit(): void {
    this.cartService.getAllCarts().subscribe(
      (carts: Cart[]) => {
        this.carts = carts;
        this.sharedService.storeCartCount(carts.length);
        this.sharedService.cartCountSubscription.next(carts.length);
        this.getTotalPrice(this.carts);
      },
      (errResponse: ErrorResponse) => {
        this.messageService.showToastMessage('error', errResponse.message);
      }
    );
  }

  removeFromCart(courseId: Number) {
    const newCarts = this.carts.filter((cart) => cart.courseId !== courseId);
    this.carts = newCarts;
    this.getTotalPrice(newCarts);

    this.sharedService.removeFromCart(courseId.toString());
    this.sharedService.storeCartCount(newCarts.length);
    this.sharedService.cartCountSubscription.next(newCarts.length);
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

  puchase(totalPrice: number) {
    this.purchaseService.purchaseCourse(totalPrice).subscribe(
      (response: PaymentResponse) => {
        let totalAmt = (totalPrice * 100).toString();
        let options = this.getOptions(totalAmt, response);
        var razorPay = new Razorpay(options);
        razorPay.open();
      },
      (errResponse: ErrorResponse) => {
        this.messageService.showToastMessage('error', errResponse.message);
      }
    );
  }

  getOptions(totalAmt: string, payment: PaymentResponse) {
    var options = {
      key: payment.secretKey, // Enter the Key ID generated from the Dashboard
      amount: totalAmt, // Amount is in currency subunits. Default currency is INR. Hence, 50000 refers to 50000 paise
      currency: 'INR',
      name: 'TechDemy',
      description: 'Test Transaction',
      image: 'https://example.com/your_logo',
      order_id: payment.orderId, //This is a sample Order ID. Pass the `id` obtained in the response of Step 1
      handler: (response: PaymentRequest) => {
        console.log(response);
        this.purchaseService
          .completePayment(response)
          .subscribe((res: GenericResponse) => {
            this.messageService.showToastMessage('success', res.message);
          });
      },
      prefill: {
        name: 'Saurabh Sen',
        email: 'saurbhsen@gmail.com',
        contact: '8151849266',
      },
      notes: {
        address: 'Razorpay Corporate Office',
      },
      theme: {
        color: '#3399cc',
      },
    };

    console.log(options);
    return options;
  }
}
