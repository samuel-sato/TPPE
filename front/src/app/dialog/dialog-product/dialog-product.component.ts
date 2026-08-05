import { Component, ChangeDetectionStrategy, inject } from '@angular/core';
import { MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatTableModule } from '@angular/material/table';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatButtonModule } from '@angular/material/button';

import { ProductService } from '../../service/product.service';
import { ProductList } from '../../entity/ProductList';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-product-selector-dialog',
  standalone: true,
  imports: [
    MatDialogModule,
    MatTableModule,
    MatCheckboxModule,
    MatButtonModule,
    FormsModule
],
  changeDetection: ChangeDetectionStrategy.OnPush,
  template: `
    <h2 mat-dialog-title>Selecionar Produtos</h2>
    <mat-dialog-content>
      <table mat-table [dataSource]="products" class="mat-elevation-z8">

        <ng-container matColumnDef="select">
          <th mat-header-cell *matHeaderCellDef> Selecionar </th>
          <td mat-cell *matCellDef="let element">
            <mat-checkbox [(ngModel)]="selection[element.id]"></mat-checkbox>
          </td>
        </ng-container>

        <ng-container matColumnDef="name">
          <th mat-header-cell *matHeaderCellDef> Nome </th>
          <td mat-cell *matCellDef="let element"> {{element.name}} </td>
        </ng-container>

        <ng-container matColumnDef="price">
          <th mat-header-cell *matHeaderCellDef> Preço </th>
          <td mat-cell *matCellDef="let element"> {{element.price}} </td>
        </ng-container>

        <tr mat-header-row *matHeaderRowDef="displayedColumns"></tr>
        <tr mat-row *matRowDef="let row; columns: displayedColumns;"></tr>
      </table>
    </mat-dialog-content>

    <mat-dialog-actions align="end">
      <button mat-button mat-dialog-close>Cancelar</button>
      <button mat-raised-button color="primary" (click)="confirmar()">Confirmar</button>
    </mat-dialog-actions>
  `
})
export class ProductSelectorDialogComponent {
  private dialogRef = inject<MatDialogRef<ProductSelectorDialogComponent>>(MatDialogRef);
  private productService = inject(ProductService);

  products: ProductList[] = [];
  displayedColumns = ['select', 'name', 'price'];
  selection: Record<number, boolean> = {};

  constructor() {
    this.productService.getAll().subscribe({
      next: (data) => this.products = data,
      error: (err) => console.error('Erro ao carregar produtos:', err)
    });
  }

  confirmar() {
    const selecionados = this.products.filter(p => this.selection[p.id!]);
    this.dialogRef.close(selecionados);
  }
}
