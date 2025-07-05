import { Injectable } from '@angular/core';
import { Product } from '../entity/Product';
import { CrudBaseService } from './base/crud-base.service';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProductService extends CrudBaseService<Product> {

  constructor(http: HttpClient){
    super(http, 'products');
  }
}
