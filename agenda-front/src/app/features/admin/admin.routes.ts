import { Routes } from '@angular/router';

export const ADMIN_ROUTES: Routes = [
    {
        // El "Home" del Admin: Un Dashboard de gestión
        path: '',
        loadComponent: () => import('./pages/admin-dashboard/admin-dashboard').then(m => m.AdminDashboard)
    },
    {
        path: 'usuarios',
        loadComponent: () => import('./pages/user-management/user-management').then(m => m.UserManagement)
    },
    {
        path: 'grupos', // CRUD Global de grupos
        loadComponent: () => import('./pages/admin-grupos/admin-grupos').then(m => m.AdminGrupos)
    },
    {
        path: 'clases', // CRUD Global de clases
        loadComponent: () => import('./pages/admin-clases/admin-clases').then(m => m.AdminClases)
    },
    {
        path: 'anotaciones', // CRUD Global de clases
        loadComponent: () => import('./pages/admin-anotaciones/admin-anotaciones').then(m => m.AdminAnotaciones)
    }
];