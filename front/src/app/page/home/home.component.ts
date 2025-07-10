import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { Department } from '../../entity/Department';
import { Product } from '../../entity/Product';
import { ProductService } from '../../service/product.service';
import { DepartmentService } from '../../service/department.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-home',
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatSelectModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    RouterLink
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {

  products: Product[] = [];
  filteredProducts: Product[] = [];
  departments: Department[] = [];
  selectedDepartment = new FormControl(0); // Valor inicial para 'Todos'

  constructor(
    private productService: ProductService,
    private departmentService: DepartmentService
  ) { }

  ngOnInit(): void {
    // Carrega todos os produtos inicialmente
    this.productService.getAll().subscribe(data => {
      this.products = data;
      this.filteredProducts = data; // Exibe todos inicialmente
    });

    // Carrega os departamentos
    this.departmentService.getAll().subscribe(data => {
      this.departments = data;
      // Adiciona a opção "Todos" no início da lista de departamentos
      //this.departments.unshift({ id: 0, name: 'Todos' });
    });

    // Observa mudanças no seletor de departamento para filtrar
    this.selectedDepartment.valueChanges.subscribe(departmentId => {
      // if (departmentId !== null) {
      //   this.productService.getProductsByDepartment(departmentId).subscribe(data => {
      //     this.filteredProducts = data;
      //   });
      // }
    });
  }

  // Função para comparar objetos no mat-select
  compareDepartments(obj1: Department, obj2: Department): boolean {
    return obj1 && obj2 && obj1.id === obj2.id;
  }
}
