import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, Router } from '@angular/router';

import { AppComponent } from './app.component';
import { LoginService } from './service/login.service';

describe('AppComponent', () => {
  let fixture: ComponentFixture<AppComponent>;
  let loginService: jasmine.SpyObj<LoginService>;

  beforeEach(async () => {
    loginService = jasmine.createSpyObj('LoginService', ['getUserProfile', 'getUserId', 'logout']);
    loginService.getUserProfile.and.returnValue('');
    loginService.getUserId.and.returnValue('');

    await TestBed.configureTestingModule({
      imports: [AppComponent],
      providers: [
        provideRouter([]),
        { provide: LoginService, useValue: loginService }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(AppComponent);
  });

  it('deve ser criado', () => {
    expect(fixture.componentInstance).toBeTruthy();
  });

  it(`deve ter o título 'E-Loja'`, () => {
    expect(fixture.componentInstance.title).toEqual('E-Loja');
  });

  it('deve renderizar a marca "E-Loja" no toolbar', () => {
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('mat-toolbar')?.textContent).toContain('Loja');
  });

  it('não deve exibir nenhum menu quando não há perfil (usuário deslogado)', () => {
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('.centered-icons')).toBeNull();
  });

  it('deve montar routerPersonData para o perfil de cliente (role 2)', () => {
    loginService.getUserProfile.and.returnValue('2');
    loginService.getUserId.and.returnValue('42');

    fixture.detectChanges();

    expect(fixture.componentInstance.routerPersonData).toBe('/client/42');
  });

  it('logout() deve delegar ao LoginService e navegar para /login', () => {
    fixture.detectChanges();
    const router = TestBed.inject(Router);
    spyOn(router, 'navigate');

    fixture.componentInstance.logout();

    expect(loginService.logout).toHaveBeenCalled();
    expect(router.navigate).toHaveBeenCalledWith(['/login']);
  });
});
