import type { BaseLayoutProps } from 'fumadocs-ui/layouts/shared';

// rpamis-security 项目配置
export const gitConfig = {
  user: 'rpamis',
  repo: 'rpamis-security',
  branch: 'master',
};

export function baseOptions(): BaseLayoutProps {
  return {
    nav: {
      title: (
        <div className="flex items-center gap-2">
          <img src="/logo.png" alt="Rpamis-Security Logo" className="h-8 w-8 rounded-lg" />
          <span className="font-bold">Rpamis-Security</span>
        </div>
      ),
      url: '/',
    },
    githubUrl: `https://github.com/${gitConfig.user}/${gitConfig.repo}`,
  };
}
