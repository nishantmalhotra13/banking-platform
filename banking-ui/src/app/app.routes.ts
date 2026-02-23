import { Routes } from '@angular/router';
import { authGuard, adminGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'login', loadComponent: () => import('./features/auth/login.component').then(m => m.LoginComponent) },
  { path: 'register', loadComponent: () => import('./features/auth/register.component').then(m => m.RegisterComponent) },
  { path: 'dashboard', loadComponent: () => import('./features/dashboard/dashboard.component').then(m => m.DashboardComponent), canActivate: [authGuard] },
  { path: 'search', loadComponent: () => import('./features/search/search.component').then(m => m.SearchComponent), canActivate: [authGuard] },
  { path: 'accounts/:accountNumber', loadComponent: () => import('./features/accounts/account-detail.component').then(m => m.AccountDetailComponent), canActivate: [authGuard] },
  { path: 'cards/validate', loadComponent: () => import('./features/cards/card-validate.component').then(m => m.CardValidateComponent), canActivate: [authGuard] },
  { path: 'admin/users', loadComponent: () => import('./features/admin/user-management.component').then(m => m.UserManagementComponent), canActivate: [adminGuard] },
  { path: '**', redirectTo: '/dashboard' }
];
