export function getApiBaseUrl() {
    return typeof window === 'undefined' ? process.env.API_BASE_URL : process.env.NEXT_PUBLIC_API_BASE_URL;
}
