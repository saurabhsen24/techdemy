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
import { ROLE } from './user-auth.guard';

@Injectable({
  providedIn: 'root',
})
export class AdminAuthGuard implements CanActivate {
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
    if (user && user.role === ROLE.ADMIN) return true;
    this.router.navigateByUrl('/admin');
    return false;
  }
}
