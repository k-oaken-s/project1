import type {NextConfig} from "next";

const nextConfig: NextConfig = {
    output: "standalone",
    reactStrictMode: true,
    // pageExtensions: [
    //     "page.tsx",
    //     "page.ts",
    //     // FIXME: Next.js has a bug which does not resolve not-found.page.tsx correctly
    //     // Instead, use `not-found.ts` as a workaround
    //     // "ts" is required to resolve `not-found.ts`
    //     // https://github.com/vercel/next.js/issues/65447
    //     "ts"
    // ],
    webpack: (config, {isServer}) => {
        if (!isServer) {
            config.resolve.fallback = {
                ...config.resolve.fallback,
                fs: false,
            };
        }
        return config;
    },
    images: {
        remotePatterns: [
            {
                protocol: 'http',
                hostname: 'backend',
                port: '8080',
                pathname: '/images/**',
            },
            {
                protocol: 'http',
                hostname: 'localhost',
                port: '8080',
                pathname: '/images/**',
            }
        ],
    },
};

export default nextConfig;
