import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatTableModule } from '@angular/material/table';

@Component({
  selector: 'app-dialog-confirmacao',
  imports: [
    CommonModule,
    MatDialogModule,
    MatTableModule,
    MatCheckboxModule,
    MatButtonModule,
    FormsModule
  ],
  templateUrl: './dialog-confirmacao.component.html',
  styleUrl: './dialog-confirmacao.component.css'
})
export class DialogConfirmacaoComponent {
  
    displayedColumns: string[] = ['select', 'name', 'price'];
    titulo: string = "Confirmar exclusão";
  
  
    constructor(
      private dialogRef: MatDialogRef<DialogConfirmacaoComponent>
    ) {
    }
  
    confirmar() {
      this.dialogRef.close(true);
    }

}
