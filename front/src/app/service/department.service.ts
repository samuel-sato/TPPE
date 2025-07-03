import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CrudBaseService } from './base/crud-base.service';
import { Department } from '../entity/Department';

@Injectable({
  providedIn: 'root'
})
export class DepartmentService extends CrudBaseService<Department> {

  constructor(http: HttpClient){
    super(http, 'departments');
  }
}
