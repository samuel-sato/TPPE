import { Routes } from '@angular/router';
import { DepartmentsComponent } from './departments/departments.component';
import { LoginComponent } from './login/login.component';
import { ClienteComponent } from './cliente/cliente.component';
import { DepartmentComponent } from './department/department.component';
import { SellerComponent } from './seller/seller.component';
import { SellersComponent } from './sellers/sellers.component';

export const routes: Routes = [
    {path: 'login', component: LoginComponent},
    {path: 'cliente', component: ClienteComponent},
    {path: 'cliente/:id', component: ClienteComponent },
    {path: 'departments', component: DepartmentsComponent},
    {path: 'department', component: DepartmentComponent},
    {path: 'department/:id', component: DepartmentComponent},
    {path: 'sellers', component: SellersComponent},
    {path: 'seller', component: SellerComponent},
    {path: 'seller/:id', component: SellerComponent}
];

