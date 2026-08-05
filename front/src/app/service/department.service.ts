import { Injectable } from '@angular/core';
import { CrudBaseService } from './base/crud-base.service';
import { Department } from '../entity/Department';

@Injectable({
  providedIn: 'root'
})
export class DepartmentService extends CrudBaseService<Department> {
  protected endpoint = 'departments';
}
