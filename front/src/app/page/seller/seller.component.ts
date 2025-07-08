import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { ActivatedRoute } from '@angular/router';
import { Seller } from '../../entity/Seller';
import { SellerService } from '../../service/seller.service';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { provideNativeDateAdapter } from '@angular/material/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NotificationComponent } from '../../notification/notification.component';

@Component({
  selector: 'app-seller',
  imports: [
    ReactiveFormsModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatIconModule,
    MatDatepickerModule
  ],
  templateUrl: './seller.component.html',
  styleUrl: './seller.component.css',
  providers: [provideNativeDateAdapter()]
})
export class SellerComponent implements OnInit {

  private _snackBar = inject(MatSnackBar);
  id: string | null = null;
  sellerForm: FormGroup;
  titulo: string = 'Cadastro de Vendedor';
  hide = true;

  private seller: Seller = {
    name: '',
    email: '',
    password: '',
    birthdate: new Date(),
    baseSalary: 0,
    numberHours: 0
  };

  constructor(
    private fb: FormBuilder, 
    private crudService: SellerService,
    private route: ActivatedRoute,
    private dialogConfirmacao: MatDialog
  ) 
  {
    this.sellerForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      birthdate: ['', Validators.required],
      baseSalary: ['', [Validators.required, Validators.min(0)]],
      numberHours: ['', [Validators.required, Validators.min(0)]]
    });
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    
    if (this.id) {
      this.crudService.getById(this.id).subscribe({
        next: (seller: Seller) => {
          this.sellerForm.patchValue({
            name: seller.name,
            email: seller.email,
            password: seller.password,
            birthdate: new Date(seller.birthdate),
            baseSalary: seller.baseSalary,
            numberHours: seller.numberHours
          });
          console.log('Seller encontrado:', seller);
          this.titulo = 'Edição de Vendedor';
        },
        error: (err) => {
          console.error('Erro ao buscar vendedor:', err);
        }
      });
    }
  }

  onSubmit() {
      
      if(this.sellerForm.valid) {
        this.sellerForm.value;
  
        var seller: Seller ={
          name: this.sellerForm.value.name,
          email: this.sellerForm.value.email,
          password: this.sellerForm.value.password,
          birthdate: new Date(this.sellerForm.value.birthdate),
          baseSalary: this.sellerForm.value.baseSalary,
          numberHours: this.sellerForm.value.numberHours
        }
        
        console.log(seller);
  
        if (this.id) {
          // Atualizar
          seller.id = parseInt(this.id, 10);
          this.crudService.update(seller).subscribe({
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
          this.crudService.create(seller).subscribe({
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
        message: 'Cliente cadastrado com sucesso!', // Sua mensagem de sucesso
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
        message: 'Erro ao salvar cliente. Tente novamente.', // Sua mensagem de erro
        type: 'error'
      },
      panelClass: ['snackbar-error'] // Opcional: para aplicar estilos CSS
    });
  }
}
