import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { faTag, faTrashAlt } from '@fortawesome/free-solid-svg-icons';
import { Cart } from 'src/app/models/Cart.model';
import { ErrorResponse } from 'src/app/models/responses/ErrorResponse.model';
import { GenericResponse } from 'src/app/models/responses/GenericResponse.model';
import { PaymentResponse } from 'src/app/models/responses/PaymentResponse.model';
import { CartService } from 'src/app/services/cart.service';
import { EnrollmentService } from 'src/app/services/enrollment.service';
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
    private sharedService: SharedService,
    private enrollService: EnrollmentService,
    private router: Router
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
        razorPay.on('payment.failed', (res: any) => {
          console.log(res);
          this.messageService.showToastMessage('error', 'Payment Failed');
        });
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
      image:
        'https://example.com/https://www.udemy.com/staticx/udemy/images/v7/logo-udemy.svg',
      order_id: payment.orderId, //This is a sample Order ID. Pass the `id` obtained in the response of Step 1
      handler: (response: PaymentRequest) => {
        this.purchaseService
          .completePayment(response)
          .subscribe((res: GenericResponse) => {
            this.messageService.showToastMessage('success', res.message);
            let courseIds = this.sharedService.getCarts();
            this.enrollService.addEnrolledCourses(courseIds).subscribe(
              () => {
                this.sharedService.deleteCart();
                this.router.navigateByUrl('/enrollments');
              },
              (err: ErrorResponse) => {
                this.messageService.showToastMessage(
                  'error',
                  'Something went wrong, Please try again'
                );
              }
            );
          });
      },
      prefill: {
        name: 'Saurabh Sen',
        email: 'saurbhsen@gmail.com',
        contact: '9999999999',
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
