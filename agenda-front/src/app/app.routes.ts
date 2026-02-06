import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { adminGuard } from './core/guards/admin.guard';

export const routes: Routes = [
    {
        path: 'auth',
        loadChildren: () => import('./features/auth/auth.routes').then(m => m.AUTH_ROUTES)
    },
    {
        path: '',
        canActivate: [authGuard],
        loadComponent: () => import('./core/layout/main-layout/main-layout').then(m => m.MainLayout),
        children: [
            {
                path: 'dashboard',
                loadComponent: () => import('./features/dashboard/pages/user-dashboard/user-dashboard').then(m => m.UserDashboard)
            },
            {
                path: 'mis-grupos',
                loadComponent: () => import('./features/grupos/pages/grupos-list/grupos-list').then(m => m.GruposList)
            },
            // NUEVA RUTA DE CLASES (Enfocada a su horario/agenda)
            {
                path: 'mi-agenda',
                loadComponent: () => import('./features/clases/pages/clases-list/clases-list').then(m => m.ClasesList)
            },
            {
                path: 'mis-anotaciones',
                loadComponent: () => import('./features/anotaciones/pages/anotaciones-list/anotaciones-list').then(m => m.AnotacionesList)
            },

            // ZONA ADMIN (Gestión Global)
            {
                path: 'admin',
                canActivate: [adminGuard],
                loadChildren: () => import('./features/admin/admin.routes').then(m => m.ADMIN_ROUTES)
            },

            { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
        ]
    },
    { path: '**', redirectTo: 'auth/login' }
];
