import { Component, OnInit } from '@angular/core';
import { DepartmentService } from '../service/department.service';
import { ActivatedRoute } from '@angular/router';
import { Department } from '../entity/Departament';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';

@Component({
  selector: 'app-department',
  imports: [
    ReactiveFormsModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatIconModule
  ],
  templateUrl: './department.component.html',
  styleUrl: './department.component.css'
})
export class DepartmentComponent implements OnInit {

  id: string | null = null;
  departmentForm: FormGroup;
  titulo: string = 'Cadastro de Departamento';

  private department: Department = {
    name: '',
    description: '',
    products: []
  };

  constructor(
    private fb: FormBuilder, 
    private crudService: DepartmentService,
    private route: ActivatedRoute
  ) 
  {
    this.departmentForm = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    
    if (this.id) {
      this.crudService.getById(this.id).subscribe({
        next: (department: Department) => {
          this.departmentForm.patchValue({
            name: department.name,
            description: department.description,
            products: department.products
          });
          console.log('Departamento encontrado:', department);
          this.titulo = 'Edição de Departamento';
        },
        error: (err) => {
          console.error('Erro ao buscar cliente:', err);
        }
      });
    }
  }

  onSubmit() {
      
      if (this.departmentForm.valid) {
        const { name, description} = this.departmentForm.value;
  
        var department: Department ={
          name: name,
          description: description,
          products: []
        }
        
        console.log(department);
  
        if (this.id) {
          // Atualizar
          // this.crudService.update(this.id, cliente).subscribe({
          //   next: (response) => {
          //     console.log('Cliente atualizado com sucesso:', response);
          //   },
          //   error: (error) => {
          //     console.error('Erro ao atualizar cliente:', error);
          //   }
          // });
        } 
        else {
          // Criar
          this.crudService.create(department).subscribe({
            next: (response) => {
              console.log('Departamento cadastrado com sucesso:', response);
            },
            error: (error) => {
              console.error('Erro ao cadastrar departamento:', error);
            }
          });
        }
        
      }
    }
}
