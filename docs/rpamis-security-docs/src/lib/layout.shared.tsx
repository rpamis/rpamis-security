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
      title: 'Rpamis-Security',
    },
    githubUrl: `https://github.com/${gitConfig.user}/${gitConfig.repo}`,
  };
}
