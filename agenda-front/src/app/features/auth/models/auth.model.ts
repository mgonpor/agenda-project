export interface LoginResponse {
    access: string;
    refresh: string;
}

export interface RefreshDTO {
    refresh: string;
}
