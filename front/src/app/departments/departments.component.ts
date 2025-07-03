import { Component, OnInit } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { DepartmentService } from '../service/department.service';
import { DepartmentList } from '../entity/DepartmentList';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-departments',
  imports: [MatTableModule, CommonModule,MatIconModule],
  templateUrl: './departments.component.html',
  styleUrl: './departments.component.css'
})
export class DepartmentsComponent implements OnInit{

  departments: DepartmentList[] = [];
  displayedColumns: string[] = ['name', 'description', 'actions'];

  constructor(private service: DepartmentService){
  }

  ngOnInit(): void {
    this.service.getAll().subscribe({
      next: (data: DepartmentList[])=>{
        console.log(data)
        this.departments = data
      }
    })
  }

  editDepartment(id: any){
    console.log(id);
  }

  deleteDepartment(id: any){
    console.log(id);
  }

}
