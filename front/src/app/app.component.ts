import { AfterViewInit, Component, OnInit } from '@angular/core';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { LoginService } from './service/login.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet,
    RouterLink,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatMenuModule,
    CommonModule
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements AfterViewInit {
  
  title = 'E-Loja';
  role: number = -1;
  userId: number = -1;

  routerPersonData: string = '';

  constructor(
    private service: LoginService,
    private route: Router
  ){}
  
  ngAfterViewInit(): void {
    throw new Error('Method not implemented.');
  }

  ngOnInit(): void {
    this.role = Number.parseInt(this.service.getUserProfile());
    this.userId = Number.parseInt(this.service.getUserId());

    if(this.role == 2){
      this.routerPersonData = `/client/${this.userId}`;
    }
    if(this.role == 3){
      this.routerPersonData = `/seller/${this.userId}`;
    }
  }

  logout(): void {
    this.service.logout();
    this.route.navigate(['/login']);
  }
}
