import { Routes } from '@angular/router';
import { DepartmentsComponent } from './departments/departments.component';
import { LoginComponent } from './login/login.component';
import { ClienteComponent } from './cliente/cliente.component';

export const routes: Routes = [
    {path: 'departments', component: DepartmentsComponent},
    {path: 'login', component: LoginComponent},
    {path: 'cliente', component: ClienteComponent},
    { path: 'cliente/:id', component: ClienteComponent }
];

