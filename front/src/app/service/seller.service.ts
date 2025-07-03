import { Injectable } from '@angular/core';
import { CrudBaseService } from './base/crud-base.service';
import { Seller } from '../entity/Seller';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SellerService extends CrudBaseService<Seller> {

    constructor(http: HttpClient){
    super(http, 'sellers');
  }
}
