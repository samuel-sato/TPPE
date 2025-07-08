import { ChangeDetectionStrategy, Component, inject, model, OnInit, signal } from '@angular/core';
import { DepartmentService } from '../../service/department.service';
import { ActivatedRoute } from '@angular/router';
import { Department } from '../../entity/Department';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatTableModule } from '@angular/material/table';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { ProductSelectorDialogComponent } from '../../dialog/dialog-product/dialog-product.component';
import { ProductList } from '../../entity/ProductList';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NotificationComponent } from '../../notification/notification.component';

@Component({
  selector: 'app-department',
  imports: [
    ReactiveFormsModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatIconModule,
    MatTableModule
  ],
  templateUrl: './department.component.html',
  styleUrl: './department.component.css'
})
export class DepartmentComponent implements OnInit {

  private _snackBar = inject(MatSnackBar);
  id: string | null = null;
  departmentForm: FormGroup;
  titulo: string = 'Cadastro de Departamento';
  displayedColumns: string[] = ['name', 'price', 'actions'];

  department: Department = {
    name: '',
    description: '',
    products: []
  };

  constructor(
    private fb: FormBuilder, 
    private crudService: DepartmentService,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private dialogConfirmacao: MatDialog
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
          this.department = department;
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

      this.department = {
        name: name,
        description: description,
        products: this.department.products || []
      };
    
      
      console.log(this.department);

      if (this.id) {
        this.department.id = parseInt(this.id, 10); 
        // Atualizar
        this.crudService.update(this.department).subscribe({
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
        this.crudService.create(this.department).subscribe({
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



  addProduct(): void {
    this.dialog.open(ProductSelectorDialogComponent).afterClosed().subscribe((selecionados: ProductList[]) => {
      if (selecionados && selecionados.length > 0) {
        const produtosExistentes = this.department.products || [];
        const novosProdutos = selecionados.filter(p => !produtosExistentes.some(e => e.id === p.id));
        this.department.products = [...produtosExistentes, ...novosProdutos];
        this.departmentForm.patchValue({ products: this.department.products });
      }
    });
  }


  deleteProductDepartament(id: number) {
    console.log(id);
    if (this.department.products) {
      this.department.products = this.department.products.filter(product => product.id !== id);
      this.departmentForm.patchValue({ products: this.department.products });
    }
  }

  notificarSucesso(){
      this._snackBar.openFromComponent(NotificationComponent, {
        duration: 5 * 1000,
        data: {
          message: 'Departamento cadastrado com sucesso!', // Sua mensagem de sucesso
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
          message: 'Erro ao salvar departamento. Tente novamente.', // Sua mensagem de erro
          type: 'error'
        },
        panelClass: ['snackbar-error'] // Opcional: para aplicar estilos CSS
      });
    }
  
}
