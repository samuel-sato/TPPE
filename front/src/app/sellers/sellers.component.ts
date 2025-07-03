import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { RouterLink } from '@angular/router';
import { SellerList } from '../entity/SellerList';
import { SellerService } from '../service/seller.service';

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

  sellers: SellerList[] = [];
    displayedColumns: string[] = ['name', 'actions'];
  
    constructor(private service: SellerService){
    }
  
    ngOnInit(): void {
      this.service.getAll().subscribe({
        next: (data: SellerList[])=>{
          this.sellers = data
        }
      })
    }
  
    deleteSeller(id: any){
      console.log(id);
    }
}
