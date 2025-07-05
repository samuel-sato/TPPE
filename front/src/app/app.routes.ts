import { Routes } from '@angular/router';
import { DepartmentsComponent } from './departments/departments.component';
import { LoginComponent } from './login/login.component';
import { ClientComponent } from './client/cliente.component';
import { DepartmentComponent } from './department/department.component';
import { SellerComponent } from './seller/seller.component';
import { SellersComponent } from './sellers/sellers.component';
import { ClientsComponent } from './clients/clients.component';
import { ProductsComponent } from './products/products.component';
import { ProductComponent } from './product/product.component';

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
    {path: 'product/:id', component: ProductComponent}
];

