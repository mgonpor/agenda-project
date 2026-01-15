import { storage } from '@/utils/storage';
import { router } from 'expo-router';
import React, { createContext, useContext, useEffect, useState } from 'react';

interface AuthContextType {
    token: string | null;
    isLoading: boolean;
    signIn: (token: string) => Promise<void>;
    signOut: () => Promise<void>;
    isAuthenticated: boolean;
}

const AuthContext = createContext<AuthContextType | null>(null);

export function AuthProvider({ children }: { children: React.ReactNode }) {
    const [token, setToken] = useState<string | null>(null);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        checkToken();
    }, []);

    const checkToken = async () => {
        try {
            const storedToken = await storage.getItem('jwt_token');
            if (storedToken) {
                setToken(storedToken);
            }
        } catch (e) {
            console.error('Failed to load token', e);
        } finally {
            setIsLoading(false);
        }
    };

    const signIn = async (newToken: string) => {
        try {
            await storage.setItem('jwt_token', newToken);
            setToken(newToken);
            router.replace('/(tabs)' as any);
        } catch (e) {
            console.error('Failed to save token', e);
        }
    };

    const signOut = async () => {
        try {
            await storage.removeItem('jwt_token');
            setToken(null);
            router.replace('/auth/login' as any);
        } catch (e) {
            console.error('Failed to delete token', e);
        }
    };

    return (
        <AuthContext.Provider
            value={{
                token,
                isLoading,
                signIn,
                signOut,
                isAuthenticated: !!token,
            }}
        >
            {children}
        </AuthContext.Provider>
    );
}

export function useAuth() {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error('useAuth must be used within an AuthProvider');
    }
    return context;
}
