import { client } from 'algoliasearch';
import { sync, type DocumentRecord } from 'fumadocs-core/search/algolia';
import * as fs from 'node:fs';
import * as path from 'node:path';

const appId = process.env.NEXT_PUBLIC_ALGOLIA_APP_ID ?? '';
const apiKey = process.env.NEXT_PUBLIC_ALGOLIA_API_KEY ?? '';
const indexName = process.env.NEXT_PUBLIC_ALGOLIA_INDEX_NAME ?? 'rpamis-security-docs';

// After `next build` with output: 'export', the static JSON can be at:
// - out/static.json                        (output: 'export')
// - .next/server/app/static.json/body       (Next.js 15+)
// - .next/server/app/static.json.body       (older)
const possiblePaths = [
  path.resolve('out/static.json'),
  path.resolve('.next/server/app/static.json/body'),
  path.resolve('.next/server/app/static.json.body'),
];

function findStaticJson(): string {
  for (const p of possiblePaths) {
    if (fs.existsSync(p)) {
      return p;
    }
  }
  throw new Error(
    `static.json not found. Tried:\n${possiblePaths.map((p) => `  - ${p}`).join('\n')}\nMake sure to run 'next build' first.`
  );
}

async function main() {
  if (!appId || !apiKey) {
    console.error(
      'Missing Algolia credentials. Set ALGOLIA_APP_ID and ALGOLIA_API_KEY environment variables.'
    );
    process.exit(1);
  }

  const filePath = findStaticJson();
  console.log(`Reading search indexes from: ${filePath}`);

  const content = JSON.parse(fs.readFileSync(filePath, 'utf-8')) as DocumentRecord[];
  console.log(`Found ${content.length} documents to sync.`);

  const algoliaClient = client(appId, apiKey);

  await sync(algoliaClient, {
    indexName,
    documents: content,
  });

  console.log(`Successfully synced ${content.length} documents to Algolia index "${indexName}".`);
}

main().catch((err) => {
  console.error('Failed to sync Algolia search indexes:', err);
  process.exit(1);
});
