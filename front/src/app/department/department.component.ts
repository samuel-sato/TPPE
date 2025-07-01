import { Component, OnInit } from '@angular/core';
import { DepartmentService } from '../service/department.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Department } from '../entity/Departament';

@Component({
  selector: 'app-department',
  imports: [],
  templateUrl: './department.component.html',
  styleUrl: './department.component.css'
})
export class DepartmentComponent implements OnInit {

  private id: null | string;

  private department: Department = {
    name: '',
    description: '',
    products: []
  };

  constructor(private service: DepartmentService,
    private route: ActivatedRoute, // Para acessar os parâmetros da rota
    private router: Router){}       // Para navegação programática){
  

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.id = params.get('id'); // Tenta obter o 'id' da rota

      if (this.id) {
        this.isEditMode = true;
        this.loadProductForEdit(this.id); // Carrega o produto se o ID existir
      } else {
        this.isEditMode = false;
        this.resetForm(); // Reseta o formulário para criação
      }
    });
  }


}
