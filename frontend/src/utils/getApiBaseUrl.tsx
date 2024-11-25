export function getApiBaseUrl(nextPublicApiBaseUrl: boolean = false) {
    if (nextPublicApiBaseUrl) {
        return process.env.API_BASE_URL ?? 'http://backend:8080';
    }
    return typeof window === 'undefined' ? process.env.API_BASE_URL : process.env.NEXT_PUBLIC_API_BASE_URL!;
}
