import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { ActivatedRoute } from '@angular/router';
import { Seller } from '../entity/Seller';
import { SellerService } from '../service/seller.service';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { provideNativeDateAdapter } from '@angular/material/core';

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
    private route: ActivatedRoute
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
          this.crudService.create(seller).subscribe({
            next: (response) => {
              console.log('Seller cadastrado com sucesso:', response);
            },
            error: (error) => {
              console.error('Erro ao cadastrar seller:', error);
            }
          });
        }
        
      }
    }
}
