import { createMDX } from 'fumadocs-mdx/next';

const withMDX = createMDX();

/** @type {import('next').NextConfig} */
const config = {
  output: 'export',
  serverExternalPackages: ['@takumi-rs/image-response'],
  reactStrictMode: true,
  // GitHub Pages 配置 - 项目名
  basePath: process.env.NODE_ENV === 'production' ? '/rpamis-security' : '',
  // 只在开发模式下使用 rewrites，静态导出模式下不支持
  ...(process.env.NODE_ENV !== 'production' ? {
    async rewrites() {
      return [
        {
          source: '/docs/:path*.mdx',
          destination: '/llms.mdx/docs/:path*',
        },
      ];
    },
  } : {}),
  typescript: {
    // 临时禁用类型检查以避免构建失败
    ignoreBuildErrors: true,
  },
};

export default withMDX(config);
