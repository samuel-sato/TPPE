import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { Seller } from '../../entity/Seller';
import { Client } from '../../entity/Client';
import { SaleService } from '../../service/sale.service';
import { ActivatedRoute } from '@angular/router';
import { Product } from '../../entity/Product';
import { SellerService } from '../../service/seller.service';
import { ClienteService } from '../../service/cliente.service';
import { Sale } from '../../entity/Sale';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { provideNativeDateAdapter } from '@angular/material/core';
import { ProductSelectorDialogComponent } from '../../dialog/dialog-product/dialog-product.component';
import { ProductList } from '../../entity/ProductList';
import { MatDialog } from '@angular/material/dialog';
import { MatTableModule } from '@angular/material/table';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NotificationComponent } from '../../notification/notification.component';

@Component({
  selector: 'app-sale',
  imports: [
    ReactiveFormsModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatIconModule,
    CommonModule,
    MatSelectModule,
    FormsModule,
    MatDatepickerModule,
    MatTableModule
  ],
  templateUrl: './sale.component.html',
  styleUrl: './sale.component.css',
  providers: [provideNativeDateAdapter()]
})
export class SaleComponent implements OnInit{

  private _snackBar = inject(MatSnackBar);
  id: string | null = null;
  saleForm: FormGroup;
  titulo: string = 'Cadastro de Venda';
  sellers: Seller[] = [];
  clients: Client[] = [];
  displayedColumns: string[] = ['name', 'price', 'actions'];
  valorTotal: number = 0;

  sale: Sale ={
    dateSale: new Date(),
    price: 0,
    client: {} as Client,
    seller: {} as Seller,
    products: [] 
  }

  constructor(
    private fb: FormBuilder, 
    private crudService: SaleService,
    private route: ActivatedRoute,
    private clientService: ClienteService,
    private sellerService: SellerService,
    private dialog: MatDialog,
    private dialogConfirmacao: MatDialog
  ) 
  {
    this.saleForm = this.fb.group({
      dateSale: ['', Validators.required],
      price: [{value: '', disabled: true}, [Validators.required, Validators.min(0)]],
      client: ['', Validators.required],
      seller: ['', Validators.required]

    });

    this.clientService.getAll().subscribe({
      next: (data) => this.clients = data,
      error: (err) => console.error('Erro ao carregar clientes:', err)
    });
    
    this.sellerService.getAll().subscribe({
      next: (data) => this.sellers = data,
      error: (err) => console.error('Erro ao carregar vendedores:', err)
    });
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    
    if (this.id) {
      this.crudService.getById(this.id).subscribe({
        next: (sale: Sale) => {
          this.saleForm.patchValue({
            dateSale: sale.dateSale,
            price: sale.price,
            client: this.clients.find(c => c.id == sale.client.id),
            seller: this.sellers.find(s => s.id == sale.seller.id),
            products: sale.products
          }
        );
          
          this.titulo = 'Edição da Venda';
          this.sale = sale; 
        },
        error: (err) => {
          console.error('Erro ao buscar cliente:', err);
        }
      });
    }
  }

  onSubmit() {
      
      if(this.saleForm.valid) {
        this.saleForm.value;
  
        var sale: Sale ={
          dateSale: this.saleForm.value.dateSale,
          price: this.saleForm.value.price,
          client: this.saleForm.value.client,
          seller: this.saleForm.value.seller,
          products: []
        }
        
        sale.products = this.sale.products || [];
        sale.price = this.sale.products.reduce((total, produto) => total + produto.price, 0);

  
        if (this.id) {
          // Atualizar
          sale.id = parseInt(this.id, 10);
          this.crudService.update(sale).subscribe({
            next: (response) => {
              this.notificarSucesso();
            },
            error: (error) => {
              this.notificarErro();
            }
          });
        } 
        else {
          // Criar
          this.crudService.create(sale).subscribe({
            next: (response) => {
              this.notificarSucesso();
            },
            error: (error) => {
              this.notificarErro();
            }
          });
        }
        
      }
  }

  addProduct(): void {
    this.dialog.open(ProductSelectorDialogComponent).afterClosed().subscribe((selecionados: ProductList[]) => {
      if (selecionados && selecionados.length > 0) {
        const produtosExistentes = this.sale.products || [];
        const novosProdutos = selecionados.filter(p => !produtosExistentes.some(e => e.id === p.id));
        this.sale.products = [...produtosExistentes, ...novosProdutos];
        this.saleForm.patchValue({ products: this.sale.products });
        this.sale.price = this.sale.products.reduce((total, produto) => total + produto.price, 0);
          
        const valorTotal = selecionados.reduce((acumulador, produto) => acumulador + produto.price, 0);

        this.valorTotal += valorTotal;
        this.saleForm.get('price')?.setValue(this.valorTotal, { onlySelf: true, emitEvent: false });
      }
    });
  }

  deleteProductSale(id: number) {
    
    if (this.sale.products) {
      this.sale.products = this.sale.products.filter(product => product.id !== id);
      this.saleForm.patchValue({ products: this.sale.products });
    }
  }

  notificarSucesso(){
        this._snackBar.openFromComponent(NotificationComponent, {
          duration: 5 * 1000,
          data: {
            message: 'Venda cadastrado com sucesso!', // Sua mensagem de sucesso
            // Você pode adicionar um tipo, se quiser cores diferentes para sucesso/erro
            type: 'success'
          },
          panelClass: ['snackbar-success'] // Opcional: para aplicar estilos CSS
        });
      }
    
    notificarErro(){
      this._snackBar.openFromComponent(NotificationComponent, {
        duration: 5 * 1000,
        data: {
          message: 'Erro ao salvar venda. Tente novamente.', // Sua mensagem de erro
          type: 'error'
        },
        panelClass: ['snackbar-error'] // Opcional: para aplicar estilos CSS
      });
    }
}
