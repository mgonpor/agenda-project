# Roadmap to Restart the Ionic/Angular Project

Follow these steps to ensure a clean environment and a successful project restart.

## 1. Environment Preparation

Your current environment (Node v22.20.0, NPM 11.7.0) is very recent. If you encounter "Invalid Version" or dependency conflicts again, follow these cleanup steps:

1.  **Clear NPM Cache**:
    ```bash
    npm cache clean --force
    ```
2.  **Global Ionic CLI (Optional but recommended)**:
    Ensure you have the latest Ionic CLI:
    ```bash
    npm install -g @ionic/cli
    ```

## 2. Initialize New Project

1.  **Navigate to your workspace**:
    ```bash
    cd c:\Users\mgonpor116\Desktop\agenda-project
    ```
2.  **Delete the old folder** (if you haven't already):
    ```bash
    rm -rf agenda-front
    ```
3.  **Create a fresh Ionic project**:
    We recommend using the `blank` or `tabs` starter with Angular (Standalone components are the modern standard).
    ```bash
    ionic start agenda-front tabs --type=angular --standalone --navigator=npm
    ```
    *Note: If prompted about "Ionic Account", you can choose "N".*

## 3. Configure API & Core Infrastructure

Once the project is created, apply the implementations detailed in `IMPLEMENTATION_SUMMARY.md`:

1.  **Core Directory Structure**:
    Create the necessary folders:
    ```bash
    cd agenda-front
    mkdir -p src/app/core/models src/app/core/services src/app/core/interceptors src/app/core/guards
    ```
2.  **Models**: Copy the interfaces for `Usuario`, `Grupo`, `Clase`, and `Anotacion`.
3.  **Services**: Implement `AuthService` and the specialized User/Admin services.
4.  **Interceptors**: Register the `authInterceptor` in `src/app/core/interceptors`.
5.  **Main Configuration**: Update `src/main.ts` to include `provideHttpClient(withInterceptors([authInterceptor]))`.
6.  **Guards**: Create `auth.guard.ts` and `admin.guard.ts`.

## 4. Feature Implementation

1.  **Login Page**:
    Generate the login page:
    ```bash
    ionic generate page pages/login
    ```
    Replace the logic with the code in the summary (handling roles and redirection).
2.  **Routes**: Update `src/app/app.routes.ts` with the protected routes.

## 5. Verification & Running

1.  **Install Dependencies**:
    ```bash
    npm install --legacy-peer-deps
    ```
    *The `--legacy-peer-deps` flag is often necessary with newer Node versions and Angular 19/20 to avoid minor version conflicts.*
2.  **Run Development Server**:
    ```bash
    ionic serve
    ```

## Troubleshooting
- **Invalid Version**: Check if any version in `package.json` has a typo or refers to a non-existent version (e.g. `^20.0.0` when only `19.x` is available).
- **ES Module Errors**: Ensure your imports in `.ts` files use relative paths correctly and that you aren't trying to `require()` ES modules.
