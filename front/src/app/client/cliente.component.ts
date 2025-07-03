import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { provideNativeDateAdapter } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { Client } from '../entity/Client';
import { CrudBaseService } from '../service/base/crud-base.service';
import { ActivatedRoute } from '@angular/router';
import { ClienteService } from '../service/cliente.service';

@Component({
  selector: 'app-client',
  imports: [
    ReactiveFormsModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatIconModule,
    MatSlideToggleModule,
    MatDatepickerModule
  ],
  templateUrl: './cliente.component.html',
  styleUrl: './cliente.component.css',
  providers: [provideNativeDateAdapter()]
})
export class ClientComponent implements OnInit {
  clientForm: FormGroup;
  hide = true;
  exampleHeader: any;
  id: string | null = null;
  titulo: string = 'Cadastro de Cliente';
  
  constructor(
    private fb: FormBuilder, 
    private crudService: ClienteService,
    private route: ActivatedRoute) {
    this.clientForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      name: ['', Validators.required],
      dataNascimento: ['', Validators.required],
      notificarPromocoes: [false] // Campo para notificar promoções, padrão é false
    });
  }
  
  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');

    if (this.id) {
      this.crudService.getById(this.id).subscribe({
        next: (cliente: Client) => {
          this.clientForm.patchValue({
            email: cliente.email,
            password: cliente.password, // cuidado: normalmente senhas não vêm do backend
            name: cliente.name,
            dataNascimento: new Date(cliente.birthdate),
            notificarPromocoes: cliente.notifyPromotion
          });
          console.log('Cliente encontrado:', cliente);
          this.titulo = 'Dados do Cliente';
        },
        error: (err) => {
          console.error('Erro ao buscar cliente:', err);
        }
      });
    }
  }

  onSubmit() {
    
    if (this.clientForm.valid) {
      const { email, password, nome, dataNascimento, notificarPromocoes } = this.clientForm.value;

      var cliente: Client ={
        name: nome,
        email: email,
        password: password,
        birthdate: new Date(dataNascimento),
        notifyPromotion: notificarPromocoes
      }
      
      console.log(cliente);

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
        this.crudService.create(cliente).subscribe({
          next: (response) => {
            console.log('Cliente cadastrado com sucesso:', response);
          },
          error: (error) => {
            console.error('Erro ao cadastrar cliente:', error);
          }
        });
      }
      
    }
  }

}
