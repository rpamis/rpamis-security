import { source } from '@/lib/source';
import { createFromSource } from 'fumadocs-core/search/server';

// 静态导出模式配置
export const dynamic = 'force-static';
export const revalidate = false;

export const { staticGET: GET } = createFromSource(source, {
  // https://docs.orama.com/docs/orama-js/supported-languages
  language: 'english',
});
