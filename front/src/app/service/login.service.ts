import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, tap, throwError } from 'rxjs';
import { JwtPayload } from '../entity/JwtPayload';
import { LoginResponse } from '../entity/LoginResponse';
import { environment } from '../../environments/environments';
import { User } from '../entity/User';
import { jwtDecode } from 'jwt-decode'; 
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private readonly endpoint = 'login'; 
  private apiUrl = `${environment.apiUrl}/${this.endpoint}`;

  constructor(
    private http: HttpClient,
    private router: Router
  ) { }

  /**
   * Realiza o login do usuário.
   * Ao sucesso, decodifica o token e armazena os valores no sessionStorage.
   * @param userCredentials Objeto User contendo email e password.
   * @returns Um Observable com a resposta do backend.
   */
  login(userCredentials: User): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(this.apiUrl, userCredentials)
      .pipe(
        catchError(this.handleError),
        tap(resData => {
          const token = resData.token;
          let decodedToken: JwtPayload;

          try {
            decodedToken = jwtDecode<JwtPayload>(token);
            console.log('Token decodificado:', decodedToken);

            // Armazena o token e os dados decodificados no sessionStorage
            // sessionStorage só armazena strings, então convertemos o objeto JWT para string
            sessionStorage.setItem('auth_token', token);
            sessionStorage.setItem('user_name', decodedToken.upn);    // <-- Usando 'upn'
            sessionStorage.setItem('user_profile', decodedToken.groups); // <-- Usando 'groups'

            // Opcional: Armazenar a data de expiração para checagem mais robusta
            if (decodedToken.exp) {
                sessionStorage.setItem('token_expiration', decodedToken.exp.toString());
            }

            console.log('Login bem-sucedido! Token, nome e perfil setados na sessionStorage.');

          } catch (error) {
            console.error('Erro ao decodificar o token:', error);
            // Se o token for inválido, limpa tudo e lança um erro
            this.logout(); // Limpa dados da sessão se o token for inválido
            throw new Error('Token de autenticação inválido ou formato inesperado.');
          }
        })
      );
  }

  /**
   * Obtém o perfil do usuário a partir do sessionStorage.
   * @returns O perfil do usuário ou uma string vazia se não encontrado.
   */
  getUserProfile(): string {
    return sessionStorage.getItem('user_profile') || '';
  }

  /**
   * Obtém o nome do usuário a partir do sessionStorage.
   * @returns O nome do usuário ou uma string vazia se não encontrado.
   */
  getUserName(): string {
    return sessionStorage.getItem('user_name') || '';
  }

  /**
   * Verifica se o usuário está logado (se o token existe na sessionStorage e não expirou).
   * @returns Booleano indicando o estado de login.
   */
  isLoggedIn(): boolean {
    const token = sessionStorage.getItem('auth_token');
    const expiration = sessionStorage.getItem('token_expiration');

    if (!token || !expiration) {
      return false;
    }

    try {
        const decodedToken: JwtPayload = jwtDecode<JwtPayload>(token);
        const expTimestamp = parseInt(expiration, 10); // Converte de volta para número

        // Verifica se o token não expirou
        const currentTime = Date.now() / 1000; // Tempo atual em segundos
        return expTimestamp > currentTime;
    } catch (error) {
        console.error('Erro ao verificar token na sessionStorage (decodificação/expiração):', error);
        this.logout(); // Token inválido ou expirado, faz logout
        return false;
    }
  }

  /**
   * Realiza o logout do usuário.
   * Limpa os dados de autenticação do sessionStorage.
   */
  logout(): void {
    sessionStorage.clear(); // Limpa todos os itens do sessionStorage para esta origem
    console.log('Dados de autenticação removidos da sessionStorage. Usuário deslogado.');
    this.router.navigate(['/auth/login']); // Redireciona para a página de login
  }

  /**
   * Manipulador de erros para requisições HTTP.
   * @param errorResponse A resposta de erro HTTP.
   * @returns Um Observable de erro com a mensagem apropriada.
   */
  private handleError(errorResponse: HttpErrorResponse): Observable<never> {
    let errorMessage = 'Um erro desconhecido ocorreu!';
    if (errorResponse.error && errorResponse.error.message) {
      errorMessage = errorResponse.error.message;
    } else if (errorResponse.status === 401) {
      errorMessage = 'Credenciais inválidas. Verifique seu e-mail e senha.';
    } else if (errorResponse.status === 404) {
        errorMessage = 'Serviço de login não encontrado. Verifique a URL da API.';
    } else {
        errorMessage = `Erro do servidor: ${errorResponse.status} - ${errorResponse.statusText || ''}`;
    }
    return throwError(() => new Error(errorMessage));
  }
}
