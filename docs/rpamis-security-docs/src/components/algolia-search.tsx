'use client';

import { DocSearchModal, useDocSearchKeyboardEvents } from '@docsearch/react';
import '@docsearch/css';
import type { SharedProps } from 'fumadocs-ui/components/dialog/search';
import { createPortal } from 'react-dom';
import { useCallback } from 'react';

const appId = process.env.NEXT_PUBLIC_ALGOLIA_APP_ID ?? '';
const apiKey = process.env.NEXT_PUBLIC_ALGOLIA_API_KEY ?? '';
const indexName = process.env.NEXT_PUBLIC_ALGOLIA_INDEX_NAME ?? 'rpamis-security-docs-crawler';

export default function AlgoliaSearchDialog(props: SharedProps) {
  const { open, onOpenChange } = props;

  const onClose = useCallback(() => {
    onOpenChange(false);
  }, [onOpenChange]);

  useDocSearchKeyboardEvents({
    isOpen: open,
    onOpen: () => onOpenChange(true),
    onClose,
  });

  if (!open) return null;

  return createPortal(
    <DocSearchModal
      appId={appId}
      indexName={indexName}
      apiKey={apiKey}
      onClose={onClose}
      initialScrollY={typeof window !== 'undefined' ? window.scrollY : 0}
    />,
    document.body,
  );
}
