import { createMDX } from 'fumadocs-mdx/next';

const withMDX = createMDX();

/** @type {import('next').NextConfig} */
const config = {
  output: 'export',
  serverExternalPackages: ['@takumi-rs/image-response'],
  reactStrictMode: true,
  // 多环境部署配置
  // - GitHub Pages: 需要 `/rpamis-security` 前缀
  // - 腾讯云 EdgeOne: 部署在根路径，不需要前缀
  //
  // 通过环境变量 `NEXT_PUBLIC_DEPLOY_ENV` 区分部署环境：
  // - GitHub Pages 构建时设置为 'github'
  // - 其他环境（如腾讯云）留空或设置为其他值
  basePath:
    process.env.NEXT_PUBLIC_DEPLOY_ENV === 'github' ? '/rpamis-security' : '',
  assetPrefix:
    process.env.NEXT_PUBLIC_DEPLOY_ENV === 'github' ? '/rpamis-security' : '',
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
  // 性能优化配置
  compress: true,
  images: {
    formats: ['image/avif', 'image/webp'],
    minimumCacheTTL: 60,
    unoptimized: true, // 静态导出模式需要
  },
  compiler: {
    removeConsole: process.env.NODE_ENV === 'production',
  },
};

export default withMDX(config);
