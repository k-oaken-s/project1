'use client'

export default function GlobalError({
    error,
    reset,
}: {
    error: Error & { digest?: string }
    reset: () => void
}) {
    return (
        <html>
            <body>
                <h2>何か問題が発生しました！</h2>
                <button onClick={() => reset()}>もう一度試す</button>
            </body>
        </html>
    )
}
