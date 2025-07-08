import { Component, inject, OnInit } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { DepartmentService } from '../../service/department.service';
import { DepartmentList } from '../../entity/DepartmentList';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatButton, MatButtonModule } from '@angular/material/button';
import { RouterLink } from '@angular/router';
import { NotificationComponent } from '../../notification/notification.component';
import { DialogConfirmacaoComponent } from '../../dialog/dialog-confirmacao/dialog-confirmacao.component';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-departments',
  imports: [
    MatTableModule, 
    CommonModule,
    MatIconModule,
    MatCardModule,
    MatButtonModule,
    RouterLink
  ],
  templateUrl: './departments.component.html',
  styleUrl: './departments.component.css'
})
export class DepartmentsComponent implements OnInit{

  private _snackBar = inject(MatSnackBar);
  departments: DepartmentList[] = [];
  displayedColumns: string[] = ['name', 'description', 'actions'];

  constructor(private service: DepartmentService, private dialogConfirmacao: MatDialog){
  }

  ngOnInit(): void {
    this.service.getAll().subscribe({
      next: (data: DepartmentList[])=>{
        this.departments = data
      }
    })
  }

  deleteDepartment(id: any){
    this.dialogConfirmacao.open(DialogConfirmacaoComponent).afterClosed().subscribe((confirmacao: boolean) => {
          if (confirmacao) {
    
            this.service.delete(id).subscribe({
              next: () => {
                this.departments = this.departments.filter(department => department.id !== id);
                this._snackBar.openFromComponent(NotificationComponent, {
                  duration: 5 * 1000,
                  data: {
                    message: 'Venda excluída com sucesso!', // Sua mensagem de sucesso
                    // Você pode adicionar um tipo, se quiser cores diferentes para sucesso/erro
                    type: 'success'
                  },
                  panelClass: ['snackbar-success'] // Opcional: para aplicar estilos CSS
                });
              },
              error: (err) => {
                this._snackBar.openFromComponent(NotificationComponent, {
                  duration: 5 * 1000,
                  data: {
                    message: 'Erro ao excluir venda. Tente novamente.', // Sua mensagem de erro
                    type: 'error'
                  },
                  panelClass: ['snackbar-error'] // Opcional: para aplicar estilos CSS
                });
              }
            });
          }
        });
  }

}
