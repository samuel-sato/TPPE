import { Injectable } from '@angular/core';
import { Client } from '../entity/Client';
import { HttpClient } from '@angular/common/http';
import { CrudBaseService } from './base/crud-base.service';

@Injectable({
  providedIn: 'root'
})
export class ClienteService  extends CrudBaseService<Client> {

  constructor(http: HttpClient){
    super(http, 'clients');
  }
}
