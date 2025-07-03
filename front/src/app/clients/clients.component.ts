import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { RouterLink } from '@angular/router';
import { ClienteService } from '../service/cliente.service';
import { ClientList } from '../entity/ClientList';

@Component({
  selector: 'app-clients',
  imports: [
    MatTableModule, 
    CommonModule,
    MatIconModule,
    MatCardModule,
    MatButtonModule,
    RouterLink
  ],
  templateUrl: './clients.component.html',
  styleUrl: './clients.component.css'
})
export class ClientsComponent implements OnInit {


  clients: ClientList[] = [];
  displayedColumns: string[] = ['name', 'actions'];
  
    constructor(private service: ClienteService){
    }
  
    ngOnInit(): void {
      this.service.getAll().subscribe({
        next: (data: ClientList[])=>{
          this.clients = data
        }
      })
    }
  
    deleteClient(id: any){
      console.log(id);
    }
}
