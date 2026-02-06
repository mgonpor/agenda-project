import { Injectable, signal } from '@angular/core';

export interface Toast {
    message: string;
    type: 'success' | 'error' | 'info' | 'warning';
    id: number;
}

@Injectable({
    providedIn: 'root',
})
export class ToastService {
    private toastsSignal = signal<Toast[]>([]);
    toasts = this.toastsSignal.asReadonly();
    private nextId = 0;

    show(message: string, type: 'success' | 'error' | 'info' | 'warning' = 'info', duration = 3000) {
        const id = this.nextId++;
        const toast: Toast = { message, type, id };
        this.toastsSignal.update((toasts) => [...toasts, toast]);

        setTimeout(() => {
            this.remove(id);
        }, duration);
    }

    success(message: string) {
        this.show(message, 'success');
    }

    error(message: string) {
        this.show(message, 'error', 5000);
    }

    warning(message: string) {
        this.show(message, 'warning');
    }

    private remove(id: number) {
        this.toastsSignal.update((toasts) => toasts.filter((t) => t.id !== id));
    }
}
