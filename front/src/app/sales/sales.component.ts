import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { RouterLink } from '@angular/router';
import { SaleService } from '../service/sale.service';
import { Sale } from '../entity/Sale';
import { MatDialog } from '@angular/material/dialog';
import { DialogConfirmacaoComponent } from '../dialog/dialog-confirmacao/dialog-confirmacao.component';

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

  sales: Sale[] = [];
  displayedColumns: string[] = ['dateSale', 'client', 'seller', 'price', 'actions'];
    
  constructor(private service: SaleService, private dialogConfirmacao: MatDialog) {
    // Inicialização do componente
  }

  ngOnInit(): void {
    this.service.getAll().subscribe({
      next: (data: Sale[])=>{
        this.sales = data
        console.log(this.sales);
      }
    })
  }

  deleteSale(id: any){
    console.log(id);
    this.dialogConfirmacao.open(DialogConfirmacaoComponent).afterClosed().subscribe((confirmacao: boolean) => {
      if (confirmacao) {
        console.log('Excluir: ', this.sales.find(sale => sale.id === id));
        this.service.delete(id).subscribe({
          next: () => {
            this.sales = this.sales.filter(sale => sale.id !== id);
          },
          error: (err) => {
            console.error('Erro ao excluir venda:', err);
          }
        });
      }
    });
  }     
}
