export function getAssetPath(path: string): string {
  const basePath = process.env.NODE_ENV === 'production' ? '/rpamis-security' : '';
  return `${basePath}${path}`;
}
