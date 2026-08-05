import { TestBed } from '@angular/core/testing';
import { Router, UrlTree } from '@angular/router';

import { AuthGuard } from './auth.guard';
import { LoginService } from '../service/login.service';

describe('AuthGuard', () => {
  let guard: AuthGuard;
  let loginService: jasmine.SpyObj<LoginService>;
  let router: jasmine.SpyObj<Router>;

  beforeEach(() => {
    loginService = jasmine.createSpyObj('LoginService', ['isLoggedIn']);
    router = jasmine.createSpyObj('Router', ['createUrlTree']);

    TestBed.configureTestingModule({
      providers: [
        AuthGuard,
        { provide: LoginService, useValue: loginService },
        { provide: Router, useValue: router }
      ]
    });

    guard = TestBed.inject(AuthGuard);
  });

  it('deve ser criado', () => {
    expect(guard).toBeTruthy();
  });

  it('deve permitir a ativação quando o usuário está logado', () => {
    loginService.isLoggedIn.and.returnValue(true);

    expect(guard.canActivate()).toBeTrue();
    expect(router.createUrlTree).not.toHaveBeenCalled();
  });

  it('deve redirecionar para /login quando o usuário não está logado', () => {
    loginService.isLoggedIn.and.returnValue(false);
    const redirectTree = {} as UrlTree;
    router.createUrlTree.and.returnValue(redirectTree);

    const result = guard.canActivate();

    expect(router.createUrlTree).toHaveBeenCalledWith(['/login']);
    expect(result).toBe(redirectTree);
  });
});
