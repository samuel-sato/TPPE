import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { RouterLink } from '@angular/router';
import { SellerList } from '../../entity/SellerList';
import { SellerService } from '../../service/seller.service';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { DialogConfirmacaoComponent } from '../../dialog/dialog-confirmacao/dialog-confirmacao.component';
import { NotificationComponent } from '../../notification/notification.component';

@Component({
  selector: 'app-sellers',
  imports: [
    MatTableModule, 
    CommonModule,
    MatIconModule,
    MatCardModule,
    MatButtonModule,
    RouterLink
  ],
  templateUrl: './sellers.component.html',
  styleUrl: './sellers.component.css'
})
export class SellersComponent implements OnInit {

  private _snackBar = inject(MatSnackBar);
  sellers: SellerList[] = [];
  displayedColumns: string[] = ['name', 'actions'];
  
  constructor(private service: SellerService, private dialogConfirmacao: MatDialog){
  }

  ngOnInit(): void {
    this.service.getAll().subscribe({
      next: (data: SellerList[])=>{
        this.sellers = data
      }
    })
  }

  deleteSeller(id: any){
    this.dialogConfirmacao.open(DialogConfirmacaoComponent).afterClosed().subscribe((confirmacao: boolean) => {
          if (confirmacao) {
    
            this.service.delete(id).subscribe({
              next: () => {
                this.sellers = this.sellers.filter(seller => seller.id !== id);
                this._snackBar.openFromComponent(NotificationComponent, {
                  duration: 5 * 1000,
                  data: {
                    message: 'Vendedor excluída com sucesso!', // Sua mensagem de sucesso
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
                    message: 'Erro ao excluir vendedor. Tente novamente.', // Sua mensagem de erro
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
