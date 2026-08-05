import { Component, ChangeDetectionStrategy, OnInit, inject } from '@angular/core';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { LoginService } from './service/login.service';


@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RouterLink, MatToolbarModule, MatButtonModule, MatIconModule, MatMenuModule],
  templateUrl: './app.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {

  private route = inject(Router);
  private service = inject(LoginService);

  title = 'E-Loja';
  role = -1;
  userId = -1;
  routerPersonData = '';

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
