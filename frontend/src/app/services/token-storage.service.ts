import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { Constants } from '../constants/Constants';
import { LoginResponse } from '../models/responses/LoginResponse.model';
import { UserData } from '../models/UserData.model';

@Injectable({
  providedIn: 'root',
})
export class TokenStorageService {
  public authStatusListener = new Subject<boolean>();
  public userNameListner = new Subject<string>();
  public adminRoleListener = new Subject<boolean>();
  constructor() {}

  logOut(): void {
    sessionStorage.clear();
    this.adminRoleListener.next(false);
    this.authStatusListener.next(false);
  }

  saveUser(userData: LoginResponse) {
    sessionStorage.removeItem(Constants.USER_KEY);
    sessionStorage.setItem(Constants.USER_KEY, JSON.stringify(userData));
    this.authStatusListener.next(true);
  }

  getUser(): UserData | null {
    const user = sessionStorage.getItem(Constants.USER_KEY);
    if (user) {
      return JSON.parse(user);
    }

    return null;
  }

  checkAdmin() {
    const user = this.getUser();
    if (user && user.role === 'ADMIN') {
      this.adminRoleListener.next(true);
      return true;
    }

    this.adminRoleListener.next(false);
    return false;
  }
}
