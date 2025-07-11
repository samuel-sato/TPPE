import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CrudBaseService } from './base/crud-base.service';
import { Sale } from '../entity/Sale';
import { Observable, catchError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SaleService extends CrudBaseService<Sale> {

    constructor(http: HttpClient){
    super(http, 'sales');
    }

    getByClientId(id: string | number): Observable<Sale[]> {
      return this.http.get<Sale[]>(`${this.url}/client/${id}`, { headers: this.headers }).pipe(
        catchError(error => this.handleError(error))
      );
    }
}
