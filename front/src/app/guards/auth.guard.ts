// src/app/guards/auth.guard.ts
import { Injectable, inject } from '@angular/core';
import {
  CanActivate,
  UrlTree,
  Router
} from '@angular/router';
import { Observable } from 'rxjs'; 
import { LoginService } from '../service/login.service';


@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  private authService = inject(LoginService);
  private router = inject(Router);


  canActivate(): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    if (this.authService.isLoggedIn()) {
      return true;
    } else {
      return this.router.createUrlTree(['/login']);
    }
  }
}