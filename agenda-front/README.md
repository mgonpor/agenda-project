# Desarrollo Local - Agenda App

## 🚀 Inicio Rápido (Quick Start)

### 1. Instalar dependencias
```bash
npm install
```

### 2. Configurar .env
```bash
# Copia el ejemplo
cp .env.example .env

# Edita .env con tu configuración
# Para desarrollo local:
EXPO_PUBLIC_API_URL=http://localhost:8080
```

### 3. Iniciar desarrollo
```bash
npm start
```

### 4. Abrir la app
- Presiona `w` para abrir en navegador web
- Presiona `a` para Android emulator
- Escanea QR con Expo Go en tu teléfono

---

## 📍 Dónde Corre la App

### Frontend (Expo)
- **Web:** `http://localhost:8081`
- **Metro Bundler:** `http://localhost:8081`
- **Expo DevTools:** Se abre automáticamente en el navegador

### Backend (Spring Boot)
- **API:** `http://localhost:8080`
- Debe estar corriendo antes de iniciar el frontend

---

## 🔧 Configuración de URLs

### Desarrollo en Web
```bash
# .env
EXPO_PUBLIC_API_URL=http://localhost:8080
```

### Desarrollo en Android Emulator
```bash
# .env
EXPO_PUBLIC_API_URL=http://10.0.2.2:8080
```
⚠️ `10.0.2.2` es la IP que Android Emulator usa para `localhost` de tu PC

### Desarrollo en Teléfono Físico
```bash
# .env
# Usa la IP local de tu PC (encuentra con ipconfig)
EXPO_PUBLIC_API_URL=http://192.168.1.X:8080
```

Para encontrar tu IP:
```bash
ipconfig
# Busca "IPv4 Address"
```

---

## 📝 Scripts Disponibles

```bash
# Iniciar servidor de desarrollo
npm start

# Iniciar en web directamente
npm run web

# Limpiar caché
npx expo start --clear

# Ver en Android
npm run android

# Ver en iOS (solo Mac)
npm run ios
```

---

## 🐛 Problemas Comunes

### "Network request failed"
✅ Verifica que el backend esté corriendo en `localhost:8080`
✅ Verifica la URL en `.env`
✅ Si usas Android Emulator, usa `http://10.0.2.2:8080`

### Variables de entorno no se actualizan
✅ Reinicia con: `npx expo start --clear`

### CORS errors
✅ Configura CORS en tu backend Spring Boot para permitir `http://localhost:8081`

---

## ✅ Checklist

Antes de desarrollar:
- [ ] Backend corriendo en `localhost:8080`
- [ ] `.env` configurado
- [ ] `npm install` ejecutado
- [ ] `npm start` corriendo
- [ ] Puedes hacer login

---

Para más detalles, ver `DESARROLLO_LOCAL.md`
