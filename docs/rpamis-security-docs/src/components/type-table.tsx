/**
 * @file TypeTable 组件 - 用于展示类型定义表格的 React 组件
 * @description 提供类型安全的属性展示表格，支持字段类型、描述、默认值、必填项等信息的展示，
 * 并具有响应式设计和可折叠的详细信息面板
 */

'use client';

import { ChevronDown } from 'lucide-react';
import Link from 'fumadocs-core/link';
import { cva } from 'class-variance-authority';
import { cn } from '../lib/cn';
import { type ComponentProps, type ReactNode, useEffect, useState } from 'react';
import { Collapsible, CollapsibleContent, CollapsibleTrigger } from './ui/collapsible';

/**
 * 函数参数节点接口
 * @interface ParameterNode
 * @description 用于描述函数类型属性的参数信息
 * @property {string} name - 参数名称
 * @property {ReactNode} description - 参数描述，支持 HTML 内容
 * @example
 * const parameter: ParameterNode = {
 *   name: 'key',
 *   description: '加密/解密使用的密钥'
 * };
 */
export interface ParameterNode {
  name: string;
  description: ReactNode;
}

/**
 * 类型节点接口
 * @interface TypeNode
 * @description 用于描述类型属性的完整信息结构
 * @property {ReactNode} [description] - 属性的详细描述，支持 HTML 内容
 * @property {ReactNode} type - 类型签名（短格式），用于在表格中显示
 * @property {ReactNode} [typeDescription] - 类型签名（完整格式），用于在详情面板中显示
 * @property {string} [typeDescriptionLink] - 类型文档链接，支持跳转到外部类型定义
 * @property {ReactNode} [default] - 属性默认值，支持代码高亮
 * @property {boolean} [required] - 是否为必填属性，默认为 false
 * @property {boolean} [deprecated] - 是否已废弃，默认为 false
 * @property {ParameterNode[]} [parameters] - 函数类型属性的参数列表
 * @property {ReactNode} [returns] - 函数类型属性的返回值描述
 * @example
 * const typeNode: TypeNode = {
 *   description: 'SM4 加密算法使用的密钥',
 *   type: 'string',
 *   typeDescription: '16 字节的字符串密钥',
 *   default: '2U43wVWjLgToKBzG',
 *   required: true,
 *   parameters: []
 * };
 */
export interface TypeNode {
  /**
   * 字段的额外描述信息
   */
  description?: ReactNode;

  /**
   * 类型签名（短格式），在表格中显示
   */
  type: ReactNode;

  /**
   * 类型签名（完整格式），在详情面板中显示
   */
  typeDescription?: ReactNode;

  /**
   * 类型文档的可选链接地址
   */
  typeDescriptionLink?: string;

  /**
   * 属性的默认值
   */
  default?: ReactNode;

  /**
   * 是否为必填属性
   */
  required?: boolean;

  /**
   * 是否已废弃
   */
  deprecated?: boolean;

  /**
   * 若类型为函数，此处为函数参数信息列表
   */
  parameters?: ParameterNode[];

  /**
   * 函数类型的返回值描述
   */
  returns?: ReactNode;
}

/**
 * 字段样式变体定义
 * @constant fieldVariants
 * @description 使用 class-variance-authority 定义的字段标签样式变体
 * @type {cva}
 * @default 'text-fd-muted-foreground not-prose pe-2'
 * @remarks 用于统一表格字段标签的样式，确保在不同主题下的一致性
 */
const fieldVariants = cva('text-fd-muted-foreground not-prose pe-2');

/**
 * TypeTable 主组件
 * @component
 * @description 用于展示类型定义表格的主组件，支持属性的分类展示、搜索和响应式布局
 * @param {Object} props - 组件属性
 * @param {string} [props.id] - 组件唯一标识符，用于锚点导航
 * @param {Record<string, TypeNode>} props.type - 类型定义对象，键为属性名，值为类型信息
 * @param {string} [props.className] - 自定义类名
 * @param {ComponentProps<'div'>} [props...] - 继承自 div 元素的其他属性
 * @returns {React.ReactElement} 渲染后的组件
 * @example
 * import { TypeTable } from '@/components/type-table';
 *
 * const typeDef = {
 *   enable: {
 *     description: '是否启用安全组件',
 *     type: 'boolean',
 *     default: 'true',
 *     required: false
 *   },
 *   algorithm: {
 *     description: '加密算法配置',
 *     type: 'object',
 *     required: true
 *   }
 * };
 *
 * export function SecurityConfig() {
 *   return <TypeTable id="config" type={typeDef} />;
 * }
 */
export function TypeTable({
  id,
  type,
  className,
  ...props
}: { type: Record<string, TypeNode> } & ComponentProps<'div'>) {
  return (
    <div
      id={id}
      className={cn(
        '@container flex flex-col p-1 bg-fd-card text-fd-card-foreground rounded-2xl border my-6 text-sm overflow-hidden',
        className,
      )}
      {...props}
    >
      <div className="flex font-medium items-center px-3 py-1 not-prose text-fd-muted-foreground">
        <p className="w-1/4">Prop</p>
        <p className="@max-xl:hidden">Type</p>
      </div>
      {Object.entries(type).map(([key, value]) => (
        <Item key={key} parentId={id} name={key} item={value} />
      ))}
    </div>
  );
}

/**
 * 表单项内部组件
 * @component
 * @description 处理单个类型属性的展示和交互逻辑的内部组件
 * @param {Object} props - 组件属性
 * @param {string} [props.parentId] - 父组件的 id，用于生成锚点
 * @param {string} props.name - 属性名称
 * @param {TypeNode} props.item - 类型信息对象
 * @returns {React.ReactElement} 渲染后的组件
 * @internal 仅供 TypeTable 组件内部使用
 */
function Item({
  parentId,
  name,
  item: {
    parameters = [],
    description,
    required = false,
    deprecated,
    typeDescription,
    default: defaultValue,
    type,
    typeDescriptionLink,
    returns,
  },
}: {
  parentId?: string;
  name: string;
  item: TypeNode;
}) {
  const [open, setOpen] = useState(false);
  const id = parentId ? `${parentId}-${name}` : undefined;

  // 处理 URL 哈希锚点导航
  useEffect(() => {
    const hash = window.location.hash;
    if (!id || !hash) return;
    if (`#${id}` === hash) setOpen(true);
  }, [id]);

  return (
    <Collapsible
      id={id}
      open={open}
      onOpenChange={(v) => {
        if (v && id) {
          window.history.replaceState(null, '', `#${id}`);
        }
        setOpen(v);
      }}
      className={cn(
        'rounded-xl border overflow-hidden scroll-m-20 transition-all',
        open ? 'shadow-sm bg-fd-background not-last:mb-2' : 'border-transparent',
      )}
    >
      <CollapsibleTrigger className="relative flex flex-row items-center w-full group text-start px-3 py-2 not-prose hover:bg-fd-accent">
        <code
          className={cn(
            'text-fd-primary min-w-fit w-1/4 font-mono font-medium pe-2',
            deprecated && 'line-through text-fd-primary/50',
          )}
        >
          {name}
          {!required && '?'}
        </code>
        {typeDescriptionLink ? (
          <Link href={typeDescriptionLink} className="underline @max-xl:hidden">
            {type}
          </Link>
        ) : (
          <span className="@max-xl:hidden">{type}</span>
        )}
        <ChevronDown className="absolute end-2 size-4 text-fd-muted-foreground transition-transform group-data-[state=open]:rotate-180" />
      </CollapsibleTrigger>
      <CollapsibleContent>
        <div className="grid grid-cols-[1fr_3fr] gap-y-4 text-sm p-3 overflow-auto fd-scroll-container border-t">
          <div className="text-sm prose col-span-full prose-no-margin empty:hidden">
            {description}
          </div>
          {typeDescription && (
            <>
              <p className={cn(fieldVariants())}>Type</p>
              <p className="my-auto not-prose">{typeDescription}</p>
            </>
          )}
          {defaultValue && (
            <>
              <p className={cn(fieldVariants())}>Default</p>
              <p className="my-auto not-prose">{defaultValue}</p>
            </>
          )}
          {parameters.length > 0 && (
            <>
              <p className={cn(fieldVariants())}>Parameters</p>
              <div className="flex flex-col gap-2">
                {parameters.map((param) => (
                  <div key={param.name} className="inline-flex items-center flex-wrap gap-1">
                    <p className="font-medium not-prose text-nowrap">{param.name} -</p>
                    <div className="text-sm prose prose-no-margin">{param.description}</div>
                  </div>
                ))}
              </div>
            </>
          )}
          {returns && (
            <>
              <p className={cn(fieldVariants())}>Returns</p>
              <div className="my-auto text-sm prose prose-no-margin">{returns}</div>
            </>
          )}
        </div>
      </CollapsibleContent>
    </Collapsible>
  );
}
