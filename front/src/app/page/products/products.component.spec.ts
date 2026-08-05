import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { of } from 'rxjs';

import { ProductsComponent } from './products.component';
import { ProductService } from '../../service/product.service';
import { Product } from '../../entity/Product';

describe('ProductsComponent', () => {
  let component: ProductsComponent;
  let fixture: ComponentFixture<ProductsComponent>;
  let productService: jasmine.SpyObj<ProductService>;

  beforeEach(async () => {
    productService = jasmine.createSpyObj('ProductService', ['getAll', 'delete']);
    productService.getAll.and.returnValue(of([]));

    await TestBed.configureTestingModule({
      imports: [ProductsComponent],
      providers: [
        provideRouter([]),
        { provide: ProductService, useValue: productService }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('deve carregar a lista de produtos no ngOnInit', () => {
    expect(productService.getAll).toHaveBeenCalled();
    expect(component.products).toEqual([]);
  });

  it('deve popular products com o retorno do serviço', () => {
    const mockProducts: Product[] = [
      { id: 1, name: 'Caneta', price: 2.5, description: 'Caneta azul', department: 'Papelaria', idDepartment: 1 }
    ];
    productService.getAll.and.returnValue(of(mockProducts));

    component.ngOnInit();

    expect(component.products).toEqual(mockProducts);
  });
});
