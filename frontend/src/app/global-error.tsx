'use client' // Error boundaries は Client Components である必要があります

export default function GlobalError({
    error,
    reset,
}: {
    error: Error & { digest?: string }
    reset: () => void
}) {
    return (
        // global-error には html と body タグを含める必要があります
        <html>
            <body>
                <h2>何か問題が発生しました！</h2>
                <button onClick={() => reset()}>もう一度試す</button>
            </body>
        </html>
    )
}
