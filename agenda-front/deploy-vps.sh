#!/bin/bash

# Quick Deployment Script for VPS
# This script helps you securely set up environment variables on your VPS

echo "🚀 Agenda App - VPS Deployment Helper"
echo "======================================"
echo ""

# Check if running as root
if [ "$EUID" -ne 0 ]; then 
    echo "⚠️  Please run as root (use sudo)"
    exit 1
fi

# Create directory structure
echo "📁 Creating directory structure..."
mkdir -p /var/www/agenda-app/{backend,frontend}
cd /var/www/agenda-app

# Generate secure secrets
echo ""
echo "🔐 Generating secure secrets..."
JWT_SECRET=$(openssl rand -base64 64 | tr -d '\n')
DB_PASSWORD=$(openssl rand -base64 32 | tr -d '\n')

echo "✅ Secrets generated!"
echo ""

# Create backend .env
echo "📝 Creating backend .env file..."
cat > /var/www/agenda-app/backend/.env << EOF
# Database Configuration
DB_HOST=postgres
DB_PORT=5432
DB_NAME=agenda_db
DB_USER=agenda_user
DB_PASSWORD=${DB_PASSWORD}

# JWT Configuration
JWT_SECRET=${JWT_SECRET}
JWT_EXPIRATION=86400000

# Server Configuration
SERVER_PORT=8080
SPRING_PROFILES_ACTIVE=production

# CORS Configuration (UPDATE THIS WITH YOUR DOMAIN)
ALLOWED_ORIGINS=https://yourdomain.com,https://www.yourdomain.com
EOF

# Create docker-compose .env
echo "📝 Creating docker-compose .env file..."
cat > /var/www/agenda-app/.env << EOF
# Docker Compose Environment Variables

# Database
POSTGRES_DB=agenda_db
POSTGRES_USER=agenda_user
POSTGRES_PASSWORD=${DB_PASSWORD}

# Backend
BACKEND_PORT=8080
JWT_SECRET=${JWT_SECRET}

# Frontend
FRONTEND_PORT=80
EOF

# Set permissions
echo "🔒 Setting secure file permissions..."
chmod 600 /var/www/agenda-app/backend/.env
chmod 600 /var/www/agenda-app/.env
chown root:root /var/www/agenda-app/backend/.env
chown root:root /var/www/agenda-app/.env

echo ""
echo "✅ Environment files created successfully!"
echo ""
echo "📋 IMPORTANT: Save these credentials securely!"
echo "=============================================="
echo "Database Password: ${DB_PASSWORD}"
echo "JWT Secret: ${JWT_SECRET}"
echo ""
echo "⚠️  NEXT STEPS:"
echo "1. Update ALLOWED_ORIGINS in /var/www/agenda-app/backend/.env with your domain"
echo "2. Create docker-compose.yml in /var/www/agenda-app/"
echo "3. Build and deploy your application"
echo ""
echo "🔒 Security Checklist:"
echo "- [ ] Updated ALLOWED_ORIGINS with your domain"
echo "- [ ] Configured firewall (ufw)"
echo "- [ ] Set up SSL certificate (certbot)"
echo "- [ ] Configured Nginx reverse proxy"
echo "- [ ] Tested database connection"
echo ""
