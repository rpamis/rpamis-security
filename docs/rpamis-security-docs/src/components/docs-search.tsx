'use client';
import { Search, X } from 'lucide-react';
import { useDocsSearch } from 'fumadocs-core/search/client';
import { cn } from '../lib/cn';
import { buttonVariants } from './ui/button';
import Link from 'fumadocs-core/link';

export function DocsSearch() {
  const { search, setSearch, query, results, isLoading } = useDocsSearch({
    type: 'static',
  });

  return (
    <div className="w-full max-w-md">
      <div className="relative">
        <div className="relative rounded-md shadow-sm">
          <div className="pointer-events-none absolute inset-y-0 left-0 flex items-center pl-3">
            <Search className="h-4 w-4 text-fd-muted-foreground" />
          </div>
          <input
            type="text"
            className="block w-full rounded-md border-0 py-2 pl-10 pr-3 text-fd-foreground ring-1 ring-inset ring-fd-border placeholder:text-fd-muted-foreground focus:ring-2 focus:ring-inset focus:ring-fd-ring sm:text-sm sm:leading-6"
            placeholder="Search docs..."
            value={query}
            onChange={(e) => setSearch(e.target.value)}
          />
          {query && (
            <button
              type="button"
              className="absolute inset-y-0 right-0 flex items-center pr-3"
              onClick={() => setSearch('')}
            >
              <X className="h-4 w-4 text-fd-muted-foreground hover:text-fd-foreground" />
            </button>
          )}
        </div>
        {query && (
          <div className="absolute z-10 mt-2 w-full rounded-md border bg-fd-card p-2 shadow-lg">
            {isLoading ? (
              <div className="text-sm text-fd-muted-foreground">Searching...</div>
            ) : results.length === 0 ? (
              <div className="text-sm text-fd-muted-foreground">No results found</div>
            ) : (
              <ul className="space-y-1">
                {results.map((result) => (
                  <li key={result.id}>
                    <Link
                      href={result.url}
                      className="block rounded-md px-3 py-2 text-sm text-fd-foreground hover:bg-fd-accent hover:text-fd-accent-foreground"
                      onClick={() => setSearch('')}
                    >
                      <p className="font-medium">{result.title}</p>
                      {result.description && (
                        <p className="text-fd-muted-foreground">{result.description}</p>
                      )}
                    </Link>
                  </li>
                ))}
              </ul>
            )}
          </div>
        )}
      </div>
    </div>
  );
}
