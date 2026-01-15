import * as SecureStore from 'expo-secure-store';
import { Platform } from 'react-native';

/**
 * Storage utility that works across web and native platforms
 * - Web: Uses localStorage
 * - iOS/Android: Uses expo-secure-store
 */

const isWeb = Platform.OS === 'web';

export const storage = {
    async getItem(key: string): Promise<string | null> {
        if (isWeb) {
            // Use localStorage for web
            return localStorage.getItem(key);
        } else {
            // Use SecureStore for native
            return await SecureStore.getItemAsync(key);
        }
    },

    async setItem(key: string, value: string): Promise<void> {
        if (isWeb) {
            // Use localStorage for web
            localStorage.setItem(key, value);
        } else {
            // Use SecureStore for native
            await SecureStore.setItemAsync(key, value);
        }
    },

    async removeItem(key: string): Promise<void> {
        if (isWeb) {
            // Use localStorage for web
            localStorage.removeItem(key);
        } else {
            // Use SecureStore for native
            await SecureStore.deleteItemAsync(key);
        }
    },
};
