import { Component, Inject, Input } from '@angular/core';
import { MAT_SNACK_BAR_DATA } from '@angular/material/snack-bar';
import { NotificationData } from './NotificationData';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-notification',
  imports: [CommonModule],
  templateUrl: './notification.component.html',
  styleUrl: './notification.component.css'
})
export class NotificationComponent {

  // A propriedade 'message' agora é obtida dos dados injetados
  // O @Input() pode ser removido se a única forma de passar a mensagem for via MatSnackBar.data
  // No entanto, se você quiser que o componente de notificação também possa ser usado diretamente com [message]="...", mantenha o @Input()
  message: string;
  type: string = 'info'; // Valor padrão para tipo

  constructor(
    @Inject(MAT_SNACK_BAR_DATA) public data: NotificationData // Injete os dados
  ) {
    this.message = data.message;
    if (data.type) {
      this.type = data.type;
    }
  }
}
