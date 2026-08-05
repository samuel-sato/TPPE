import { Injectable } from '@angular/core';
import { CrudBaseService } from './base/crud-base.service';
import { Seller } from '../entity/Seller';

@Injectable({
  providedIn: 'root'
})
export class SellerService extends CrudBaseService<Seller> {
  protected endpoint = 'sellers';
}
