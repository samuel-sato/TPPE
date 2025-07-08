import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { provideNativeDateAdapter } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { Client } from '../../entity/Client';
import { CrudBaseService } from '../../service/base/crud-base.service';
import { ActivatedRoute } from '@angular/router';
import { ClienteService } from '../../service/cliente.service';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NotificationComponent } from '../../notification/notification.component';

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

  private _snackBar = inject(MatSnackBar);
  clientForm: FormGroup;
  hide = true;
  exampleHeader: any;
  id: string | null = null;
  titulo: string = 'Cadastro de Cliente';
  
  constructor(
    private fb: FormBuilder, 
    private crudService: ClienteService,
    private route: ActivatedRoute,
    private dialogConfirmacao: MatDialog) {
    this.clientForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      dataNascimento: ['', Validators.required],
      notificarPromocoes: [false] 
    });
  }
  
  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');

    if (this.id) {
      this.crudService.getById(this.id).subscribe({
        next: (cliente: Client) => {
          this.clientForm.patchValue({
            name: cliente.name,
            email: cliente.email,
            password: cliente.password, 
            dataNascimento: new Date(cliente.birthdate),
            notificarPromocoes: cliente.notifyPromotion
          });
        
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
      const { email, password, name, dataNascimento, notificarPromocoes } = this.clientForm.value;

      var cliente: Client ={
        name: name,
        email: email,
        password: password,
        birthdate: new Date(dataNascimento),
        notifyPromotion: notificarPromocoes
      }
      

      if (this.id) {
        // Atualizar
        cliente.id = parseInt(this.id, 10);
        this.crudService.update(cliente).subscribe({
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
        this.crudService.create(cliente).subscribe({
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
