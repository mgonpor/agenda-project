# Production Deployment Guide - VPS with Docker

## Understanding EXPO_PUBLIC Prefix

### ⚠️ Is EXPO_PUBLIC a Problem?

**Short Answer:** It's a limitation, not a problem.

**What EXPO_PUBLIC means:**
- In Expo, only variables prefixed with `EXPO_PUBLIC_` are accessible in your React Native code
- These variables are **embedded into the app bundle at build time**
- They are **NOT secret** - anyone can extract them from the compiled app
- This is similar to how environment variables work in React (REACT_APP_) or Next.js (NEXT_PUBLIC_)

**For your API URL:**
- ✅ Using `EXPO_PUBLIC_API_URL` is **correct and safe**
- The API URL is not a secret - users will see it in network requests anyway
- The security comes from JWT authentication, not hiding the URL

**What should NOT be in EXPO_PUBLIC:**
- ❌ Database passwords
- ❌ API secret keys
- ❌ Private encryption keys
- ✅ API URLs, feature flags, public config = OK

---

## Production VPS Deployment Architecture

```
┌─────────────────────────────────────────────────────────┐
│                    VPS Server                            │
│                                                          │
│  ┌────────────────────────────────────────────────────┐ │
│  │              Docker Compose                        │ │
│  │                                                    │ │
│  │  ┌──────────────┐      ┌──────────────┐          │ │
│  │  │   Frontend   │      │   Backend    │          │ │
│  │  │   (Nginx)    │◄────►│   (Spring)   │          │ │
│  │  │   Port 80    │      │   Port 8080  │          │ │
│  │  └──────────────┘      └──────────────┘          │ │
│  │         │                      │                  │ │
│  │         │                      ▼                  │ │
│  │         │              ┌──────────────┐          │ │
│  │         │              │   Database   │          │ │
│  │         │              │  (Postgres)  │          │ │
│  │         │              └──────────────┘          │ │
│  └────────────────────────────────────────────────────┘ │
│                                                          │
│  .env files stored securely on VPS                      │
└─────────────────────────────────────────────────────────┘
```

---

## Secure Environment Variable Management

### 1. Frontend .env (Expo/React Native)

**Location:** `/var/www/agenda-front/.env`

```bash
# Frontend .env (for building the app)
EXPO_PUBLIC_API_URL=https://api.yourdomain.com
EXPO_PUBLIC_APP_NAME=Agenda Escolar
EXPO_PUBLIC_DEFAULT_TRAMOS=12
EXPO_PUBLIC_DEBUG_MODE=false
```

**Security:**
- ✅ These are public values (embedded in app)
- ✅ Use HTTPS URL in production
- ✅ Point to your domain, not IP address

**Deployment:**
```bash
# Build the web version
npx expo export --platform web

# Or build native apps
eas build --platform android
eas build --platform ios
```

---

### 2. Backend .env (Spring Boot)

**Location:** `/var/www/agenda-backend/.env`

```bash
# Database Configuration
DB_HOST=postgres
DB_PORT=5432
DB_NAME=agenda_db
DB_USER=agenda_user
DB_PASSWORD=STRONG_RANDOM_PASSWORD_HERE

# JWT Configuration
JWT_SECRET=VERY_LONG_RANDOM_SECRET_KEY_HERE
JWT_EXPIRATION=86400000

# Server Configuration
SERVER_PORT=8080
SPRING_PROFILES_ACTIVE=production

# CORS Configuration
ALLOWED_ORIGINS=https://yourdomain.com,https://www.yourdomain.com
```

**Security:**
- 🔒 **NEVER commit this file to git**
- 🔒 Use strong random passwords (64+ characters)
- 🔒 Restrict file permissions: `chmod 600 .env`

---

### 3. Docker Compose .env

**Location:** `/var/www/agenda-app/.env`

```bash
# Docker Compose Environment Variables

# Database
POSTGRES_DB=agenda_db
POSTGRES_USER=agenda_user
POSTGRES_PASSWORD=STRONG_RANDOM_PASSWORD_HERE

# Backend
BACKEND_PORT=8080
JWT_SECRET=VERY_LONG_RANDOM_SECRET_KEY_HERE

# Frontend
FRONTEND_PORT=80
API_URL=http://backend:8080
```

---

## Step-by-Step VPS Deployment

### Step 1: Prepare Your VPS

```bash
# SSH into your VPS
ssh root@your-vps-ip

# Update system
apt update && apt upgrade -y

# Install Docker & Docker Compose
curl -fsSL https://get.docker.com -o get-docker.sh
sh get-docker.sh
apt install docker-compose -y

# Install Nginx (for reverse proxy)
apt install nginx -y

# Install certbot (for SSL)
apt install certbot python3-certbot-nginx -y
```

### Step 2: Create Directory Structure

```bash
mkdir -p /var/www/agenda-app
cd /var/www/agenda-app

# Create subdirectories
mkdir backend frontend
```

### Step 3: Securely Transfer .env Files

**Option A: Manual Creation (Most Secure)**
```bash
# SSH into VPS
ssh user@your-vps-ip

# Create backend .env
nano /var/www/agenda-app/backend/.env
# Paste your backend environment variables
# Save and exit (Ctrl+X, Y, Enter)

# Set strict permissions
chmod 600 /var/www/agenda-app/backend/.env
chown root:root /var/www/agenda-app/backend/.env
```

**Option B: Use SCP (Secure Copy)**
```bash
# From your local machine
scp .env user@your-vps-ip:/var/www/agenda-app/backend/.env

# Then SSH in and set permissions
ssh user@your-vps-ip
chmod 600 /var/www/agenda-app/backend/.env
```

**Option C: Use Environment Variable Manager (Recommended for Teams)**
- Use tools like **Vault** (HashiCorp)
- Use **AWS Secrets Manager** or **Azure Key Vault**
- Use **Doppler** or **Infisical** (modern secret managers)

### Step 4: Create docker-compose.yml

```yaml
version: '3.8'

services:
  postgres:
    image: postgres:15-alpine
    container_name: agenda-db
    environment:
      POSTGRES_DB: ${POSTGRES_DB}
      POSTGRES_USER: ${POSTGRES_USER}
      POSTGRES_PASSWORD: ${POSTGRES_PASSWORD}
    volumes:
      - postgres_data:/var/lib/postgresql/data
    networks:
      - agenda-network
    restart: unless-stopped

  backend:
    build: ./backend
    container_name: agenda-backend
    env_file:
      - ./backend/.env
    ports:
      - "8080:8080"
    depends_on:
      - postgres
    networks:
      - agenda-network
    restart: unless-stopped

  frontend:
    build: ./frontend
    container_name: agenda-frontend
    ports:
      - "80:80"
    networks:
      - agenda-network
    restart: unless-stopped

volumes:
  postgres_data:

networks:
  agenda-network:
    driver: bridge
```

### Step 5: Security Best Practices

```bash
# 1. Set proper file permissions
chmod 600 /var/www/agenda-app/.env
chmod 600 /var/www/agenda-app/backend/.env

# 2. Create a non-root user for running Docker
useradd -m -s /bin/bash agenda
usermod -aG docker agenda

# 3. Set up firewall
ufw allow 22    # SSH
ufw allow 80    # HTTP
ufw allow 443   # HTTPS
ufw enable

# 4. Set up SSL with Let's Encrypt
certbot --nginx -d yourdomain.com -d www.yourdomain.com

# 5. Configure Nginx reverse proxy
nano /etc/nginx/sites-available/agenda
```

**Nginx Configuration:**
```nginx
server {
    listen 80;
    server_name yourdomain.com www.yourdomain.com;
    return 301 https://$server_name$request_uri;
}

server {
    listen 443 ssl http2;
    server_name yourdomain.com www.yourdomain.com;

    ssl_certificate /etc/letsencrypt/live/yourdomain.com/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/yourdomain.com/privkey.pem;

    # Frontend
    location / {
        proxy_pass http://localhost:80;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }

    # Backend API
    location /api/ {
        proxy_pass http://localhost:8080/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}
```

### Step 6: Deploy

```bash
cd /var/www/agenda-app
docker-compose up -d

# Check logs
docker-compose logs -f
```

---

## Security Checklist for Production

- [ ] ✅ All .env files have `chmod 600` permissions
- [ ] ✅ .env files are in .gitignore
- [ ] ✅ Using HTTPS (SSL certificate installed)
- [ ] ✅ Firewall configured (only ports 22, 80, 443 open)
- [ ] ✅ Database not exposed to public internet
- [ ] ✅ Strong passwords (64+ characters, random)
- [ ] ✅ JWT secret is long and random
- [ ] ✅ Regular backups configured
- [ ] ✅ Docker containers restart on failure
- [ ] ✅ Monitoring/logging set up

---

## Generating Secure Secrets

```bash
# Generate strong JWT secret (64 characters)
openssl rand -base64 64

# Generate database password
openssl rand -base64 32

# Or use a password manager like 1Password, Bitwarden
```

---

## Common Mistakes to Avoid

❌ **DON'T:**
- Commit .env files to git
- Use weak passwords like "password123"
- Expose database port to public internet
- Use HTTP in production (always HTTPS)
- Store secrets in frontend code
- Use the same passwords for dev and prod

✅ **DO:**
- Use environment-specific .env files
- Rotate secrets regularly
- Use a secret manager for teams
- Set up automated backups
- Monitor logs for security issues
- Keep Docker images updated

---

## Quick Reference: Where Each .env Lives

```
Production VPS:
├── /var/www/agenda-app/
│   ├── .env                    # Docker Compose vars
│   ├── docker-compose.yml
│   ├── backend/
│   │   ├── .env               # Spring Boot secrets (NEVER in git)
│   │   ├── Dockerfile
│   │   └── src/
│   └── frontend/
│       ├── .env               # Build-time only (public values)
│       ├── Dockerfile
│       └── dist/              # Built app files

Git Repository:
├── backend/
│   ├── .env.example           # ✅ Template (safe to commit)
│   └── .gitignore             # ✅ Contains .env
└── frontend/
    ├── .env.example           # ✅ Template (safe to commit)
    └── .gitignore             # ✅ Contains .env
```

---

## Summary

1. **EXPO_PUBLIC is fine** - it's for public config like API URLs
2. **Backend .env contains secrets** - never commit, use chmod 600
3. **Transfer .env securely** - use SCP or create manually on VPS
4. **Use Docker Compose** - keeps everything organized
5. **Always use HTTPS** - Let's Encrypt is free
6. **Restrict permissions** - chmod 600 for all .env files
7. **Use strong secrets** - 64+ character random strings
