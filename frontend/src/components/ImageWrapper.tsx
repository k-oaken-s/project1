import Image, { ImageProps } from "next/image";
import { useState } from "react";

const MAX_RETRY_COUNT = 3;

function CustomImage({ src, alt, ...props }: ImageProps) {
    const [retryCount, setRetryCount] = useState(0);
    const [currentSrc, setCurrentSrc] = useState(src);

    const handleError = () => {
        if (retryCount < MAX_RETRY_COUNT) {
            setRetryCount(retryCount + 1);
            setCurrentSrc(`${src}?retry=${retryCount + 1}`); // キャッシュバイパス用のクエリパラメータを追加
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

export default CustomImage;
