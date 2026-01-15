# Environment Variables - Best Practices Guide

## Variables That Should NOT Be Hardcoded

### 🔴 Critical - MUST be in .env

1. **API URLs/Endpoints**
   - `EXPO_PUBLIC_API_URL` - Backend API base URL
   - Different for dev/staging/production

2. **API Keys & Secrets** (if you add them later)
   - Third-party service keys (Google Maps, Analytics, etc.)
   - OAuth client IDs
   - Push notification keys
   - **⚠️ NEVER commit these to git**

3. **Environment Identifiers**
   - `EXPO_PUBLIC_ENVIRONMENT` - dev/staging/production
   - Helps with conditional logic

### 🟡 Recommended - Should be configurable

4. **Timeout Values**
   - `EXPO_PUBLIC_API_TIMEOUT` - API request timeout
   - Prevents hanging requests

5. **Feature Flags**
   - `EXPO_PUBLIC_DEBUG_MODE` - Enable/disable debug logs
   - `EXPO_PUBLIC_ENABLE_ANALYTICS` - Toggle analytics
   - Useful for testing and gradual rollouts

6. **App Configuration**
   - `EXPO_PUBLIC_APP_NAME` - Display name
   - `EXPO_PUBLIC_DEFAULT_TRAMOS` - Default schedule periods
   - Makes app more flexible

7. **Pagination/Limits**
   - Items per page
   - Maximum file upload size
   - Cache durations

### 🟢 Optional - Nice to have

8. **UI Configuration**
   - Theme colors (if customizable per deployment)
   - Default language/locale
   - Date/time formats

9. **External URLs**
   - Privacy policy URL
   - Terms of service URL
   - Support/help URLs

## Current Setup

✅ Already configured:
- API URL
- API Timeout
- App Name
- Default Tramos
- Debug Mode
- Analytics Flag

## Security Notes

- ✅ `.env` is in `.gitignore` (good!)
- ✅ `.env.example` created as template
- ⚠️ In Expo, only variables prefixed with `EXPO_PUBLIC_` are accessible
- ⚠️ These variables are embedded in the app bundle (not truly secret)
- 🔒 For real secrets, use a backend proxy or secure key management
