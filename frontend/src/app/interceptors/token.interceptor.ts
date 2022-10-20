import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenStorageService } from '../services/token-storage.service';
import { UserData } from '../models/UserData.model';

const TOKEN_HEADER_KEY = 'Authorization';
const BEARER_TOKEN = 'Bearer';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {
  constructor(private tokenStorage: TokenStorageService) {}

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    if (request.url.indexOf('auth') !== -1) {
      return next.handle(request);
    }

    const user: UserData | null = this.tokenStorage.getUser();

    if (user && user.accessToken != null) {
      request = this.addTokenHeader(request, user.accessToken);
    }

    return next.handle(request);
  }

  private addTokenHeader(request: HttpRequest<unknown>, token: string) {
    return request.clone({
      headers: request.headers.set(
        TOKEN_HEADER_KEY,
        `${BEARER_TOKEN} ${token}`
      ),
    });
  }
}
