export function getApiBaseUrl(nextPublicApiBaseUrl: boolean = false) {
    if (nextPublicApiBaseUrl) {
        return process.env.API_BASE_URL ?? 'http://backend:8080';
    }
    return process.env.NEXT_PUBLIC_API_BASE_URL ?? 'http://localhost:8080';
}
