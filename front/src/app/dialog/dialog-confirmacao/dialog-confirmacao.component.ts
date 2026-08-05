
import { Component, ChangeDetectionStrategy, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatTableModule } from '@angular/material/table';

@Component({
  selector: 'app-dialog-confirmacao',
  imports: [
    MatDialogModule,
    MatTableModule,
    MatCheckboxModule,
    MatButtonModule,
    FormsModule
],
  templateUrl: './dialog-confirmacao.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  styleUrl: './dialog-confirmacao.component.css'
})
export class DialogConfirmacaoComponent {
    private dialogRef = inject<MatDialogRef<DialogConfirmacaoComponent>>(MatDialogRef) ?? inject(MatDialogRef<DialogConfirmacaoComponent>);

  
    displayedColumns = ['select', 'name', 'price'];
    titulo = "Confirmar exclusão";
  
    confirmar() {
      this.dialogRef.close(true);
    }

}
