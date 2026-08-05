import { Injectable } from '@angular/core';
import { Client } from '../entity/Client';
import { CrudBaseService } from './base/crud-base.service';

@Injectable({
  providedIn: 'root'
})
export class ClienteService extends CrudBaseService<Client> {
  protected endpoint = 'clients';
}
