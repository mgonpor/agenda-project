import { inject, Injectable, computed } from '@angular/core';
import { AuthService } from '../../features/auth/services/auth.service';

export interface NavLink {
    label: string;
    route: string;
    icon: string;
    roles?: string[]; // Si no hay roles, es público (ej. Dashboard)
}

@Injectable({
    providedIn: 'root',
})
export class NavigationService {
    private authService = inject(AuthService);

    // Definición centralizada de rutas
    private allLinks: NavLink[] = [
        { label: 'Dashboard', route: '/dashboard', icon: 'dashboard' },
        { label: 'Grupos', route: '/grupos', icon: 'groups' },
        { label: 'Admin Panel', route: '/admin', icon: 'admin_panel_settings', roles: ['ADMIN'] },
    ];

    // Signal computado que filtra los links según el rol actual
    visibleLinks = computed(() => {
        const { rol, isAuthenticated } = this.authService.authStatus();

        if (!isAuthenticated) return [];

        return this.allLinks.filter(link => {
            if (!link.roles) return true; // Públicos para logueados
            return link.roles.includes(rol || '');
        });
    });
}
