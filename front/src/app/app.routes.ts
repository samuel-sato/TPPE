import { Routes } from '@angular/router';
import { DepartmentsComponent } from './page/departments/departments.component';
import { LoginComponent } from './page/login/login.component';
import { ClientComponent } from './page/client/cliente.component';
import { DepartmentComponent } from './page/department/department.component';
import { SellerComponent } from './page/seller/seller.component';
import { SellersComponent } from './page/sellers/sellers.component';
import { ClientsComponent } from './page/clients/clients.component';
import { ProductsComponent } from './page/products/products.component';
import { ProductComponent } from './page/product/product.component';
import { SalesComponent } from './page/sales/sales.component';
import { SaleComponent } from './page/sale/sale.component';

export const routes: Routes = [
    {path: 'login', component: LoginComponent},
    {path: 'clients', component: ClientsComponent},
    {path: 'client', component: ClientComponent},
    {path: 'client/:id', component: ClientComponent },
    {path: 'departments', component: DepartmentsComponent},
    {path: 'department', component: DepartmentComponent},
    {path: 'department/:id', component: DepartmentComponent},
    {path: 'sellers', component: SellersComponent},
    {path: 'seller', component: SellerComponent},
    {path: 'seller/:id', component: SellerComponent},
    {path: 'products', component: ProductsComponent},
    {path: 'product', component: ProductComponent},
    {path: 'product/:id', component: ProductComponent},
    {path: 'sales', component: SalesComponent},
    {path: 'sale', component: SaleComponent},
    {path: 'sale/:id', component: SaleComponent}
];

