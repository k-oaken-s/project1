import Image, { ImageProps } from "next/image";
import { useState, useMemo } from "react";
import { getApiBaseUrl } from "@/utils/getApiBaseUrl";
import { getImageUrl } from "@/utils/getImageUrl";

const MAX_RETRY_COUNT = 3;

function ImageWrapper({ src, alt, ...props }: ImageProps) {
    const [retryCount, setRetryCount] = useState(0);

    const currentSrc = useMemo(() => {
        // キャッシュバイパス用のクエリパラメータを追加
        return retryCount > 0 ? `${src}?retry=${retryCount}` : src;
    }, [src, retryCount]);

    const handleError = () => {
        if (retryCount < MAX_RETRY_COUNT) {
            setRetryCount(retryCount + 1);
        } else {
            console.error(`Failed to load image after ${MAX_RETRY_COUNT} attempts: ${src}`);
        }
    };

    return (
        <Image
            {...props} // width, height, styleなどのプロパティをそのまま渡す
            src={currentSrc}
            alt={alt}
            onError={handleError}
        />
    );
}

export default ImageWrapper;
