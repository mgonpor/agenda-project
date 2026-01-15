# 🚀 Guía de Desarrollo Local

## Requisitos Previos

- Node.js 18+ instalado
- Backend Spring Boot corriendo en `localhost:8080`
- Expo CLI (se instala automáticamente con npx)

---

## 🏃 Cómo Ejecutar en Desarrollo

### 1. Instalar Dependencias (Primera vez)

```bash
cd c:\Users\migue\Desktop\agenda-project\agenda-front
npm install
```

### 2. Configurar Variables de Entorno

Asegúrate de que tu `.env` tenga la URL correcta para desarrollo local:

```bash
# .env
EXPO_PUBLIC_API_URL=http://localhost:8080
```

**Para Android Emulator:**
```bash
EXPO_PUBLIC_API_URL=http://10.0.2.2:8080
```

### 3. Iniciar el Servidor de Desarrollo

```bash
npx expo start
```

O también puedes usar:
```bash
npm start
```

### 4. Abrir la Aplicación

Después de ejecutar `npx expo start`, verás un menú con opciones:

```
› Press a │ open Android
› Press i │ open iOS simulator
› Press w │ open web

› Press r │ reload app
› Press m │ toggle menu
› Press o │ open project code in your editor
```

**Opciones:**
- **`w`** - Abrir en navegador web (más fácil para desarrollo)
- **`a`** - Abrir en emulador Android
- **`i`** - Abrir en simulador iOS (solo Mac)
- **Escanear QR** - Usar la app Expo Go en tu teléfono

---

## 📱 Desarrollo en Diferentes Plataformas

### Web (Más Rápido)
```bash
npx expo start --web
```
Se abrirá en `http://localhost:8081`

### Android
```bash
# Asegúrate de tener Android Studio instalado
npx expo start --android
```

### iOS (Solo Mac)
```bash
npx expo start --ios
```

---

## 🔧 Configuración Recomendada para Desarrollo

### Opción 1: Desarrollo Web (Recomendado para empezar)

1. **Backend:** Corre Spring Boot en `localhost:8080`
2. **Frontend:** Corre Expo en modo web
3. **`.env`:**
   ```bash
   EXPO_PUBLIC_API_URL=http://localhost:8080
   ```

### Opción 2: Desarrollo en Android Emulator

1. **Backend:** Corre Spring Boot en `localhost:8080`
2. **Frontend:** Corre Expo y abre en Android
3. **`.env`:**
   ```bash
   EXPO_PUBLIC_API_URL=http://10.0.2.2:8080
   ```
   ⚠️ `10.0.2.2` es la IP especial que el emulador Android usa para acceder a `localhost` de tu PC

### Opción 3: Desarrollo en Teléfono Físico

1. **Backend:** Corre Spring Boot en `localhost:8080`
2. **Frontend:** Escanea el QR con Expo Go
3. **`.env`:**
   ```bash
   # Usa la IP local de tu PC (encuentra con ipconfig)
   EXPO_PUBLIC_API_URL=http://192.168.1.X:8080
   ```

Para encontrar tu IP local:
```bash
# Windows
ipconfig
# Busca "IPv4 Address" en tu adaptador WiFi/Ethernet

# Linux/Mac
ifconfig
```

---

## 🛠️ Comandos Útiles

```bash
# Iniciar desarrollo
npm start

# Limpiar caché (si hay problemas)
npx expo start --clear

# Ver logs en tiempo real
npx expo start --dev-client

# Reiniciar servidor
# Presiona 'r' en la terminal donde corre expo
```

---

## 🐛 Solución de Problemas Comunes

### Error: "Network request failed"
**Causa:** El frontend no puede conectarse al backend.

**Solución:**
1. Verifica que el backend esté corriendo en `localhost:8080`
2. Verifica la URL en `.env`
3. Si usas Android Emulator, usa `http://10.0.2.2:8080`
4. Si usas teléfono físico, usa tu IP local (ej: `http://192.168.1.5:8080`)

### Error: "Unable to resolve module"
**Solución:**
```bash
# Limpia caché y reinstala
rm -rf node_modules
npm install
npx expo start --clear
```

### El backend rechaza las peticiones (CORS)
**Solución:** Asegúrate de que tu backend Spring Boot tenga CORS configurado:

```java
@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                    .allowedOrigins("http://localhost:8081", "http://10.0.2.2:8080")
                    .allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };
    }
}
```

### Variables de entorno no se actualizan
**Solución:**
```bash
# Detén el servidor (Ctrl+C)
# Reinicia con caché limpia
npx expo start --clear
```

---

## 📂 Estructura de Archivos para Desarrollo

```
agenda-front/
├── .env                    # ✅ Variables de entorno (NO commitear)
├── .env.example            # ✅ Template (SÍ commitear)
├── app/                    # Páginas de la app
│   ├── (tabs)/            # Tabs principales
│   │   ├── index.tsx      # Home (Calendario)
│   │   └── anotaciones.tsx # Anotaciones
│   ├── auth/              # Autenticación
│   │   ├── login.tsx
│   │   └── register.tsx
│   └── _layout.tsx        # Layout principal
├── components/            # Componentes reutilizables
│   ├── WeekCalendar.tsx
│   └── DaySelector.tsx
├── services/              # Lógica de API
│   ├── api.ts            # Configuración Axios
│   ├── auth.ts           # Servicios de auth
│   └── data.ts           # Servicios de datos
├── context/              # Estado global
│   └── AuthContext.tsx
└── types/                # TypeScript interfaces
    └── index.ts
```

---

## 🎯 Flujo de Desarrollo Típico

1. **Inicia el backend:**
   ```bash
   cd agenda-backend
   ./mvnw spring-boot:run
   ```

2. **Inicia el frontend:**
   ```bash
   cd agenda-front
   npm start
   ```

3. **Abre en web:**
   - Presiona `w` en la terminal
   - O visita `http://localhost:8081`

4. **Desarrolla:**
   - Los cambios se recargan automáticamente (Hot Reload)
   - Revisa la consola del navegador para errores
   - Usa React DevTools para debugging

5. **Prueba:**
   - Regístrate con un usuario
   - Inicia sesión
   - Verifica que el calendario cargue
   - Prueba las anotaciones

---

## 🔄 Hot Reload

Expo tiene **Hot Reload** activado por defecto:
- Guarda un archivo → La app se recarga automáticamente
- Si algo se rompe, presiona `r` para recargar manualmente

---

## 📊 Monitoreo en Desarrollo

### Ver logs del frontend:
- Consola del navegador (F12)
- Terminal donde corre `expo start`

### Ver logs del backend:
- Terminal donde corre Spring Boot
- Busca errores de CORS, autenticación, etc.

### Network Inspector:
- Abre DevTools (F12) → Network
- Verifica las peticiones HTTP
- Revisa headers (Authorization: Bearer ...)
- Revisa respuestas del backend

---

## ✅ Checklist de Desarrollo

Antes de empezar a desarrollar:
- [ ] Backend corriendo en `localhost:8080`
- [ ] `.env` configurado con la URL correcta
- [ ] Dependencias instaladas (`npm install`)
- [ ] Expo iniciado (`npm start`)
- [ ] Puedes hacer login/register
- [ ] El calendario carga datos
- [ ] Las anotaciones funcionan

---

## 🚀 Próximos Pasos

1. **Desarrolla features nuevas** en `app/` y `components/`
2. **Prueba en diferentes plataformas** (web, Android, iOS)
3. **Cuando esté listo para producción**, sigue `PRODUCTION_DEPLOYMENT.md`

---

## 📚 Recursos Útiles

- [Expo Docs](https://docs.expo.dev/)
- [React Native Docs](https://reactnative.dev/)
- [Axios Docs](https://axios-http.com/)
- [React Navigation](https://reactnavigation.org/)

---

## 💡 Tips

- Usa **web** para desarrollo rápido (más fácil de debuggear)
- Prueba en **Android/iOS** antes de hacer deploy
- Mantén el backend corriendo mientras desarrollas
- Usa **Postman** para probar el backend independientemente
- Revisa los **logs** cuando algo no funcione
