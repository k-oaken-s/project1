import { getApiBaseUrl } from '@/utils/getApiBaseUrl';
import { StaticImport } from 'next/dist/shared/lib/get-img-props';

/**
 * Generate a full URL for an image based on its type and filename.
 * @param imageName - The name of the image file or StaticImport.
 * @returns The full URL of the image.
 */
export function getImageUrl(imageName: string | StaticImport | null | undefined): string {
    if (!imageName) {
        return getApiBaseUrl(true)  + '/resources/default-thumbnail.webp';
    }

    if (typeof imageName !== 'string') {
        return imageName.toString();
    }
    console.log(`${getApiBaseUrl(true)}${imageName}`);
    return `${getApiBaseUrl(true)}${imageName}`;
}
