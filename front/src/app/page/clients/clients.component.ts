
import { Component, inject, OnInit, ChangeDetectionStrategy } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { RouterLink } from '@angular/router';
import { ClienteService } from '../../service/cliente.service';
import { ClientList } from '../../entity/ClientList';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { DialogConfirmacaoComponent } from '../../dialog/dialog-confirmacao/dialog-confirmacao.component';
import { NotificationComponent } from '../../notification/notification.component';

@Component({
  selector: 'app-clients',
  imports: [
    MatTableModule,
    MatIconModule,
    MatCardModule,
    MatButtonModule,
    RouterLink
],
  templateUrl: './clients.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  styleUrl: './clients.component.css'
})
export class ClientsComponent implements OnInit {
  private service = inject(ClienteService);
  private dialogConfirmacao = inject(MatDialog);


  private _snackBar = inject(MatSnackBar);

  clients: ClientList[] = [];
  displayedColumns = ['name', 'actions'];
  
    ngOnInit(): void {
      this.service.getAll().subscribe({
        next: (data: ClientList[])=>{
          this.clients = data
        }
      })
    }
  
    deleteClient(id: number){
      this.dialogConfirmacao.open(DialogConfirmacaoComponent).afterClosed().subscribe((confirmacao: boolean) => {
            if (confirmacao) {
      
              this.service.delete(id).subscribe({
                next: () => {
                  this.clients = this.clients.filter(client => client.id !== id);
                  this._snackBar.openFromComponent(NotificationComponent, {
                    duration: 5 * 1000,
                    data: {
                      message: 'Cliente excluído com sucesso!', // Sua mensagem de sucesso
                      // Você pode adicionar um tipo, se quiser cores diferentes para sucesso/erro
                      type: 'success'
                    },
                    panelClass: ['snackbar-success'] // Opcional: para aplicar estilos CSS
                  });
                },
                error: () => {
                  this._snackBar.openFromComponent(NotificationComponent, {
                    duration: 5 * 1000,
                    data: {
                      message: 'Erro ao excluir cliente. Tente novamente.', // Sua mensagem de erro
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
