import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { Router } from '@angular/router';

import { LoginService } from './login.service';
import { environment } from '../../environments/environments';
import { JwtPayload } from '../entity/JwtPayload';

/** Monta um JWT "de verdade" (header.payload.assinatura) para o jwt-decode conseguir ler. */
function buildJwt(payload: Partial<JwtPayload>): string {
  const base64UrlEncode = (obj: unknown) =>
    btoa(JSON.stringify(obj)).replace(/\+/g, '-').replace(/\//g, '_').replace(/=+$/, '');

  return `${base64UrlEncode({ alg: 'HS256', typ: 'JWT' })}.${base64UrlEncode(payload)}.signature`;
}

describe('LoginService', () => {
  let service: LoginService;
  let httpMock: HttpTestingController;
  let router: jasmine.SpyObj<Router>;

  const loginUrl = `${environment.apiUrl}/login`;

  beforeEach(() => {
    router = jasmine.createSpyObj('Router', ['navigate']);

    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        { provide: Router, useValue: router }
      ]
    });

    service = TestBed.inject(LoginService);
    httpMock = TestBed.inject(HttpTestingController);
    sessionStorage.clear();
  });

  afterEach(() => {
    httpMock.verify();
    sessionStorage.clear();
  });

  it('deve ser criado', () => {
    expect(service).toBeTruthy();
  });

  it('login() deve decodificar o token e armazenar os dados no sessionStorage', () => {
    const exp = Math.floor(Date.now() / 1000) + 3600;
    const token = buildJwt({ upn: 'usuario@teste.com', groups: 'ADMIN', exp });

    service.login({ email: 'usuario@teste.com', password: '123456' }).subscribe();

    const req = httpMock.expectOne(loginUrl);
    expect(req.request.method).toBe('POST');
    req.flush({ token });

    expect(sessionStorage.getItem('auth_token')).toBe(token);
    expect(sessionStorage.getItem('user_id')).toBe('usuario@teste.com');
    expect(sessionStorage.getItem('user_profile')).toBe('ADMIN');
    expect(sessionStorage.getItem('token_expiration')).toBe(exp.toString());
  });

  it('login() deve propagar uma mensagem de erro tratada quando a API rejeita as credenciais', () => {
    let capturedError: Error | undefined;

    service.login({ email: 'usuario@teste.com', password: 'errada' }).subscribe({
      error: (err) => (capturedError = err)
    });

    const req = httpMock.expectOne(loginUrl);
    req.flush({ message: 'Credenciais inválidas' }, { status: 401, statusText: 'Unauthorized' });

    expect(capturedError?.message).toBe('Credenciais inválidas');
    expect(sessionStorage.getItem('auth_token')).toBeNull();
  });

  it('isLoggedIn() deve retornar false quando não há token armazenado', () => {
    expect(service.isLoggedIn()).toBeFalse();
  });

  it('isLoggedIn() deve retornar true para um token ainda válido', () => {
    const exp = Math.floor(Date.now() / 1000) + 3600;
    sessionStorage.setItem('auth_token', buildJwt({ upn: 'a', groups: 'g', exp }));
    sessionStorage.setItem('token_expiration', exp.toString());

    expect(service.isLoggedIn()).toBeTrue();
  });

  it('isLoggedIn() deve retornar false quando o token já expirou', () => {
    const exp = Math.floor(Date.now() / 1000) - 3600;
    sessionStorage.setItem('auth_token', buildJwt({ upn: 'a', groups: 'g', exp }));
    sessionStorage.setItem('token_expiration', exp.toString());

    expect(service.isLoggedIn()).toBeFalse();
  });

  it('isLoggedIn() deve fazer logout quando o token armazenado está corrompido', () => {
    sessionStorage.setItem('auth_token', 'token-invalido-sem-formato-jwt');
    sessionStorage.setItem('token_expiration', String(Math.floor(Date.now() / 1000) + 3600));

    expect(service.isLoggedIn()).toBeFalse();
    expect(router.navigate).toHaveBeenCalledWith(['/auth/login']);
    expect(sessionStorage.getItem('auth_token')).toBeNull();
  });

  it('logout() deve limpar o sessionStorage e navegar para /auth/login', () => {
    sessionStorage.setItem('auth_token', 'algum-token');

    service.logout();

    expect(sessionStorage.getItem('auth_token')).toBeNull();
    expect(router.navigate).toHaveBeenCalledWith(['/auth/login']);
  });

  it('getUserProfile() e getUserId() devem ler os valores gravados no sessionStorage', () => {
    sessionStorage.setItem('user_profile', 'ADMIN');
    sessionStorage.setItem('user_id', 'usuario@teste.com');

    expect(service.getUserProfile()).toBe('ADMIN');
    expect(service.getUserId()).toBe('usuario@teste.com');
  });

  it('getUserProfile() e getUserId() devem retornar string vazia quando nada foi armazenado', () => {
    expect(service.getUserProfile()).toBe('');
    expect(service.getUserId()).toBe('');
  });
});
