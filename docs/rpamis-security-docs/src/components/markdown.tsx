/**
 * Markdown 渲染组件 - 基于 Remark/Rehype 管道的 Markdown 到 React 组件转换器
 *
 * 该组件提供了 Markdown 文本的解析和渲染功能，支持：
 * - GitHub Flavored Markdown (GFM) 语法
 * - 代码高亮和动态代码块
 * - 文字逐词淡入动画效果
 * - 异步渲染和缓存优化
 *
 * 技术栈：
 * - remark: Markdown 解析器
 * - rehype: HTML/HAST 转换器和处理器
 * - hast-util-to-jsx-runtime: HAST 到 React JSX 转换
 * - fumadocs-ui: 文档 UI 组件库
 */

import { remark } from 'remark';
import remarkGfm from 'remark-gfm';
import remarkRehype from 'remark-rehype';
import { toJsxRuntime } from 'hast-util-to-jsx-runtime';
import {
  Children,
  type ComponentProps,
  type ReactElement,
  type ReactNode,
  Suspense,
  use,
} from 'react';
import { Fragment, jsx, jsxs } from 'react/jsx-runtime';
import { DynamicCodeBlock } from 'fumadocs-ui/components/dynamic-codeblock';
import defaultMdxComponents from 'fumadocs-ui/mdx';
import { visit } from 'unist-util-visit';
import type { ElementContent, Root, RootContent } from 'hast';

/**
 * Markdown 处理器接口
 *
 * 定义了 Markdown 处理的核心方法，用于将 Markdown 文本转换为 React 组件
 */
export interface Processor {
  /**
   * 处理 Markdown 文本
   * @param content - 要处理的 Markdown 文本
   * @returns Promise<ReactNode> - 解析后的 React 组件
   */
  process: (content: string) => Promise<ReactNode>;
}

/**
 * Rehype 插件：文字逐词包装处理器
 *
 * 该插件用于将 Markdown 文本中的每个单词和空白字符包装在 `<span>` 元素中，
 * 并添加 `animate-fd-fade-in` 类以实现逐词淡入的动画效果。
 *
 * 关键特性：
 * - 跳过代码块（<pre> 元素）以避免破坏代码高亮
 * - 保留原始文本的空格和格式
 * - 递归处理文本节点
 *
 * @returns Rehype 插件函数
 */
export function rehypeWrapWords() {
  return (tree: Root) => {
    // 遍历 AST 树中的文本和元素节点
    visit(tree, ['text', 'element'], (node, index, parent) => {
      // 跳过代码块，避免破坏代码高亮
      if (node.type === 'element' && node.tagName === 'pre') return 'skip';
      // 只处理文本节点
      if (node.type !== 'text' || !parent || index === undefined) return;

      // 按单词和空白字符分割（保留空白字符的位置）
      const words = node.value.split(/(?=\s)/);

      // 为每个单词和空白字符创建 span 节点
      const newNodes: ElementContent[] = words.flatMap((word) => {
        if (word.length === 0) return [];

        return {
          type: 'element',
          tagName: 'span',
          properties: {
            class: 'animate-fd-fade-in',
          },
          children: [{ type: 'text', value: word }],
        };
      });

      // 替换原始文本节点为包含 span 子节点的新元素
      Object.assign(node, {
        type: 'element',
        tagName: 'span',
        properties: {},
        children: newNodes,
      } satisfies RootContent);
      return 'skip'; // 停止进一步处理此节点
    });
  };
}

/**
 * 创建 Markdown 处理器
 *
 * 该函数构建并配置了完整的 Remark/Rehype 处理管道，用于将 Markdown 文本
 * 转换为 React 组件。处理管道包括：
 *
 * 1. remark: Markdown 解析器（Markdown -> MDAST）
 * 2. remarkGfm: GitHub Flavored Markdown 支持
 * 3. remarkRehype: Markdown AST 到 HTML AST 转换（MDAST -> HAST）
 * 4. rehypeWrapWords: 自定义插件，为文本添加逐词动画
 *
 * @returns Processor - 配置好的 Markdown 处理器实例
 */
function createProcessor(): Processor {
  // 构建 remark 处理管道
  const processor = remark().use(remarkGfm).use(remarkRehype).use(rehypeWrapWords);

  return {
    /**
     * 处理 Markdown 文本并转换为 React 组件
     * @param content - Markdown 文本内容
     * @returns Promise<ReactNode> - 渲染后的 React 组件
     */
    async process(content) {
      // 步骤 1: 解析 Markdown 为语法树（MDAST）
      const nodes = processor.parse({ value: content });
      // 步骤 2: 运行插件处理语法树（生成 HAST）
      const hast = await processor.run(nodes);

      // 步骤 3: 将 HAST 转换为 React JSX
      return toJsxRuntime(hast, {
        development: false,
        jsx,
        jsxs,
        Fragment,
        components: {
          ...defaultMdxComponents,
          pre: Pre, // 自定义代码块组件
          img: undefined, // 使用 JSX 处理图片
        },
      });
    },
  };
}

/**
 * 自定义代码块渲染组件
 *
 * 替代默认的 `<pre>` 标签渲染，使用 Fumadocs 的 DynamicCodeBlock 组件
 * 提供代码高亮、复制、换行等增强功能。
 *
 * @param props - <pre> 标签的属性
 * @returns ReactNode - 渲染后的代码块组件
 */
function Pre(props: ComponentProps<'pre'>) {
  // 提取 <code> 子元素
  const code = Children.only(props.children) as ReactElement;
  const codeProps = code.props as ComponentProps<'code'>;
  const content = codeProps.children;

  // 确保内容是字符串类型
  if (typeof content !== 'string') return null;

  // 提取代码语言（从 className 中解析）
  let lang =
    codeProps.className
      ?.split(' ')
      .find((v) => v.startsWith('language-'))
      ?.slice('language-'.length) ?? 'text';

  // 特殊处理 mdx 语言（转换为 md）
  if (lang === 'mdx') lang = 'md';

  // 使用 DynamicCodeBlock 渲染代码块
  return <DynamicCodeBlock lang={lang} code={content.trimEnd()} />;
}

/**
 * 全局处理器实例
 *
 * 创建单一处理器实例并在整个应用程序中复用，避免重复初始化开销
 */
const processor = createProcessor();

/**
 * Markdown 渲染主组件
 *
 * 提供简单的 API 用于将 Markdown 文本渲染为 React 组件。
 * 使用 Suspense 进行异步加载优化，提供占位内容。
 *
 * @param props - 组件属性
 * @param props.text - 要渲染的 Markdown 文本
 * @returns ReactNode - 渲染后的 Markdown 组件
 *
 * @example
 * ```tsx
 * <Markdown text="# 标题\n\n这是一段 **Markdown** 文本" />
 * ```
 */
export function Markdown({ text }: { text: string }) {
  return (
    <Suspense fallback={<p className="invisible">{text}</p>}>
      <Renderer text={text} />
    </Suspense>
  );
}

/**
 * 渲染结果缓存
 *
 * 使用 Map 缓存 Markdown 文本到 React 组件的渲染结果，
 * 避免重复解析相同的 Markdown 内容，提升性能。
 */
const cache = new Map<string, Promise<ReactNode>>();

/**
 * 内部渲染组件
 *
 * 负责实际的 Markdown 解析和渲染过程，使用 React 18 的 `use` 钩子
 * 直接消费 Promise 结果。
 *
 * @param props - 组件属性
 * @param props.text - 要渲染的 Markdown 文本
 * @returns ReactNode - 渲染后的 React 组件
 */
function Renderer({ text }: { text: string }) {
  // 从缓存中获取或创建新的渲染任务
  const result = cache.get(text) ?? processor.process(text);
  cache.set(text, result);

  // 使用 React 18 的 use 钩子直接解析 Promise
  return use(result);
}
