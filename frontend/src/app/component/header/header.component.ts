import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SharedService } from 'src/app/services/shared.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
  isLoggedIn: Boolean = false;
  isAdmin: Boolean = false;
  username: string = '';
  cartCount: Number = 0;

  constructor(
    private tokenStorage: TokenStorageService,
    private sharedService: SharedService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.autoLogin();

    this.tokenStorage.authStatusListener.subscribe((data) => {
      this.isLoggedIn = data;
    });

    this.tokenStorage.userNameListner.subscribe((userNameData: string) => {
      this.username = userNameData;
    });

    this.tokenStorage.adminRoleListener.subscribe((isAdmin: Boolean) => {
      this.isAdmin = isAdmin;
    });

    this.sharedService.cartCountSubscription.subscribe((cartCount: Number) => {
      this.cartCount = cartCount;
    });
  }

  autoLogin() {
    const user = this.tokenStorage.getUser();
    if (user) {
      this.isLoggedIn = true;
      this.username = user.userName;
      this.isAdmin = this.tokenStorage.checkAdmin();
      this.cartCount = this.sharedService.getCartCount();
      this.tokenStorage.adminRoleListener.next(true);
      this.tokenStorage.authStatusListener.next(true);
    } else {
      this.isLoggedIn = false;
      this.isAdmin = false;
      this.tokenStorage.adminRoleListener.next(false);
      this.tokenStorage.authStatusListener.next(false);
    }
  }

  logOutUser() {
    this.tokenStorage.logOut();
    this.router.navigateByUrl('/login');
  }

  ngOnDestroy(): void {
    this.tokenStorage.authStatusListener.unsubscribe();
    this.tokenStorage.userNameListner.unsubscribe();
    this.tokenStorage.adminRoleListener.unsubscribe();
  }
}
