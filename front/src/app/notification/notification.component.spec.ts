import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MAT_SNACK_BAR_DATA } from '@angular/material/snack-bar';

import { NotificationComponent } from './notification.component';
import { NotificationData } from './NotificationData';

describe('NotificationComponent', () => {
  let component: NotificationComponent;
  let fixture: ComponentFixture<NotificationComponent>;

  async function createComponent(data: NotificationData) {
    await TestBed.configureTestingModule({
      imports: [NotificationComponent],
      providers: [{ provide: MAT_SNACK_BAR_DATA, useValue: data }]
    }).compileComponents();

    fixture = TestBed.createComponent(NotificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }

  it('should create', async () => {
    await createComponent({ message: 'Operação realizada com sucesso!', type: 'success' });
    expect(component).toBeTruthy();
  });

  it('deve expor a mensagem e o tipo recebidos via MAT_SNACK_BAR_DATA', async () => {
    await createComponent({ message: 'Operação realizada com sucesso!', type: 'success' });
    expect(component.message).toBe('Operação realizada com sucesso!');
    expect(component.type).toBe('success');
  });

  it('deve usar o tipo padrão "info" quando nenhum tipo é informado', async () => {
    await createComponent({ message: 'Aviso qualquer' });
    expect(component.type).toBe('info');
  });
});
