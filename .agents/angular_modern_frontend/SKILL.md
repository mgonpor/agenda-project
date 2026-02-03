---
name: angular_modern_frontend
description: Skill para el desarrollo de front-ends modernos con Angular 18+, Signals, consumo de API REST securizado y diseño UI/UX premium.
---
# Angular Modern Frontend Expert

Esta habilidad te convierte en un experto en la creación de aplicaciones Angular modernas y profesionales. Sigue estas directrices para garantizar un resultado de alta calidad.

## 1. Arquitectura y Estructura
- **Angular 18+**: Utiliza siempre las últimas características (Standalone Components, Signals, Control Flow syntax `@if`, `@for`).
- **Estructura Core**: Organiza el código en `src/app/core` para singleton services, interceptores y modelos globales.
- **Features**: Divide la lógica de negocio por módulos funcionales o carpetas de "features".

## 2. Consumo de API REST y Seguridad
- **Servicios Genéricos**: Implementa servicios que utilicen `HttpClient` de forma eficiente.
- **Interceptores**: Configura `HttpInterceptorFn` para adjuntar tokens JWT automáticamente y manejar errores 401/403 de forma global.
- **Guards**: Protege las rutas utilizando `canActivateFn` basándote en el estado de autenticación.
- **Signals para Estado**: Utiliza `signal` y `computed` para manejar el estado de la aplicación de forma reactiva en lugar de BehaviorSubjects cuando sea posible.

## 3. Diseño UI/UX y Estética
- **Principios de Diseño**: Crea interfaces que causen un efecto "WOW".
- **CSS Avanzado**: Prioriza Vanilla CSS con variables (Custom Properties) para temas dinámicos.
- **Estética Premium**:
    - **Gradientes Suaves**: Usa fondos con gradientes modernos.
    - **Vidrio (Glassmorphism)**: Aplica `backdrop-filter: blur()` y bordes semitransparentes.
    - **Micro-animaciones**: Añade transiciones suaves en hovers y cambios de estado.
    - **Tipografía**: Importa fuentes modernas como 'Inter' o 'Outfit' desde Google Fonts.
- **Responsividad**: Diseña con enfoque mobile-first y usa CSS Grid/Flexbox para layouts dinámicos.

## 4. Desarrollo Paso a Paso
1. **Configuración Inicial**: `npx -y @angular/cli@latest new app-name --style=scss --routing`.
2. **Sistema de Diseño**: Define `index.css` con la paleta de colores y variables globales.
3. **Servicios Core**: Implementa `AuthService` y `ApiService`.
4. **Componentes Layout**: Crea `Header`, `Sidebar` y `Footer` con estilos premium.
5. **Vistas**: Desarrolla las pantallas principales consumiendo los servicios y aplicando los estilos definidos.

## 5. Validación
- Verifica que no haya fugas de memoria (limpieza de suscripciones si usas Observables).
- Asegura que los formularios sean reactivos y tengan validaciones visuales claras.
- Comprueba que la UI sea consistente en diferentes tamaños de pantalla.
