import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { Observable } from 'rxjs';
import { TokenStorageService } from '../services/token-storage.service';

@Injectable({
  providedIn: 'root',
})
export class UserAuthGuard implements CanActivate {
  constructor(
    private tokenStorageService: TokenStorageService,
    private router: Router
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    const user = this.tokenStorageService.getUser();
    if (user && user.role === ROLE.USER) return true;
    this.router.navigateByUrl('/');
    return false;
  }
}

export enum ROLE {
  USER = 'USER',
  ADMIN = 'ADMIN',
}
