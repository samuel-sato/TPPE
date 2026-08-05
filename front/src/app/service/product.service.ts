import { Injectable } from '@angular/core';
import { Product } from '../entity/Product';
import { CrudBaseService } from './base/crud-base.service';

@Injectable({
  providedIn: 'root'
})
export class ProductService extends CrudBaseService<Product> {
  protected endpoint = 'products';
}
