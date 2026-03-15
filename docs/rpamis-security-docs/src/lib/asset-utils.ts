export function getAssetPath(path: string): string {
  // 与 next.config.mjs 中的 basePath 保持一致：
  // - GitHub Pages：`NEXT_PUBLIC_DEPLOY_ENV=github` 时使用 `/rpamis-security` 前缀
  // - 其他环境（如腾讯云 EdgeOne）：使用根路径
  const basePath =
    process.env.NEXT_PUBLIC_DEPLOY_ENV === 'github' ? '/rpamis-security' : '';
  const normalizedPath = path.startsWith('/') ? path : `/${path}`;

  return `${basePath}${normalizedPath}`;
}
