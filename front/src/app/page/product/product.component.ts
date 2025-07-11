import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { Product } from '../../entity/Product';
import { ProductService } from '../../service/product.service';
import { ActivatedRoute } from '@angular/router';
import { Department } from '../../entity/Department';
import { CommonModule } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NotificationComponent } from '../../notification/notification.component';

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

  private _snackBar = inject(MatSnackBar);
  id: string | null = null;
  productForm: FormGroup;
  titulo: string = 'Cadastro de Produto';

  

  constructor(
    private fb: FormBuilder, 
    private crudService: ProductService,
    private route: ActivatedRoute,
    private dialogConfirmacao: MatDialog
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
      

      if (this.id) {
        // Atualizar
        product.id = parseInt(this.id, 10);
        this.crudService.update(product).subscribe({
          next: (response) => {
            this.notificarSucesso();
          },
          error: (error) => {
            this.notificarErro();
          }
        });
      } 
      else {
        // Criar
        this.crudService.create(product).subscribe({
          next: (response) => {
            this.notificarSucesso();
          },
          error: (error) => {
            this.notificarErro();
          }
        });
      }
    }
  }

  notificarSucesso(){
      this._snackBar.openFromComponent(NotificationComponent, {
        duration: 5 * 1000,
        data: {
          message: 'Produto cadastrado com sucesso!', // Sua mensagem de sucesso
          // Você pode adicionar um tipo, se quiser cores diferentes para sucesso/erro
          type: 'success'
        },
        panelClass: ['snackbar-success'] // Opcional: para aplicar estilos CSS
      });
    }
  
  notificarErro(){
    this._snackBar.openFromComponent(NotificationComponent, {
      duration: 5 * 1000,
      data: {
        message: 'Erro ao salvar produto. Tente novamente.', // Sua mensagem de erro
        type: 'error'
      },
      panelClass: ['snackbar-error'] // Opcional: para aplicar estilos CSS
    });
  }
}
