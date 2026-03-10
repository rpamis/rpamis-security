import { useState } from 'react';

/**
 * 复制按钮组件 - 用于复制代码片段到剪贴板
 *
 * 功能特性：
 * - 点击复制代码到剪贴板
 * - 复制成功后显示反馈图标
 * - 2秒后自动恢复原始状态
 * - 支持自定义样式类名
 * - 完整的错误处理
 *
 * @component
 * @example
 * ```tsx
 * // 基础使用
 * <CopyButton code="console.log('Hello World')" />
 *
 * // 带自定义样式
 * <CopyButton code="console.log('Hello World')" className="custom-button" />
 * ```
 */
interface CopyButtonProps {
  /**
   * 要复制到剪贴板的代码内容
   */
  code: string;

  /**
   * 可选的自定义样式类名
   */
  className?: string;
}

export function CopyButton({ code, className = '' }: CopyButtonProps) {
  // 复制状态管理：false 表示未复制，true 表示已复制
  const [isCopied, setIsCopied] = useState(false);

  /**
   * 处理复制按钮点击事件
   *
   * 实现逻辑：
   * 1. 尝试复制代码到剪贴板
   * 2. 显示复制成功状态
   * 3. 2秒后自动恢复原始状态
   * 4. 错误处理：捕获并处理复制过程中的异常
   */
  const handleCopy = async () => {
    try {
      // 复制代码到剪贴板
      await navigator.clipboard.writeText(code);

      // 设置复制成功状态
      setIsCopied(true);

      // 2秒后自动恢复原始状态
      setTimeout(() => {
        setIsCopied(false);
      }, 2000);
    } catch (error) {
      console.error('复制代码失败:', error);
      // 可以添加错误提示反馈，如 toast 通知
    }
  };

  return (
    <button
      onClick={handleCopy}
      className={`absolute top-2 right-2 z-10 p-1.5 bg-white/80 dark:bg-gray-800/80 rounded-md hover:bg-white dark:hover:bg-gray-700 transition-colors flex items-center justify-center ${className}`}
      title={isCopied ? '已复制' : '复制代码'}
      aria-label={isCopied ? '已复制' : '复制代码'}
    >
      {isCopied ? (
        // 复制成功图标
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="16"
          height="16"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
          strokeWidth="2"
          strokeLinecap="round"
          strokeLinejoin="round"
          className="w-4 h-4"
        >
          <polyline points="20 6 9 17 4 12"></polyline>
        </svg>
      ) : (
        // 原始复制图标
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="16"
          height="16"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
          strokeWidth="2"
          strokeLinecap="round"
          strokeLinejoin="round"
          className="w-4 h-4"
        >
          <rect x="9" y="9" width="13" height="13" rx="2" ry="2"></rect>
          <path d="M5 15H4a2 2 0 0 1-2-2V4a2 2 0 0 1 2-2h9a2 2 0 0 1 2 2v1"></path>
        </svg>
      )}
    </button>
  );
}
