import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError, catchError } from 'rxjs';
import { environment } from '../../../environments/environments';
import { BaseEntity } from '../../entity/BaseEntity';

@Injectable({
  providedIn: 'root'
})
export abstract class CrudBaseService<E extends BaseEntity> {

  protected url: string;
  protected headers: HttpHeaders;
  
  constructor(
    protected http: HttpClient,
    protected endpoint: string 
  ) { 
    this.url = `${environment.apiUrl}/${this.endpoint}`;
    this.headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  }

  protected handleError(error: any): Observable<never> {
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
}
