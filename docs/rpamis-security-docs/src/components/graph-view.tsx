'use client';
import { type RefObject, useEffect, useMemo, useRef, useState } from 'react';
import ForceGraph2D from 'react-force-graph-2d';
import type {
  ForceGraphMethods,
  ForceGraphProps,
  LinkObject,
  NodeObject,
} from 'react-force-graph-2d';
import { forceCollide, forceLink, forceManyBody } from 'd3-force';
import { useRouter } from 'fumadocs-core/framework';

export interface Graph {
  links: Link[];
  nodes: Node[];
}

export type Node = NodeObject<NodeType>;
export type Link = LinkObject<NodeType, LinkType>;

export interface NodeType {
  text: string;
  description?: string;
  neighbors?: string[];
  url: string;
}

export type LinkType = Record<string, unknown>;

export interface GraphViewProps {
  graph: Graph;
}

export function GraphView(props: GraphViewProps) {
  const ref = useRef<HTMLDivElement>(null);
  const [mount, setMount] = useState(false);
  useEffect(() => {
    setMount(true);
  }, []);

  return (
    <div
      ref={ref}
      className="relative border h-[600px] [&_canvas]:size-full rounded-xl overflow-hidden bg-fd-background"
    >
      {mount && <ClientOnly {...props} containerRef={ref} />}
    </div>
  );
}

function ClientOnly({
  containerRef,
  graph,
}: GraphViewProps & { containerRef: RefObject<HTMLDivElement | null> }) {
  const graphRef = useRef<ForceGraphMethods<Node, Link> | undefined>(undefined);
  const hoveredRef = useRef<Node | null>(null);
  const router = useRouter();
  const [tooltip, setTooltip] = useState<{
    x: number;
    y: number;
    content: string;
  } | null>(null);
  const resizeObserverRef = useRef<ResizeObserver | null>(null);

  // 优化：添加 ResizeObserver 清理逻辑
  useEffect(() => {
    const container = containerRef.current;
    if (container) {
      resizeObserverRef.current = new ResizeObserver(() => {
        // 可以在这里添加响应容器大小变化的逻辑
        // 目前保持空实现，确保 observer 被正确创建和清理
      });
      resizeObserverRef.current.observe(container);
    }

    // 组件卸载时清理 ResizeObserver
    return () => {
      if (resizeObserverRef.current) {
        resizeObserverRef.current.disconnect();
        resizeObserverRef.current = null;
      }
    };
  }, [containerRef]);

  const handleNodeHover = (node: Node | null) => {
    const graph = graphRef.current;
    if (!graph) return;
    hoveredRef.current = node;

    if (node) {
      // 优化：为 node.x 和 node.y 添加类型安全检查，替代非空断言
      if (node.x == null || node.y == null) {
        console.warn('Node coordinates are missing', node);
        setTooltip(null);
        return;
      }

      try {
        const coords = graph.graph2ScreenCoords(node.x, node.y);
        setTooltip({
          x: coords.x + 4,
          y: coords.y + 4,
          content: node.description ?? 'No description',
        });
      } catch (error) {
        console.error('Failed to convert node coordinates to screen coords', error);
        setTooltip(null);
      }
    } else {
      setTooltip(null);
    }
  };

  // Custom node rendering: circle with text label below
  const nodeCanvasObject: ForceGraphProps['nodeCanvasObject'] = (node, ctx) => {
    const container = containerRef.current;
    if (!container) return;
    const style = getComputedStyle(container);
    const fontSize = 14;
    const radius = 5;

    // 优化：添加节点坐标有效性检查
    if (node.x == null || node.y == null) {
      console.warn('Node rendering skipped due to missing coordinates', node);
      return;
    }

    try {
      // Draw circle
      ctx.beginPath();
      ctx.arc(node.x, node.y, radius, 0, 2 * Math.PI, false);

      const hoverNode = hoveredRef.current;
      const isActive = hoverNode?.id === node.id || hoverNode?.neighbors?.includes(node.id as string);

      ctx.fillStyle = isActive
        ? style.getPropertyValue('--color-fd-primary')
        : style.getPropertyValue('--color-purple-300');
      ctx.fill();

      // Draw text below the node
      ctx.font = `${fontSize}px Sans-Serif`;
      ctx.textAlign = 'center';
      ctx.textBaseline = 'middle';
      ctx.fillStyle = getComputedStyle(container).getPropertyValue('color');
      ctx.fillText(node.text, node.x, node.y + radius + fontSize);
    } catch (error) {
      console.error('Node rendering failed', error);
    }
  };

  const linkColor = (link: Link) => {
    const container = containerRef.current;
    if (!container) return '#999';
    const style = getComputedStyle(container);
    const hoverNode = hoveredRef.current;

    if (
      hoverNode &&
      typeof link.source === 'object' &&
      typeof link.target === 'object' &&
      (hoverNode.id === link.source.id || hoverNode.id === link.target.id)
    ) {
      return style.getPropertyValue('--color-fd-primary');
    }

    return `color-mix(in oklab, ${style.getPropertyValue('--color-fd-muted-foreground')} 50%, transparent)`;
  };

  // Enrich nodes with neighbors for hover effects
  const enrichedNodes = useMemo(() => {
    // 优化：添加对输入数据的有效性检查
    if (!graph || !Array.isArray(graph.nodes) || !Array.isArray(graph.links)) {
      console.warn('Invalid graph data structure');
      return { nodes: [], links: [] };
    }

    try {
      const { nodes, links } = structuredClone(graph);
      for (const node of nodes) {
        node.neighbors = links.flatMap((link) => {
          if (link.source === node.id) return link.target as string;
          if (link.target === node.id) return link.source as string;
          return [];
        });
      }

      return {
        nodes,
        links,
      };
    } catch (error) {
      console.error('Failed to process graph data', error);
      return { nodes: [], links: [] };
    }
  }, [graph]);

  return (
    <>
      <ForceGraph2D<NodeType, LinkType>
        ref={{
          get current() {
            return graphRef.current;
          },
          set current(fg) {
            graphRef.current = fg;
            if (fg) {
              // 优化：确保 d3-force 初始化的安全性
              try {
                fg.d3Force('link', forceLink().distance(200));
                fg.d3Force('charge', forceManyBody().strength(10));
                fg.d3Force('collision', forceCollide(60));
              } catch (error) {
                console.error('Failed to initialize d3-force simulation', error);
              }
            }
          },
        }}
        graphData={enrichedNodes}
        nodeCanvasObject={nodeCanvasObject}
        linkColor={linkColor}
        onNodeHover={handleNodeHover}
        onNodeClick={(node) => {
          try {
            router.push(node.url);
          } catch (error) {
            console.error('Failed to navigate to node URL', error);
          }
        }}
        linkWidth={2}
        enableNodeDrag
        enableZoomInteraction
      />
      {tooltip && (
        <div
          className="absolute bg-fd-popover text-fd-popover-foreground size-fit p-2 border rounded-xl shadow-lg text-sm max-w-xs"
          style={{ top: tooltip.y, left: tooltip.x }}
        >
          {tooltip.content}
        </div>
      )}
    </>
  );
}
