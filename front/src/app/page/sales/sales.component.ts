import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { RouterLink } from '@angular/router';
import { SaleService } from '../../service/sale.service';
import { Sale } from '../../entity/Sale';
import { MatDialog } from '@angular/material/dialog';
import { DialogConfirmacaoComponent } from '../../dialog/dialog-confirmacao/dialog-confirmacao.component';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NotificationComponent } from '../../notification/notification.component';

@Component({
  selector: 'app-sales',
  imports: [
    MatTableModule, 
    CommonModule,
    MatIconModule,
    MatCardModule,
    MatButtonModule,
    RouterLink
  ],
  templateUrl: './sales.component.html',
  styleUrl: './sales.component.css'
})
export class SalesComponent {

  private _snackBar = inject(MatSnackBar);
  sales: Sale[] = [];
  displayedColumns: string[] = ['dateSale', 'client', 'seller', 'price', 'actions'];
    
  constructor(private service: SaleService, private dialogConfirmacao: MatDialog) {
    
  }

  ngOnInit(): void {
    this.service.getAll().subscribe({
      next: (data: Sale[])=>{
        this.sales = data;
      }
    })
  }

  deleteSale(id: any){
    
    this.dialogConfirmacao.open(DialogConfirmacaoComponent).afterClosed().subscribe((confirmacao: boolean) => {
      if (confirmacao) {

        this.service.delete(id).subscribe({
          next: () => {
            this.sales = this.sales.filter(sale => sale.id !== id);
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
