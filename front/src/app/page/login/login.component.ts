import { Component, inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { LoginService } from '../../service/login.service';
import { User } from '../../entity/User';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { NotificationComponent } from '../../notification/notification.component';
import { CommonModule } from '@angular/common';

import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

@Component({
  selector: 'app-login',
  imports: [
    ReactiveFormsModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatIconModule,
    MatSnackBarModule,
    CommonModule,
    MatProgressSpinnerModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginForm: FormGroup;
  hide = true;
  isLoading = false; // Adicionado estado de carregamento
  private _snackBar = inject(MatSnackBar);

  constructor(
    private fb: FormBuilder,
    private loginService: LoginService,
    private router: Router,
    private dialogConfirmacao: MatDialog // Mantido, mas não usado neste método
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      this.isLoading = true; // Inicia o carregamento
      const { email, password } = this.loginForm.value;

      const user: User = {
        email: email,
        password: password
      };

      this.loginService.login(user).subscribe({
        next: (resData) => {
          this.isLoading = false; // Finaliza o carregamento
          this.router.navigate(['/home']); // Redireciona para a home
        },
        error: (error) => {
          console.error('Erro no login:', error);
          this.isLoading = false; // Finaliza o carregamento
          // Aqui você pode usar 'error.message' se o seu handleError retornar uma mensagem específica
          // Ou manter a mensagem genérica como solicitado
          this.notificarErro('Email ou Senha incorretos.');
        }
      });
    }
  }

  cadastrarCliente() {
    this.router.navigate(['/client']);
  }

  // Método notificarErro agora aceita uma mensagem
  notificarErro(message: string) {
      this._snackBar.openFromComponent(NotificationComponent, {
        duration: 5 * 1000,
        data: {
          message: message, // Usa a mensagem passada
          type: 'error'
        },
        panelClass: ['snackbar-error']
      });
    }

}