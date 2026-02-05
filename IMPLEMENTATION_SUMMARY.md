# Agenda Project - Frontend implementation Summary

This document summarizes the core infrastructure and implementation details created for the `agenda-front` project. You can use this as a reference when re-creating the project.

## 1. API Configuration

### Base URL & Interceptors
The backend API is configured with a context path `/agenda`.

**Auth Interceptor (`src/app/core/interceptors/auth.interceptor.ts`)**:
Automatically adds the Bearer token to all requests.
```typescript
import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
    const authService = inject(AuthService);
    const token = authService.getToken();

    if (token) {
        const cloned = req.clone({
            headers: req.headers.set('Authorization', `Bearer ${token}`)
        });
        return next(cloned);
    }
    return next(req);
};
```

**Main Config (`src/main.ts`)**:
```typescript
provideHttpClient(withInterceptors([authInterceptor]))
```

## 2. Models (DTO Aligned)

### Usuario (`src/app/core/models/usuario.model.ts`)
```typescript
export interface LoginRequest {
    username: string;
    password: string;
}
export interface LoginResponse {
    access: string;
    refresh: string;
}
export interface RefreshDTO {
    refreshToken: string;
}
```

### Grupo (`src/app/core/models/grupo.model.ts`)
```typescript
import { AnotacionDto } from './anotacion.model';
export interface GrupoRequest {
    id?: number;
    nombre: string;
}
export interface GrupoResponse {
    id: number;
    nombre: string;
    anotaciones: AnotacionDto[];
}
```

### Clase (`src/app/core/models/clase.model.ts`)
```typescript
export interface ClaseRequest {
    id?: number;
    diaSemana: string;
    tramo: number;
    aula: string;
}
export interface ClaseResponse {
    id: number;
    idGrupo: number;
    grupo: string;
    diaSemana: string;
    tramo: number;
    aula: string;
}
```

### Anotacion (`src/app/core/models/anotacion.model.ts`)
```typescript
export interface AnotacionDto {
    id?: number;
    fecha: string; // ISO date string
    texto: string;
}
```

## 3. Services

### AuthService (`src/app/core/services/auth.service.ts`)
Handles login, registration, and role extraction from JWT.
```typescript
// apiUrl = 'http://localhost:8080/agenda/auth'
login(credentials: LoginRequest): Observable<LoginResponse> { ... }
isAdmin(): boolean { return this.getRole() === 'ADMIN' || this.getRole() === 'ROLE_ADMIN'; }
private extractRoleFromToken(token: string): string | null {
    const payload = JSON.parse(atob(token.split('.')[1]));
    return payload.role || payload.roles?.[0] || payload.authorities?.[0] || null;
}
```

### User Services (`/agenda/user/...`)
- `UserGrupoService`: CRUD for user's groups.
- `UserClaseService`: Methods like `getClasesByGrupo(idGrupo)`.
- `UserAnotacionService`: Methods like `getAnotacionesByGrupo(idGrupo)`.

### Admin Services (`/agenda/admin/...`)
- `AdminGrupoService`, `AdminClaseService`, `AdminAnotacionService`.
- All POST/PUT methods include `?idUsuario=X` query parameter as required by backend.

## 4. Guards

### AuthGuard & AdminGuard
- `authGuard`: Ensures user is logged in.
- `adminGuard`: Ensures user is logged in AND has role `ADMIN`.

## 5. Login Page Logic

**Role-based routing**:
```typescript
this.authService.login(this.credentials).subscribe({
  next: () => {
    if (this.authService.isAdmin()) {
      this.router.navigate(['/admin']);
    } else {
      this.router.navigate(['/user']);
    }
  }
});
```

## 6. Routes (`src/app/app.routes.ts`)

```typescript
export const routes: Routes = [
  { path: 'login', loadComponent: () => import('./pages/login/login.page').then(m => m.LoginPage) },
  { path: 'user', loadComponent: () => import('./home/home.page').then(m => m.HomePage), canActivate: [authGuard] },
  { path: 'admin', loadComponent: () => import('./home/home.page').then(m => m.HomePage), canActivate: [authGuard, adminGuard] },
  { path: '', redirectTo: 'login', pathMatch: 'full' },
];
```
