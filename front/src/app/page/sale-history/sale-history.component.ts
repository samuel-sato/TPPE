import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { RouterLink } from '@angular/router';
import { SaleList } from '../../entity/SaleList';
import { SaleService } from '../../service/sale.service';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Sale } from '../../entity/Sale';
import { LoginService } from '../../service/login.service';

@Component({
  selector: 'app-sale-history',
  imports: [
    MatTableModule, 
    CommonModule,
    MatIconModule,
    MatCardModule,
    MatButtonModule,
    RouterLink
  ],
  templateUrl: './sale-history.component.html',
  styleUrl: './sale-history.component.css'
})
export class SaleHistoryComponent implements OnInit{

  private _snackBar = inject(MatSnackBar);
  saleHistory: Sale[] = [];
  displayedColumns: string[] = ['id', 'dateSale', 'price', 'actions'];

  constructor(
    private service: SaleService, 
    private dialogConfirmacao: MatDialog,
    private loginService: LoginService
  ){
  }

  ngOnInit(): void {
    this.service.getByClientId(this.loginService.getUserId()).subscribe({
      next: (data: Sale[])=>{
        this.saleHistory = data
      }
    })
  }

}
