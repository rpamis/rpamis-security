import type { BaseLayoutProps } from 'fumadocs-ui/layouts/shared';
import { getAssetPath } from '@/lib/asset-utils';

export const gitConfig = {
  user: 'rpamis',
  repo: 'rpamis-security',
  branch: 'master',
};

export function baseOptions(): BaseLayoutProps {
  const logoSrc = getAssetPath('/logo.png');

  return {
    nav: {
      title: (
        <div className="flex items-center gap-2">
          <img src={logoSrc} alt="Rpamis-Security Logo" className="h-8 w-8 rounded-lg" />
          <span className="font-bold">Rpamis-Security</span>
        </div>
      ),
      url: '/',
    },
    githubUrl: `https://github.com/${gitConfig.user}/${gitConfig.repo}`,
  };
}
