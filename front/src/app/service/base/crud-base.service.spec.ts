import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';

// CrudBaseService é abstrata: não pode ser instanciada diretamente.
// Testamos o comportamento herdado através de uma subclasse concreta real.
import { ClienteService } from '../cliente.service';
import { environment } from '../../../environments/environments';
import { Client } from '../../entity/Client';

describe('CrudBaseService (via ClienteService)', () => {
  let service: ClienteService;
  let httpMock: HttpTestingController;

  const url = `${environment.apiUrl}/clients`;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()]
    });

    service = TestBed.inject(ClienteService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => httpMock.verify());

  it('deve ser criado', () => {
    expect(service).toBeTruthy();
  });

  it('getAll() deve fazer GET para a URL do endpoint configurado', () => {
    const mockClients: Client[] = [];

    service.getAll().subscribe(clients => expect(clients).toEqual(mockClients));

    const req = httpMock.expectOne(url);
    expect(req.request.method).toBe('GET');
    req.flush(mockClients);
  });

  it('create() deve fazer POST enviando o item no corpo da requisição', () => {
    const novoCliente = {
      name: 'Ana',
      email: 'ana@teste.com',
      password: '123456',
      birthdate: new Date('2000-01-01'),
      notifyPromotion: false
    } as Client;

    service.create(novoCliente).subscribe();

    const req = httpMock.expectOne(url);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(novoCliente);
    req.flush(novoCliente);
  });

  it('delete() deve fazer DELETE para a URL do item pelo id', () => {
    service.delete(5).subscribe();

    const req = httpMock.expectOne(`${url}/5`);
    expect(req.request.method).toBe('DELETE');
    req.flush(null);
  });

  it('deve traduzir uma falha HTTP em um erro com mensagem amigável', () => {
    let capturedError: Error | undefined;

    service.getAll().subscribe({ error: (err) => (capturedError = err) });

    const req = httpMock.expectOne(url);
    req.flush({ message: 'Falha interna' }, { status: 500, statusText: 'Server Error' });

    expect(capturedError?.message).toContain('Erro no servidor (500)');
  });
});
