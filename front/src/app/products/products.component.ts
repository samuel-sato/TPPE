import { Component, OnInit } from '@angular/core';
import { ProductList } from '../entity/ProductList';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { RouterLink } from '@angular/router';
import { DepartmentService } from '../service/department.service';
import { ProductService } from '../service/product.service';

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

  products: ProductList[] = [];
  displayedColumns: string[] = ['name', 'price', 'actions'];
  
  constructor(private service: ProductService){
  }

  ngOnInit(): void {
    this.service.getAll().subscribe({
      next: (data: ProductList[])=>{
        this.products = data
      }
    })
  }

  deleteProduct(id: any){
    console.log(id);
  }
}
