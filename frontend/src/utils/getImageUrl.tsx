import { getApiBaseUrl } from '@/utils/getApiBaseUrl';

/**
 * Generate a full URL for an image based on its type and filename.
 * @param imageType - The type of the image (e.g., "category", "item").
 * @param imageName - The name of the image file.
 * @returns The full URL of the image.
 */
export function getImageUrl(imageName: string | null| undefined): string {
    if (!imageName) {
        return '/default-thumbnail.jpg';
    }
    return `${getApiBaseUrl()}${imageName}`;
}
