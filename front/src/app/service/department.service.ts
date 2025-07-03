import { Injectable } from '@angular/core';
import { DepartmentList } from '../entity/DepartmentList';
import { HttpClient } from '@angular/common/http';
import { CrudBaseService } from './base/crud-base.service';

@Injectable({
  providedIn: 'root'
})
export class DepartmentService extends CrudBaseService<DepartmentList> {

  constructor(http: HttpClient){
    super(http, 'departments');
  }
}
