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
import { HomeComponent } from './page/home/home.component';
import { AuthGuard } from './guards/auth.guard';
import { SaleHistoryComponent } from './page/sale-history/sale-history.component';

export const routes: Routes = [
    {path: 'home', component: HomeComponent, canActivate: [AuthGuard]},
    {path: 'login', component: LoginComponent},
    {path: 'clients', component: ClientsComponent, canActivate: [AuthGuard]},
    {path: 'client', component: ClientComponent},
    {path: 'client/:id', component: ClientComponent , canActivate: [AuthGuard]},
    {path: 'departments', component: DepartmentsComponent, canActivate: [AuthGuard]},
    {path: 'department', component: DepartmentComponent, canActivate: [AuthGuard]},
    {path: 'department/:id', component: DepartmentComponent, canActivate: [AuthGuard]},
    {path: 'sellers', component: SellersComponent, canActivate: [AuthGuard]},
    {path: 'seller', component: SellerComponent, canActivate: [AuthGuard]},
    {path: 'seller/:id', component: SellerComponent, canActivate: [AuthGuard]},
    {path: 'products', component: ProductsComponent, canActivate: [AuthGuard]},
    {path: 'product', component: ProductComponent, canActivate: [AuthGuard]},
    {path: 'product/:id', component: ProductComponent, canActivate: [AuthGuard]},
    {path: 'sales', component: SalesComponent, canActivate: [AuthGuard]},
    {path: 'sale', component: SaleComponent, canActivate: [AuthGuard]},
    {path: 'sale/:id', component: SaleComponent, canActivate: [AuthGuard]},
    {path: 'salehistory', component: SaleHistoryComponent, canActivate: [AuthGuard]},
    {path: '', redirectTo: '/home', pathMatch: 'full' },
    { path: '**', redirectTo: '/home' }
];

