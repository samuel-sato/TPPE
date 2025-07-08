import { Component, inject, OnInit } from '@angular/core';
import { ProductList } from '../../entity/ProductList';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { RouterLink } from '@angular/router';
import { DepartmentService } from '../../service/department.service';
import { ProductService } from '../../service/product.service';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { DialogConfirmacaoComponent } from '../../dialog/dialog-confirmacao/dialog-confirmacao.component';
import { NotificationComponent } from '../../notification/notification.component';

@Component({
  selector: 'app-products',
  imports: [
    MatTableModule, 
    CommonModule,
    MatIconModule,
    MatCardModule,
    MatButtonModule,
    RouterLink
  ],
  templateUrl: './products.component.html',
  styleUrl: './products.component.css'
})
export class ProductsComponent implements OnInit {

  private _snackBar = inject(MatSnackBar);
  products: ProductList[] = [];
  displayedColumns: string[] = ['name', 'price', 'actions'];
  
  constructor(private service: ProductService, private dialogConfirmacao: MatDialog){
  }

  ngOnInit(): void {
    this.service.getAll().subscribe({
      next: (data: ProductList[])=>{
        this.products = data
      }
    })
  }

  deleteProduct(id: any){
    this.dialogConfirmacao.open(DialogConfirmacaoComponent).afterClosed().subscribe((confirmacao: boolean) => {
          if (confirmacao) {
    
            this.service.delete(id).subscribe({
              next: () => {
                this.products = this.products.filter(product => product.id !== id);
                this._snackBar.openFromComponent(NotificationComponent, {
                  duration: 5 * 1000,
                  data: {
                    message: 'Produto excluída com sucesso!', // Sua mensagem de sucesso
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
                    message: 'Erro ao excluir produto. Tente novamente.', // Sua mensagem de erro
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
