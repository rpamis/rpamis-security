'use client';

import Link from 'next/link';
import GradientText from '@/components/GradientText';
import SpotlightCard from '@/components/SpotlightCard';
import Dither from '@/components/Dither';
import PixelBlast from '@/components/PixelBlast';
import MagicBento from '@/components/MagicBento';
import CountUp from '@/components/CountUp';
import { getAssetPath } from '@/lib/asset-utils';
import { Cards, Card } from '@/components/card';
import { Callout } from '@/components/callout';
import { Accordions, Accordion } from '@/components/accordion';
import { Tabs, TabsList, TabsTrigger, TabsContent } from '@/components/tabs';
import { CodeBlock, Pre } from 'fumadocs-ui/components/codeblock';
import { Steps, Step } from 'fumadocs-ui/components/steps';
import dynamic from 'next/dynamic';
import { CopyButton } from '@/components/copy-button';
import {
  Shield,
  Lock,
  Key,
  Database,
  Layers,
  Settings,
  Cpu,
  Puzzle,
  Github,
  ArrowRight,
  Zap,
  CheckCircle2,
  Star,
  Users,
  Download,
  Code2,
} from 'lucide-react';
import { useMemo, useCallback, memo, useState, useEffect } from 'react';

// 懒加载 ColorBends 组件，避免阻塞首屏
const ColorBends = dynamic(() => import('@/components/ColorBends').then((mod) => ({
  default: mod.default,
})), {
  ssr: false,
  loading: () => null,
});

// 优化后的静态数据，使用 useMemo 缓存
const features = [
  {
    icon: <Shield className="size-5" />,
    title: '数据脱敏',
    description: '支持9种内置脱敏规则，灵活的自定义脱敏，支持任意实体类型',
  },
  {
    icon: <Database className="size-5" />,
    title: '数据库加解密',
    description: '基于Mybatis插件的自动加解密，入库加密，出库解密',
  },
  {
    icon: <Key className="size-5" />,
    title: '国密SM4',
    description: '支持国家标准SM4对称加密算法，安全可靠',
  },
  {
    icon: <Layers className="size-5" />,
    title: '任意类型支持',
    description: '支持任意实体、List、Map，无论是否具有泛型，均支持脱敏和加解密',
  },
  {
    icon: <Puzzle className="size-5" />,
    title: '嵌套脱敏',
    description: '支持多层嵌套实体的脱敏，满足复杂场景需求',
  },
  {
    icon: <Settings className="size-5" />,
    title: '零影响配置',
    description: '加解密失败支持原值返回，不影响业务正常运行',
  },
  {
    icon: <Cpu className="size-5" />,
    title: '深拷贝设计',
    description: '新增入库后不改变源对象引用，支持save操作后继续操作对象',
  },
  {
    icon: <Lock className="size-5" />,
    title: '高可扩展性',
    description: '支持自定义加密算法、加解密类型处理器、脱敏类型处理器',
  },
];

const faqs = [
  {
    title: '🤔 什么是 Rpamis-Security？',
    content: (
      <p>
        Rpamis-security 是基于 MyBatis 插件开发的安全组件，提供注解式的数据脱敏、数据库自动加解密功能，旨在提供优于同类组件的企业级数据安全解决方案。
      </p>
    ),
  },
  {
    title: '📦 它支持哪些JDK版本？',
    content: (
      <p>
        对于 <strong>JDK 17+</strong> 使用版本 <code>1.1.4</code>，
        对于 <strong>JDK 8-JDK 17</strong> 使用版本 <code>1.0.7</code>。
      </p>
    ),
  },
  {
    title: '✨ 它与同类项目相比有什么优势？',
    content: (
      <div>
        <p className="mb-2">
          支持任意实体类型、嵌套脱敏、动态SQL加解密、自定义加密算法、零影响配置、深拷贝设计等高可扩展性，
          并且有完整的 <strong>89% 单测覆盖率</strong>。
        </p>
        <ul className="list-disc pl-5 space-y-1">
          <li>✅ 任意实体类型脱敏</li>
          <li>✅ 任意实体类型嵌套脱敏</li>
          <li>✅ 动态 SQL 加解密</li>
          <li>✅ 国家标准加密算法 SM4</li>
          <li>✅ 深拷贝设计</li>
        </ul>
      </div>
    ),
  },
];

// 使用 memo 包装静态组件，避免不必要的重渲染
const FeatureCard = memo(({ feature, index }: { feature: any; index: number }) => {
  const gradients = useMemo(() => [
    'bg-gradient-to-br from-blue-500 to-purple-600',
    'bg-gradient-to-br from-purple-500 to-pink-600',
    'bg-gradient-to-br from-pink-500 to-orange-600',
    'bg-gradient-to-br from-orange-500 to-yellow-600',
    'bg-gradient-to-br from-green-500 to-blue-600',
    'bg-gradient-to-br from-teal-500 to-cyan-600',
    'bg-gradient-to-br from-cyan-500 to-blue-600',
    'bg-gradient-to-br from-indigo-500 to-purple-600',
  ], []);

  // Spotlight 颜色与渐变颜色保持一致，增强透明度让效果更明显
  const spotlightColors = useMemo(() => [
    'rgba(139, 92, 246, 0.4)',  // blue-purple
    'rgba(219, 39, 119, 0.4)',  // purple-pink
    'rgba(234, 88, 12, 0.4)',   // pink-orange
    'rgba(202, 138, 4, 0.4)',   // orange-yellow
    'rgba(37, 99, 235, 0.4)',   // green-blue
    'rgba(8, 145, 178, 0.4)',   // teal-cyan
    'rgba(37, 99, 235, 0.4)',   // cyan-blue
    'rgba(139, 92, 246, 0.4)',  // indigo-purple
  ], []);

  const gradient = useMemo(() => gradients[index % gradients.length], [gradients, index]);
  const spotlightColor = useMemo(() => spotlightColors[index % spotlightColors.length], [spotlightColors, index]);

  return (
    <SpotlightCard
      key={index}
      className="p-6 rounded-xl transition-all duration-300 hover:shadow-xl"
      spotlightColor={spotlightColor}
    >
      <div className="relative">
        <div className={`flex items-center justify-center w-12 h-12 rounded-lg ${gradient} text-white mb-5 group-hover:scale-110 transition-transform duration-300`}>
          {feature.icon}
        </div>
        <h3 className="text-lg font-semibold text-gray-900 dark:text-white mb-3">
          {feature.title}
        </h3>
        <p className="text-gray-600 dark:text-gray-400 text-sm leading-relaxed">
          {feature.description}
        </p>
      </div>
    </SpotlightCard>
  );
});

FeatureCard.displayName = 'FeatureCard';

const FAQItem = memo(({ faq, index }: { faq: any; index: number }) => {
  return (
    <Accordion key={index} title={faq.title} className="border-b border-gray-200 dark:border-gray-700 last:border-b-0">
      <div className="p-6">
        {faq.content}
      </div>
    </Accordion>
  );
});

FAQItem.displayName = 'FAQItem';

export default function HomePage() {
  // 检测当前主题
  const [isDarkTheme, setIsDarkTheme] = useState(true);

  useEffect(() => {
    // 初始检查 html 标签是否有 dark 类
    const checkTheme = () => {
      const html = document.documentElement;
      setIsDarkTheme(html.classList.contains('dark'));
    };

    checkTheme();

    // 监听主题变化（如果有主题切换功能的话）
    const observer = new MutationObserver(checkTheme);
    observer.observe(document.documentElement, {
      attributes: true,
      attributeFilter: ['class']
    });

    return () => observer.disconnect();
  }, []);

  // 缓存渐变数组，避免每次渲染重新创建
  const gradients = useMemo(() => [
    'bg-gradient-to-br from-blue-500 to-purple-600',
    'bg-gradient-to-br from-purple-500 to-pink-600',
    'bg-gradient-to-br from-pink-500 to-orange-600',
    'bg-gradient-to-br from-orange-500 to-yellow-600',
    'bg-gradient-to-br from-green-500 to-blue-600',
    'bg-gradient-to-br from-teal-500 to-cyan-600',
    'bg-gradient-to-br from-cyan-500 to-blue-600',
    'bg-gradient-to-br from-indigo-500 to-purple-600',
  ], []);

  // 使用 useCallback 优化事件处理
  const handleStarClick = useCallback(() => {
    window.open('https://github.com/rpamis/rpamis-security/stargazers', '_blank');
  }, []);

  const handleGitHubClick = useCallback(() => {
    window.open('https://github.com/rpamis/rpamis-security', '_blank');
  }, []);

  const memoizedFeatures = useMemo(() => features, []);
  const memoizedFaqs = useMemo(() => faqs, []);

  return (
    <div className="min-h-screen bg-gradient-to-b from-gray-50 to-white dark:from-gray-950 dark:to-gray-900 relative overflow-hidden">
      {/* ColorBends 动画背景 - 懒加载优化 */}
      <div className="hidden md:block absolute top-0 left-0 right-0 h-screen z-0">
        <div style={{ width: '100%', height: '100%', position: 'relative' }}>
          {/* @ts-expect-error - ColorBends组件是JSX文件，TypeScript类型推断不完整 */}
          <ColorBends
            rotation={0}
            speed={0.2}
            colors={["#215be4", "#e566ff", "#f14bac", "#ef864d"]}
            transparent
            autoRotate={0}
            scale={1}
            frequency={1}
            warpStrength={1}
            mouseInfluence={1}
            parallax={0.5}
            noise={0.1}
          />
          {/* 渐变遮罩，让动画边缘与页面背景自然衔接 */}
          <div className="absolute bottom-0 left-0 right-0 h-32 bg-gradient-to-t from-gray-50 to-transparent dark:from-gray-950 dark:to-transparent pointer-events-none"></div>
        </div>
      </div>


      <div className="container mx-auto px-4 py-16 relative z-10">
        {/* Hero Section */}
        <div className="text-center mb-20">
          <div className="inline-flex items-center gap-2 px-4 py-2 bg-blue-100 dark:bg-blue-900/30 text-blue-700 dark:text-blue-300 rounded-full text-sm font-medium mb-8 backdrop-blur-sm">
            <Zap className="size-4" />
            <span>企业级数据安全组件</span>
          </div>
          <div className="flex flex-col items-center gap-4 mb-6">
            <h1 className="text-4xl md:text-6xl font-bold text-gray-900 dark:text-white leading-tight">
              <GradientText
                colors={["#29d4ff", "#FF9FFC", "#B19EEF", "#29d4ff"]}
                animationSpeed={8}
                showBorder={false}
                direction="horizontal"
                pauseOnHover={true}
                yoyo={true}
                className="inline-block"
              >
                Rpamis-Security
              </GradientText>
            </h1>
          </div>
          <p className="text-xl text-gray-600 dark:text-gray-300 mb-10 max-w-3xl mx-auto leading-relaxed">
            基于 MyBatis 插件开发的企业级数据安全组件，
            提供<strong className="text-gray-900 dark:text-white">注解式数据脱敏</strong>和
            <strong className="text-gray-900 dark:text-white">数据库自动加解密</strong>功能
          </p>
          <div className="flex justify-center gap-4 mb-12 flex-wrap">
            <span className="px-4 py-2 border border-gray-300 dark:border-gray-700 rounded-full text-sm font-medium bg-white/80 dark:bg-gray-800/80 backdrop-blur-sm">
              <Star className="size-4 inline-block mr-1 fill-yellow-400 text-yellow-400" />
              v1.1.4
            </span>
            <span className="px-4 py-2 border border-gray-300 dark:border-gray-700 rounded-full text-sm font-medium bg-white/80 dark:bg-gray-800/80 backdrop-blur-sm">
              <CheckCircle2 className="size-4 inline-block mr-1 text-green-500" />
              89% 单测覆盖率
            </span>
            <span className="px-4 py-2 border border-gray-300 dark:border-gray-700 rounded-full text-sm font-medium bg-white/80 dark:bg-gray-800/80 backdrop-blur-sm">
              <CheckCircle2 className="size-4 inline-block mr-1 text-blue-500" />
              130+生产级单测场景
            </span>
          </div>
          <div className="flex flex-wrap justify-center gap-6">
            <Link
              href="/docs/quick-start"
              className="inline-flex items-center px-8 py-4 bg-black dark:bg-white text-white dark:text-black rounded-lg hover:bg-gray-800 dark:hover:bg-gray-200 transition-all duration-200 shadow-sm hover:shadow-md font-medium"
            >
              快速开始
              <ArrowRight className="size-4 ml-2" />
            </Link>
            <a
              href="https://github.com/rpamis/rpamis-security"
              target="_blank"
              rel="noopener noreferrer"
              className="inline-flex items-center px-8 py-4 border border-gray-300 dark:border-gray-600 bg-white/80 dark:bg-gray-800/80 backdrop-blur-sm rounded-lg hover:bg-gray-100 dark:hover:bg-gray-700 transition-all duration-200 shadow-sm hover:shadow-md"
              onClick={handleGitHubClick}
            >
              <Github className="size-4 mr-2" />
              GitHub
            </a>
          </div>
        </div>

        {/* Features Section */}
        <div className="mb-24">
          <div className="text-center mb-16">
            <h2 className="text-2xl md:text-4xl font-bold mb-6 text-gray-900 dark:text-white">
              核心特性 ✨
            </h2>
            <p className="text-gray-600 dark:text-gray-400 max-w-2xl mx-auto">
              开箱即用的企业级数据安全解决方案，让您专注于业务开发
            </p>
          </div>
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
            {memoizedFeatures.map((feature, index) => (
              <FeatureCard key={index} feature={feature} index={index} />
            ))}
          </div>
        </div>

        {/* Stats Section */}
        <div className="mb-24">
          <div className="text-center mb-12">
            <h2 className="text-2xl md:text-4xl font-bold mb-4 text-gray-900 dark:text-white">
              数据安全核心指标 ⚡
            </h2>
            <p className="text-gray-600 dark:text-gray-400 max-w-2xl mx-auto">
              生产级单测场景，全方位覆盖各种使用场景，保障您的数据安全
            </p>
          </div>

          <div className="relative">
            {/* @ts-ignore - MagicBento with custom cards */}
            <MagicBento
              textAutoHide={true}
              enableStars
              enableSpotlight
              enableBorderGlow={true}
              enableTilt={false}
              enableMagnetism={false}
              clickEffect
              spotlightRadius={400}
              particleCount={12}
              glowColor="132, 0, 255"
              disableAnimations={false}
              cards={[
                {
                  color: '#060010',
                  content: (
                    <div className="h-full flex flex-col justify-center items-start">
                      <div className="flex items-baseline justify-start">
                        <span className="stats-number">
                          {/* @ts-ignore - CountUp component */}
                          <CountUp from={0} to={130} separator="" duration={2} onStart={null} onEnd={null} />
                        </span>
                        <span className="text-5xl md:text-6xl font-bold text-theme-symbol ml-1">+</span>
                      </div>
                      <h3 className="stats-title">生产级单测场景</h3>
                      <p className="stats-desc">全面覆盖各种使用场景</p>
                    </div>
                  )
                },
                {
                  color: '#060010',
                  content: (
                    <div className="h-full flex flex-col justify-center items-start">
                      <div className="flex items-baseline justify-start">
                        <span className="stats-number">
                          {/* @ts-ignore - CountUp component */}
                          <CountUp from={0} to={9} separator="" duration={2} onStart={null} onEnd={null} />
                        </span>
                        <span className="text-3xl md:text-4xl font-bold text-theme-symbol ml-1">种</span>
                      </div>
                      <h3 className="stats-title">内置脱敏规则</h3>
                      <p className="stats-desc">满足各种脱敏需求</p>
                    </div>
                  )
                },
                {
                  color: '#060010',
                  content: (
                    <div className="h-full flex flex-col justify-center items-start">
                      <div className="flex items-baseline justify-start">
                        <span className="stats-number">
                          {/* @ts-ignore - CountUp component */}
                          <CountUp from={0} to={89} separator="" duration={2} onStart={null} onEnd={null} />
                        </span>
                        <span className="text-5xl md:text-6xl font-bold text-theme-symbol ml-1">%</span>
                      </div>
                      <h3 className="stats-title">单测覆盖率</h3>
                      <p className="stats-desc">高质量的代码保障</p>
                    </div>
                  )
                },
                {
                  color: '#060010',
                  content: (
                    <div className="h-full flex flex-col justify-center items-start">
                      <div className="flex items-baseline justify-start">
                        <span className="stats-number">
                          {/* @ts-ignore - CountUp component */}
                          <CountUp from={0} to={100} separator="" duration={2} onStart={null} onEnd={null} />
                        </span>
                        <span className="text-5xl md:text-6xl font-bold text-theme-symbol ml-1">%</span>
                      </div>
                      <h3 className="stats-title">免费开源</h3>
                      <p className="stats-desc">完全开源，社区驱动</p>
                    </div>
                  )
                }
              ]}
            />
          </div>
        </div>

        {/* Quick Start & Features Section */}
        <div className="mb-24">
          <div className="text-center mb-16">
            <h2 className="text-2xl md:text-4xl font-bold mb-6 text-gray-900 dark:text-white">
              快速接入 🚀
            </h2>
            <p className="text-gray-600 dark:text-gray-400 max-w-2xl mx-auto">
              只需几个简单步骤，即可将 Rpamis-Security 集成到您的项目中
            </p>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
            {/* Quick Installation Card */}
            <div className="relative overflow-hidden bg-white/80 dark:bg-gray-800/80 backdrop-blur-sm rounded-2xl border border-gray-200 dark:border-gray-700 p-8 shadow-lg transition-all duration-300 group">
              {/* PixelBlast 背景 */}
              <div className="absolute inset-0 z-0">
                <PixelBlast
                  variant="square"
                  pixelSize={4}
                  color={isDarkTheme ? "#f39649" : "#f39649"}
                  patternScale={2}
                  patternDensity={1}
                  enableRipples
                  rippleSpeed={0.3}
                  rippleThickness={0.1}
                  rippleIntensityScale={1}
                  speed={0.5}
                  transparent
                  edgeFade={0.25}
                />
              </div>

              <div className="relative z-10">
                <div className="flex items-center gap-3 mb-6">
                  <div className="w-12 h-12 bg-gradient-to-br from-amber-400 to-orange-500 rounded-xl flex items-center justify-center shadow-lg shadow-amber-500/25">
                    <Download className="size-6 text-white" />
                  </div>
                  <h3 className="text-2xl font-bold text-gray-900 dark:text-white">快速安装</h3>
                </div>

                <div className="bg-white/60 dark:bg-gray-800/60 border border-gray-200/50 dark:border-gray-700/50 rounded-lg p-4 mb-6 backdrop-blur-md shadow-sm">
                  <div className="flex items-center gap-2 mb-2">
                    <div className="w-5 h-5 bg-amber-100 dark:bg-amber-900/40 rounded-full flex items-center justify-center">
                      <span className="text-amber-600 dark:text-amber-400 text-xs">💡</span>
                    </div>
                    <h4 className="text-sm font-medium text-gray-700 dark:text-gray-300">版本说明</h4>
                  </div>
                  <p className="text-sm text-gray-600 dark:text-gray-400">
                    请根据您的 JDK 版本选择合适的组件版本。JDK 17+ 请使用 1.1.4 版本，JDK 8-17 请使用 1.0.7 版本。
                  </p>
                </div>

                <div className="space-y-6">
                  <div>
                    <h4 className="text-sm font-medium text-gray-700 dark:text-gray-300 mb-3">
                      ☕ JDK 17 及以上
                    </h4>
                    <div className="relative bg-gray-100 dark:bg-gray-900 rounded-lg overflow-hidden">
                      <div className="overflow-x-auto p-4">
                        <pre className="font-mono text-sm whitespace-pre">
                          <code className="text-gray-800 dark:text-gray-200">{`<dependency>
    <groupId>com.rpamis</groupId>
    <artifactId>rpamis-security-spring-boot-starter</artifactId>
    <version>1.1.4</version>
</dependency>`}</code>
                        </pre>
                      </div>
                      <CopyButton code={`<dependency>
    <groupId>com.rpamis</groupId>
    <artifactId>rpamis-security-spring-boot-starter</artifactId>
    <version>1.1.4</version>
</dependency>`} />
                    </div>
                  </div>
                  <div>
                    <h4 className="text-sm font-medium text-gray-700 dark:text-gray-300 mb-3">
                      📦 JDK 8-JDK 17
                    </h4>
                    <div className="relative bg-gray-100 dark:bg-gray-900 rounded-lg overflow-hidden">
                      <div className="overflow-x-auto p-4">
                        <pre className="font-mono text-sm whitespace-pre">
                          <code className="text-gray-800 dark:text-gray-200">{`<dependency>
    <groupId>com.rpamis</groupId>
    <artifactId>rpamis-security-spring-boot-starter</artifactId>
    <version>1.0.7</version>
</dependency>`}</code>
                        </pre>
                      </div>
                      <CopyButton code={`<dependency>
    <groupId>com.rpamis</groupId>
    <artifactId>rpamis-security-spring-boot-starter</artifactId>
    <version>1.0.7</version>
</dependency>`} />
                    </div>
                  </div>
                </div>
              </div>
            </div>

            {/* Annotations Usage Card */}
            <div className="relative overflow-hidden bg-white/80 dark:bg-gray-800/80 backdrop-blur-sm rounded-2xl border border-gray-200 dark:border-gray-700 p-8 shadow-lg transition-all duration-300 group">
              <div className="relative">
                <div className="flex items-center gap-3 mb-6">
                  <div className="w-12 h-12 bg-gradient-to-br from-rose-400 to-pink-500 rounded-xl flex items-center justify-center shadow-lg shadow-rose-500/25">
                    <Code2 className="size-6 text-white" />
                  </div>
                  <h3 className="text-2xl font-bold text-gray-900 dark:text-white">注解使用</h3>
                </div>

                <div className="space-y-6">
                  {/* 加解密注解示例 */}
                  <div>
                    <h4 className="text-sm font-medium text-gray-700 dark:text-gray-300 mb-3">
                      🔒 加解密注解
                    </h4>
                    <div className="bg-gray-100 dark:bg-gray-900 p-4 rounded-lg overflow-x-auto">
                      <pre className="font-mono text-sm whitespace-pre">
                        <code className="text-gray-800 dark:text-gray-200">{`public class User {
    private Long id;

    private String username;

    `}<span className="relative inline-block px-2 py-0.5 rounded-md transition-all duration-200 hover:shadow-md">
                            <span className="absolute inset-0 bg-gradient-to-r from-blue-100 to-purple-100 dark:from-blue-900/40 dark:to-purple-900/40 rounded-md transition-all duration-200 hover:from-blue-200 hover:to-purple-200 dark:hover:from-blue-800/50 dark:hover:to-purple-800/50"></span>
                            <span className="relative bg-clip-text text-transparent bg-gradient-to-r from-blue-700 to-purple-700 dark:from-blue-300 dark:to-purple-300 font-medium transition-all duration-200 hover:from-blue-800 hover:to-purple-800 dark:hover:from-blue-200 dark:hover:to-purple-200">@SecurityField</span>
                          </span>{`
    private String password;
}`}</code>
                      </pre>
                    </div>
                  </div>

                  {/* 脱敏注解示例 */}
                  <div>
                    <h4 className="text-sm font-medium text-gray-700 dark:text-gray-300 mb-3">
                      🎭 脱敏注解
                    </h4>
                    <div className="bg-gray-100 dark:bg-gray-900 p-4 rounded-lg overflow-x-auto">
                      <pre className="font-mono text-sm whitespace-pre">
                        <code className="text-gray-800 dark:text-gray-200">{`public class User {
    private Long id;

    private String username;

    `}<span className="relative inline-block px-2 py-0.5 rounded-md transition-all duration-200 hover:shadow-md">
                            <span className="absolute inset-0 bg-gradient-to-r from-orange-100 to-red-100 dark:from-orange-900/40 dark:to-red-900/40 rounded-md transition-all duration-200 hover:from-orange-200 hover:to-red-200 dark:hover:from-orange-800/50 dark:hover:to-red-800/50"></span>
                            <span className="relative bg-clip-text text-transparent bg-gradient-to-r from-orange-700 to-red-700 dark:from-orange-300 dark:to-red-300 font-medium transition-all duration-200 hover:from-orange-800 hover:to-red-800 dark:hover:from-orange-200 dark:hover:to-red-200">@Masked(type = MaskType.NAME_MASK)</span>
                          </span>{`
    private String name;
}`}</code>
                      </pre>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* FAQ Section */}
        <div className="mb-24">
          <div className="text-center mb-16">
            <h2 className="text-2xl md:text-4xl font-bold mb-6 text-gray-900 dark:text-white">
              常见问题 ❓
            </h2>
            <p className="text-gray-600 dark:text-gray-400 max-w-2xl mx-auto">
              看看其他开发者都在问什么
            </p>
          </div>
          <div className="max-w-3xl mx-auto bg-white/80 dark:bg-gray-800/80 backdrop-blur-lg rounded-lg overflow-hidden shadow-md transition-all duration-300 hover:shadow-lg">
            <Accordions type="single">
              {memoizedFaqs.map((faq, index) => (
                <FAQItem key={index} faq={faq} index={index} />
              ))}
              <Accordion title="🧪 测试场景覆盖情况如何？" className="border-b border-gray-200 dark:border-gray-700 last:border-b-0">
                <div className="p-6">
                  <p className="mb-4">
                    Rpamis-Security 包含 <strong>130 个生产级单测场景</strong>，覆盖了各种实际使用场景，确保组件在不同环境下的稳定性和可靠性。
                  </p>
                  <p>
                    测试场景包括但不限于：各种实体类型的脱敏、嵌套对象脱敏、不同加密算法的使用、异常处理、边界情况等，确保组件在生产环境中能够稳定运行。
                  </p>
                </div>
              </Accordion>
            </Accordions>
          </div>
        </div>

        {/* Community Section */}
        <div className="mb-24">
          <div className="text-center mb-16">
            <h2 className="text-2xl md:text-4xl font-bold mb-6 text-gray-900 dark:text-white">
              社区驱动的安全组件
            </h2>
            <p className="text-gray-600 dark:text-gray-400 max-w-2xl mx-auto">
              Rpamis-Security 由社区驱动，持续改进和优化
            </p>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
            {/* Community Card */}
            <div className="relative overflow-hidden bg-white/80 dark:bg-gray-800/80 backdrop-blur-sm rounded-2xl border border-gray-200 dark:border-gray-700 p-8 shadow-lg">

              <div className="relative z-10">
                <div className="flex items-center gap-3 mb-6">
                  <div className="w-12 h-12 bg-gradient-to-br from-blue-500 to-cyan-500 rounded-xl flex items-center justify-center shadow-lg shadow-blue-500/30">
                    <Users className="size-6 text-white" />
                  </div>
                  <h3 className="text-2xl font-bold text-gray-900 dark:text-white">由您驱动</h3>
                </div>
                <p className="text-gray-600 dark:text-gray-300 mb-6 text-lg leading-relaxed">
                  Rpamis-Security 100% 由开源社区的热情驱动，欢迎您的贡献和反馈。
                </p>
                <div className="space-y-3 mb-6">
                  <div className="flex items-center gap-3 p-3 rounded-lg bg-white/50 dark:bg-gray-900/30 backdrop-blur-sm border border-gray-100 dark:border-gray-700/50">
                    <div className="w-8 h-8 bg-gradient-to-br from-amber-400 to-orange-400 rounded-lg flex items-center justify-center shadow-sm">
                      <Star className="size-4 text-white" />
                    </div>
                    <div>
                      <h4 className="font-semibold text-gray-900 dark:text-white text-sm">Star 项目</h4>
                      <p className="text-xs text-gray-600 dark:text-gray-400">简单快速的支持方式</p>
                    </div>
                  </div>
                  <div className="flex items-center gap-3 p-3 rounded-lg bg-white/50 dark:bg-gray-900/30 backdrop-blur-sm border border-gray-100 dark:border-gray-700/50">
                    <div className="w-8 h-8 bg-gradient-to-br from-rose-400 to-pink-400 rounded-lg flex items-center justify-center shadow-sm">
                      <span className="text-white text-sm">💡</span>
                    </div>
                    <div>
                      <h4 className="font-semibold text-gray-900 dark:text-white text-sm">提出 Issue</h4>
                      <p className="text-xs text-gray-600 dark:text-gray-400">分享想法和反馈</p>
                    </div>
                  </div>
                  <div className="flex items-center gap-3 p-3 rounded-lg bg-white/50 dark:bg-gray-900/30 backdrop-blur-sm border border-gray-100 dark:border-gray-700/50">
                    <div className="w-8 h-8 bg-gradient-to-br from-indigo-400 to-blue-400 rounded-lg flex items-center justify-center shadow-sm">
                      <span className="text-white text-sm">🛠️</span>
                    </div>
                    <div>
                      <h4 className="font-semibold text-gray-900 dark:text-white text-sm">提交 PR</h4>
                      <p className="text-xs text-gray-600 dark:text-gray-400">一起改进项目</p>
                    </div>
                  </div>
                </div>
                <div className="flex flex-wrap gap-3">
                  <a
                    href="https://github.com/rpamis/rpamis-security"
                    target="_blank"
                    rel="noopener noreferrer"
                    className="inline-flex items-center gap-2 px-4 py-2 bg-gray-900 dark:bg-white text-white dark:text-gray-900 rounded-full font-medium hover:bg-gray-800 dark:hover:bg-gray-100 transition-all duration-200 shadow-sm hover:shadow-md"
                    onClick={handleStarClick}
                  >
                    <Star className="size-4 fill-current" />
                    Star 项目
                  </a>
                </div>
              </div>
            </div>

            {/* Features Card */}
            <div className="relative overflow-hidden bg-white/80 dark:bg-gray-800/80 backdrop-blur-sm rounded-2xl border border-gray-200 dark:border-gray-700 p-8 shadow-lg">
              <div className="absolute inset-0 z-0" style={{ width: '100%', height: '100%', opacity: isDarkTheme ? 1 : 0.15 }}>
                <Dither
                  key="dither-background"
                  waveColor={isDarkTheme ? [0.4980392156862745, 0.5882352941176471, 0.9411764705882353] : [0.6, 0.7, 0.95]}
                  disableAnimation={false}
                  enableMouseInteraction={false}
                  mouseRadius={0.3}
                  colorNum={4}
                  pixelSize={2}
                  waveAmplitude={isDarkTheme ? 0.05 : 0.02}
                  waveFrequency={2.5}
                  waveSpeed={0.05}
                />
              </div>

              <div className="relative z-10">
                <div className="flex items-center gap-3 mb-6">
                  <div className="w-12 h-12 bg-gradient-to-br from-blue-500 to-emerald-500 rounded-xl flex items-center justify-center shadow-lg shadow-emerald-500/30">
                    <Shield className="size-6 text-white" />
                  </div>
                  <h3 className="text-2xl font-bold text-gray-900 dark:text-white">
                    企业级数据安全
                  </h3>
                </div>
                <p className="text-gray-600 dark:text-gray-300 mb-6 text-lg leading-relaxed">
                  为您的项目提供可靠的数据安全保障，让您专注于业务开发
                </p>
                <div className="space-y-3 mb-6">
                  <div className="flex items-center gap-3 p-3 rounded-lg bg-white/50 dark:bg-gray-900/30 backdrop-blur-sm border border-gray-100 dark:border-gray-700/50">
                    <div className="w-8 h-8 bg-gradient-to-br from-emerald-400 to-teal-400 rounded-lg flex items-center justify-center shadow-sm">
                      <CheckCircle2 className="size-4 text-white" />
                    </div>
                    <div>
                      <h4 className="font-semibold text-gray-900 dark:text-white text-sm">持续维护</h4>
                      <p className="text-xs text-gray-600 dark:text-gray-400">积极更新，欢迎贡献</p>
                    </div>
                  </div>
                  <div className="flex items-center gap-3 p-3 rounded-lg bg-white/50 dark:bg-gray-900/30 backdrop-blur-sm border border-gray-100 dark:border-gray-700/50">
                    <div className="w-8 h-8 bg-gradient-to-br from-sky-400 to-cyan-400 rounded-lg flex items-center justify-center shadow-sm">
                      <CheckCircle2 className="size-4 text-white" />
                    </div>
                    <div>
                      <h4 className="font-semibold text-gray-900 dark:text-white text-sm">完全开源</h4>
                      <p className="text-xs text-gray-600 dark:text-gray-400">开源项目，可在 GitHub 上获取</p>
                    </div>
                  </div>
                  <div className="flex items-center gap-3 p-3 rounded-lg bg-white/50 dark:bg-gray-900/30 backdrop-blur-sm border border-gray-100 dark:border-gray-700/50">
                    <div className="w-8 h-8 bg-gradient-to-br from-fuchsia-400 to-pink-400 rounded-lg flex items-center justify-center shadow-sm">
                      <CheckCircle2 className="size-4 text-white" />
                    </div>
                    <div>
                      <h4 className="font-semibold text-gray-900 dark:text-white text-sm">易于集成</h4>
                      <p className="text-xs text-gray-600 dark:text-gray-400">几行代码即可集成到项目中</p>
                    </div>
                  </div>
                </div>
                <div className="flex flex-wrap gap-3">
                  <Link
                    href="/docs/quick-start"
                    className="inline-flex items-center gap-2 px-4 py-2 bg-gray-900 dark:bg-white text-white dark:text-gray-900 rounded-full font-medium hover:bg-gray-800 dark:hover:bg-gray-100 transition-all duration-200 shadow-sm hover:shadow-md"
                  >
                    <ArrowRight className="size-4" />
                    阅读文档
                  </Link>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Footer */}
        <footer className="border-t border-gray-200 dark:border-gray-800 pt-8 pb-8">
          <div className="text-center text-gray-600 dark:text-gray-400 text-sm">
            <p>© 2023-2026 Rpamis. All rights reserved.</p>
          </div>
        </footer>
      </div>
    </div>
  );
}
