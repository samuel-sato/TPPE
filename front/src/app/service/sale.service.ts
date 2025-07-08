import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CrudBaseService } from './base/crud-base.service';
import { Sale } from '../entity/Sale';

@Injectable({
  providedIn: 'root'
})
export class SaleService extends CrudBaseService<Sale> {

    constructor(http: HttpClient){
    super(http, 'sales');
  }
}
