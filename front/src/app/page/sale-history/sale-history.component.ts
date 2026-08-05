
import { Component, inject, OnInit, ChangeDetectionStrategy } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { RouterLink } from '@angular/router';
import { SaleService } from '../../service/sale.service';
import { Sale } from '../../entity/Sale';
import { LoginService } from '../../service/login.service';

@Component({
  selector: 'app-sale-history',
  imports: [
    MatTableModule,
    MatIconModule,
    MatCardModule,
    MatButtonModule,
    RouterLink
],
  templateUrl: './sale-history.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  styleUrl: './sale-history.component.css'
})
export class SaleHistoryComponent implements OnInit{
  private service = inject(SaleService);
  private loginService = inject(LoginService);

  saleHistory: Sale[] = [];
  displayedColumns = ['id', 'dateSale', 'price', 'actions'];

  ngOnInit(): void {
    this.service.getByClientId(this.loginService.getUserId()).subscribe({
      next: (data: Sale[])=>{
        this.saleHistory = data
      }
    })
  }

}
