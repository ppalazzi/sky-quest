import type { NextConfig } from 'next';

const nextConfig: NextConfig = {
  // This app is one module of a Maven monorepo; pin the tracing root so Next
  // does not infer it from lockfiles outside the repository.
  outputFileTracingRoot: __dirname,
  images: {
    remotePatterns: [
      {
        protocol: 'https',
        hostname: 'osricdienda.com',
      },
    ],
  },
};

export default nextConfig;
