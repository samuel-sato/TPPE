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
  standalone: true, // Adicione standalone: true se não estiver usando módulos
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

  products: Product[] = []; // Armazena todos os produtos carregados
  filteredProducts: Product[] = []; // Armazena os produtos filtrados para exibição
  departments: Department[] = []; // Armazena todos os departamentos
  selectedDepartment = new FormControl<number | null>(null); // Use number | null para o FormControl

  constructor(
    private productService: ProductService,
    private departmentService: DepartmentService
  ) { }

  ngOnInit(): void {
    // Carrega todos os produtos inicialmente
    this.productService.getAll().subscribe(data => {
      this.products = data;
      this.applyFilter(); // Exibe todos inicialmente
    });

    // Carrega os departamentos
    this.departmentService.getAll().subscribe(data => {
      this.departments = data;
      // Adiciona a opção "Todos" no início da lista de departamentos
      this.departments.unshift({ id: 0, name: 'Todos', description: '', products:[] }); // Adiciona "Todos" com ID 0
      this.selectedDepartment.setValue(0); // Define "Todos" como padrão selecionado
    });

    // Observa mudanças no seletor de departamento para filtrar
    this.selectedDepartment.valueChanges.subscribe(departmentId => {
      this.applyFilter(); // Chama o método de filtro em memória
    });
  }

  /**
   * Aplica o filtro de produtos com base no departamento selecionado.
   * Filtra a lista 'products' em memória e atualiza 'filteredProducts'.
   */
  applyFilter(): void {
    const departmentId = this.selectedDepartment.value;

    if (departmentId === 0 || departmentId === null) {
      // Se "Todos" (ID 0) ou nenhum departamento estiver selecionado, exibe todos os produtos
      this.filteredProducts = [...this.products]; // Cria uma cópia para garantir a detecção de mudanças pelo Angular
    } else {
      // Filtra os produtos onde o department.id corresponde ao departmentId selecionado
      this.filteredProducts = this.products.filter(product =>
        product.idDepartment === departmentId
      );
    }
  }

  // Função para comparar objetos no mat-select (útil se você usar objetos no FormControl)
  // Mas como estamos usando o ID no FormControl, esta função não é estritamente necessária para a lógica atual,
  // mas é uma boa prática se o valor do FormControl fosse um objeto Department.
  compareDepartments(obj1: Department, obj2: Department): boolean {
    return obj1 && obj2 && obj1.id === obj2.id;
  }
}