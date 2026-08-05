import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, throwError, catchError } from 'rxjs';
import { environment } from '../../../environments/environments';
import { BaseEntity } from '../../entity/BaseEntity';

@Injectable({
  providedIn: 'root'
})
export abstract class CrudBaseService<E extends BaseEntity> {

  protected abstract endpoint: string;
  protected http = inject(HttpClient);
  protected headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  protected get url(): string {
    return `${environment.apiUrl}/${this.endpoint}`;
  }

  protected handleError(error: HttpErrorResponse): Observable<never> {
    // console.error(`Erro na requisição para ${this.url}:`, error);
    let errorMessage = 'Ocorreu um erro desconhecido.';
    
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Erro: ${error.error.message}`;
    } else if (error.status) {
      errorMessage = `Erro no servidor (${error.status}): ${error.message || error.error}`;
    }

    return throwError(() => new Error(errorMessage));
  }

  create(item: E): Observable<E> {
    return this.http.post<E>(this.url, item, { headers: this.headers }).pipe(
      catchError(error => this.handleError(error)) // corrigido aqui
    );
  }

  update(item: E): Observable<E> {
    return this.http.put<E>(`${this.url}/${item.id}`, item, { headers: this.headers }).pipe(
      catchError(error => this.handleError(error)) // corrigido aqui
    );
  }

  
  getAll(): Observable<E[]> {
    return this.http.get<E[]>(this.url, { headers: this.headers }).pipe(
      catchError(error => this.handleError(error))
    );
  }

  
  getById(id: string | number): Observable<E> {
    return this.http.get<E>(`${this.url}/${id}`, { headers: this.headers }).pipe(
      catchError(error => this.handleError(error))
    );
  }

  delete(id: string | number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`, { headers: this.headers }).pipe(
      catchError(error => this.handleError(error))
    );
  }
}
