import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { Product } from '../entity/Product';
import { ProductService } from '../service/product.service';
import { ActivatedRoute } from '@angular/router';
import { Department } from '../entity/Department';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-product',
  imports: [
    ReactiveFormsModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatIconModule,
    CommonModule
  ],
  templateUrl: './product.component.html',
  styleUrl: './product.component.css'
})
export class ProductComponent implements OnInit{

  id: string | null = null;
  productForm: FormGroup;
  titulo: string = 'Cadastro de Produto';

  private product: Product = {
    name: '',
    description: '',
    price: 0,
    department: '',
    idDepartment: 0
  };

  constructor(
    private fb: FormBuilder, 
    private crudService: ProductService,
    private route: ActivatedRoute
  ) 
  {
    this.productForm = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      price: ['', [Validators.required, Validators.min(0)]],
      department: [{value: '', disabled: true}, Validators.required],
      // idDepartment: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    
    if (this.id) {
      this.crudService.getById(this.id).subscribe({
        next: (product: Product) => {
          this.productForm.patchValue({
            name: product.name,
            description: product.description,
            price: product.price,
            department: product.department,
            idDepartment: product.idDepartment
          });
          console.log('Product encontrado:', product);
          this.titulo = 'Edição do Produto';
        },
        error: (err) => {
          console.error('Erro ao buscar cliente:', err);
        }
      });
    }
  }

  onSubmit() {
      
      if(this.productForm.valid) {
        this.productForm.value;
  
        var product: Product ={
          name: this.productForm.value.name,
          description: this.productForm.value.description,
          price: this.productForm.value.price,
          department: this.productForm.value.department,
          idDepartment: this.productForm.value.idDepartment
        }
        
        console.log(product);
  
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
          this.crudService.create(product).subscribe({
            next: (response) => {
              console.log('Departamento cadastrado com sucesso:', response);
              // Exibir mensagem de sucesso ou redirecionar
            },
            error: (error) => {
              console.error('Erro ao cadastrar departamento:', error);
              // Exibir mensagem de erro ou redirecionar
            }
          });
        }
        
      }
    }

}
