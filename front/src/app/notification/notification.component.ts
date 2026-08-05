import { Component, ChangeDetectionStrategy, inject } from '@angular/core';
import { MAT_SNACK_BAR_DATA } from '@angular/material/snack-bar';
import { NotificationData } from './NotificationData';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-notification',
  imports: [CommonModule],
  templateUrl: './notification.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  styleUrl: './notification.component.css'
})
export class NotificationComponent {
  data = inject<NotificationData // Injete os dados
>(MAT_SNACK_BAR_DATA);

  message: string;
  type = 'info'; // Valor padrão para tipo

  constructor() {
    const data = this.data;

    this.message = data.message;
    if (data.type) {
      this.type = data.type;
    }
  }
}
