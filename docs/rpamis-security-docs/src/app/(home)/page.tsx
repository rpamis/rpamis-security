'use client';

import Link from 'next/link';
import { Cards, Card } from '@/components/card';
import { Callout } from '@/components/callout';
import { Accordions, Accordion } from '@/components/accordion';
import { Tabs, TabsList, TabsTrigger, TabsContent } from '@/components/tabs';
import { CodeBlock, Pre } from 'fumadocs-ui/components/codeblock';
import { HeroCanvas } from '@/components/hero-canvas';
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
} from 'lucide-react';

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
        Rpamis-security 是一个基于 MyBatis 插件开发的安全组件，提供注解式的数据脱敏、数据库自动加解密功能，旨在提供优于同类组件的企业级数据安全解决方案。
      </p>
    ),
  },
  {
    title: '📦 它支持哪些JDK版本？',
    content: (
      <p>
        对于 <strong>JDK 17+</strong> 使用版本 <code>1.1.2</code>，
        对于 <strong>JDK 8-JDK 17</strong> 使用版本 <code>1.0.5</code>。
      </p>
    ),
  },
  {
    title: '✨ 它与同类项目相比有什么优势？',
    content: (
      <div>
        <p className="mb-2">
          支持任意实体类型、嵌套脱敏、动态SQL加解密、自定义加密算法、零影响配置、深拷贝设计等高可扩展性，
          并且有完整的 <strong>82% 单测覆盖率</strong>。
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



export default function HomePage() {
  return (
    <div className="min-h-screen bg-gradient-to-b from-gray-50 to-white dark:from-gray-950 dark:to-gray-900 relative overflow-hidden">
      {/* Canvas 粒子背景 */}
      <HeroCanvas />

      {/* 渐变光晕效果 - 与页面背景协调 */}
      <div className="absolute inset-0 z-0 pointer-events-none">
        <div className="absolute top-1/4 left-1/4 w-72 h-72 bg-blue-400 dark:bg-blue-600/30 rounded-full blur-3xl opacity-40 animate-flow"></div>
        <div className="absolute top-1/2 right-1/4 w-80 h-80 bg-purple-400 dark:bg-purple-600/30 rounded-full blur-3xl opacity-40 animate-flow" style={{ animationDelay: '1s' }}></div>
        <div className="absolute bottom-1/4 left-1/2 w-72 h-72 bg-cyan-400 dark:bg-cyan-600/30 rounded-full blur-3xl opacity-40 animate-flow" style={{ animationDelay: '2s' }}></div>
      </div>

      <div className="container mx-auto px-4 py-16 relative z-10">
        {/* Hero Section */}
        <div className="text-center mb-20">
          <div className="inline-flex items-center gap-2 px-4 py-2 bg-blue-100 dark:bg-blue-900/30 text-blue-700 dark:text-blue-300 rounded-full text-sm font-medium mb-8 backdrop-blur-sm">
            <Zap className="size-4" />
            <span>企业级数据安全组件</span>
          </div>
          <div className="flex flex-col items-center gap-4 mb-6 relative">
            {/* 蓝色粒子效果 - 位于 logo 底下 */}
            <div className="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 w-40 h-40 bg-blue-400 rounded-full blur-3xl opacity-20 animate-pulse"></div>
            <div className="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 w-32 h-32 bg-blue-500 rounded-full blur-2xl opacity-10 animate-pulse" style={{ animationDelay: '0.5s' }}></div>
            <img src="https://rpamis.github.io/rpamis-security/logo.png" alt="Rpamis-Security Logo" className="h-40 w-40 rounded-3xl relative z-10" />
            <h1 className="text-4xl md:text-6xl font-bold text-gray-900 dark:text-white leading-tight relative z-10">
              Rpamis-Security
            </h1>
          </div>
          <p className="text-xl text-gray-600 dark:text-gray-300 mb-10 max-w-3xl mx-auto leading-relaxed">
            一个基于 MyBatis 插件开发的企业级数据安全组件，
            提供<strong className="text-gray-900 dark:text-white">注解式数据脱敏</strong>和
            <strong className="text-gray-900 dark:text-white">数据库自动加解密</strong>功能
          </p>
          <div className="flex justify-center gap-4 mb-12 flex-wrap">
            <span className="px-4 py-2 border border-gray-300 dark:border-gray-700 rounded-full text-sm font-medium bg-white/80 dark:bg-gray-800/80 backdrop-blur-sm">
              <Star className="size-4 inline-block mr-1 fill-yellow-400 text-yellow-400" />
              v1.1.2
            </span>
            <span className="px-4 py-2 border border-gray-300 dark:border-gray-700 rounded-full text-sm font-medium bg-white/80 dark:bg-gray-800/80 backdrop-blur-sm">
              <CheckCircle2 className="size-4 inline-block mr-1 text-green-500" />
              82% 单测覆盖率
            </span>
            <span className="px-4 py-2 border border-gray-300 dark:border-gray-700 rounded-full text-sm font-medium bg-white/80 dark:bg-gray-800/80 backdrop-blur-sm">
              <CheckCircle2 className="size-4 inline-block mr-1 text-blue-500" />
              78+生产级单测场景
            </span>
          </div>
          <div className="flex flex-wrap justify-center gap-6">
            <Link
              href="/docs/quick-start"
              className="inline-flex items-center px-8 py-4 bg-black dark:bg-white text-white dark:text-black rounded-lg hover:bg-gray-800 dark:hover:bg-gray-200 transition-colors font-medium transform hover:-translate-y-0.5 shadow-lg hover:shadow-xl"
            >
              快速开始
              <ArrowRight className="size-4 ml-2" />
            </Link>
            <a
              href="https://github.com/rpamis/rpamis-security"
              target="_blank"
              rel="noopener noreferrer"
              className="inline-flex items-center px-8 py-4 border border-gray-300 dark:border-gray-600 bg-white/80 dark:bg-gray-800/80 backdrop-blur-sm rounded-lg hover:bg-gray-100 dark:hover:bg-gray-700 transition-colors transform hover:-translate-y-0.5 shadow-md hover:shadow-lg"
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
            {features.map((feature, index) => {
              const gradients = [
                'bg-gradient-to-br from-blue-500 to-purple-600',
                'bg-gradient-to-br from-purple-500 to-pink-600',
                'bg-gradient-to-br from-pink-500 to-orange-600',
                'bg-gradient-to-br from-orange-500 to-yellow-600',
                'bg-gradient-to-br from-green-500 to-blue-600',
                'bg-gradient-to-br from-teal-500 to-cyan-600',
                'bg-gradient-to-br from-cyan-500 to-blue-600',
                'bg-gradient-to-br from-indigo-500 to-purple-600',
              ];

              const gradient = gradients[index % gradients.length];

              return (
                <div
                  key={index}
                  className="group relative bg-white/80 dark:bg-gray-800/80 backdrop-blur-lg rounded-xl border border-gray-200 dark:border-gray-700 p-6 transition-all duration-300 hover:shadow-xl"
                >
                  <div className={`absolute inset-0 rounded-xl ${gradient} opacity-0 group-hover:opacity-10 transition-all duration-300`}></div>
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
                </div>
              );
            })}
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
            <div className="bg-white/80 dark:bg-gray-800/80 backdrop-blur-sm rounded-xl border border-gray-200 dark:border-gray-700 p-8 shadow-md transition-all duration-300 hover:shadow-lg">
              <h3 className="text-xl font-semibold mb-6 text-gray-900 dark:text-white">快速安装</h3>

              <div className="bg-blue-50 dark:bg-blue-900/20 border border-blue-200 dark:border-blue-800 rounded-lg p-4 mb-6">
                <div className="flex items-center gap-2 mb-2">
                  <div className="w-5 h-5 bg-blue-100 dark:bg-blue-800 rounded-full flex items-center justify-center">
                    <span className="text-blue-600 dark:text-blue-400 text-xs">💡</span>
                  </div>
                  <h4 className="text-sm font-medium text-blue-700 dark:text-blue-300">版本说明</h4>
                </div>
                <p className="text-sm text-blue-600 dark:text-blue-400">
                  请根据您的 JDK 版本选择合适的组件版本。JDK 17+ 请使用 1.1.2 版本，JDK 8-17 请使用 1.0.5 版本。
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
    <version>1.1.2</version>
</dependency>`}</code>
                      </pre>
                    </div>
                    <CopyButton code={`<dependency>
    <groupId>com.rpamis</groupId>
    <artifactId>rpamis-security-spring-boot-starter</artifactId>
    <version>1.1.2</version>
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
    <version>1.0.5</version>
</dependency>`}</code>
                      </pre>
                    </div>
                    <CopyButton code={`<dependency>
    <groupId>com.rpamis</groupId>
    <artifactId>rpamis-security-spring-boot-starter</artifactId>
    <version>1.0.5</version>
</dependency>`} />
                  </div>
                </div>
              </div>
            </div>

            {/* Annotations Usage Card */}
            <div className="bg-white/80 dark:bg-gray-800/80 backdrop-blur-sm rounded-xl border border-gray-200 dark:border-gray-700 p-8 shadow-md transition-all duration-300 hover:shadow-lg">
              <h3 className="text-xl font-semibold mb-6 text-gray-900 dark:text-white">注解使用</h3>

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
              {faqs.map((faq, index) => (
                <Accordion key={index} title={faq.title} className="border-b border-gray-200 dark:border-gray-700 last:border-b-0">
                  <div className="p-6">
                    {faq.content}
                  </div>
                </Accordion>
              ))}
              <Accordion title="🧪 测试场景覆盖情况如何？" className="border-b border-gray-200 dark:border-gray-700 last:border-b-0">
                <div className="p-6">
                  <p className="mb-4">
                    Rpamis-Security 包含 <strong>78 个生产级单测场景</strong>，覆盖了各种实际使用场景，确保组件在不同环境下的稳定性和可靠性。
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
            <div className="relative overflow-hidden bg-white/80 dark:bg-gray-800/80 backdrop-blur-sm rounded-2xl border border-gray-200 dark:border-gray-700 p-8 shadow-lg transition-all duration-300 group">
              <div className="absolute top-0 right-0 w-40 h-40 bg-blue-500/5 rounded-full blur-3xl group-hover:bg-blue-500/10 transition-all duration-500"></div>
              <div className="absolute bottom-0 left-0 w-40 h-40 bg-cyan-500/5 rounded-full blur-3xl group-hover:bg-cyan-500/10 transition-all duration-500"></div>

              <div className="relative">
                <div className="flex items-center gap-3 mb-6">
                  <div className="w-12 h-12 bg-gradient-to-br from-blue-500 to-cyan-500 rounded-xl flex items-center justify-center shadow-lg">
                    <Users className="size-6 text-white" />
                  </div>
                  <h3 className="text-2xl font-bold text-gray-900 dark:text-white">由您驱动</h3>
                </div>
                <p className="text-gray-600 dark:text-gray-300 mb-8 text-lg leading-relaxed">
                  Rpamis-Security 100% 由开源社区的热情驱动，欢迎您的贡献和反馈。
                </p>
                <div className="space-y-4 mb-8">
                  <div className="flex items-start gap-3 p-4 bg-gradient-to-r from-amber-50 to-yellow-50 dark:from-amber-900/20 dark:to-yellow-900/20 rounded-xl border border-amber-100 dark:border-amber-800/30 transition-all duration-300">
                    <div className="w-8 h-8 bg-gradient-to-br from-amber-500 to-yellow-500 rounded-full flex items-center justify-center flex-shrink-0 mt-0.5 shadow-sm">
                      <Star className="size-4 text-white fill-current" />
                    </div>
                    <div>
                      <p className="font-semibold text-gray-900 dark:text-white">Star 项目</p>
                      <p className="text-sm text-gray-600 dark:text-gray-400">简单快速的支持方式</p>
                    </div>
                  </div>
                  <div className="flex items-start gap-3 p-4 bg-gradient-to-r from-rose-50 to-pink-50 dark:from-rose-900/20 dark:to-pink-900/20 rounded-xl border border-rose-100 dark:border-rose-800/30 transition-all duration-300">
                    <div className="w-8 h-8 bg-gradient-to-br from-rose-500 to-pink-500 rounded-full flex items-center justify-center flex-shrink-0 mt-0.5 shadow-sm">
                      <span className="text-white text-sm">💡</span>
                    </div>
                    <div>
                      <p className="font-semibold text-gray-900 dark:text-white">提出 Issue</p>
                      <p className="text-sm text-gray-600 dark:text-gray-400">分享想法和反馈</p>
                    </div>
                  </div>
                  <div className="flex items-start gap-3 p-4 bg-gradient-to-r from-violet-50 to-indigo-50 dark:from-violet-900/20 dark:to-indigo-900/20 rounded-xl border border-violet-100 dark:border-violet-800/30 transition-all duration-300">
                    <div className="w-8 h-8 bg-gradient-to-br from-violet-500 to-indigo-500 rounded-full flex items-center justify-center flex-shrink-0 mt-0.5 shadow-sm">
                      <span className="text-white text-sm">🛠️</span>
                    </div>
                    <div>
                      <p className="font-semibold text-gray-900 dark:text-white">提交 PR</p>
                      <p className="text-sm text-gray-600 dark:text-gray-400">一起改进项目</p>
                    </div>
                  </div>
                </div>
                <div className="flex flex-wrap gap-4">
                  <a
                    href="https://github.com/rpamis/rpamis-security/stargazers"
                    target="_blank"
                    rel="noopener noreferrer"
                    className="inline-flex items-center gap-2 px-6 py-3 bg-gradient-to-r from-amber-400 to-orange-400 text-white rounded-xl font-medium hover:from-amber-500 hover:to-orange-500 transition-all duration-300 shadow-md"
                  >
                    <Star className="size-5 fill-current" />
                    Star 项目
                  </a>
                  <a
                    href="https://github.com/rpamis/rpamis-security"
                    target="_blank"
                    rel="noopener noreferrer"
                    className="inline-flex items-center gap-2 px-6 py-3 bg-gradient-to-r from-gray-600 to-gray-700 dark:from-gray-600 dark:to-gray-700 text-white rounded-xl font-medium hover:from-gray-700 hover:to-gray-800 transition-all duration-300 shadow-md"
                  >
                    <Github className="size-5" />
                    打开 GitHub
                  </a>
                </div>
              </div>
            </div>

            {/* Features Card */}
            <div className="relative overflow-hidden bg-white/80 dark:bg-gray-800/80 backdrop-blur-sm rounded-2xl border border-gray-200 dark:border-gray-700 p-8 shadow-lg transition-all duration-300 group">
              <div className="absolute top-0 right-0 w-40 h-40 bg-blue-500/5 rounded-full blur-3xl group-hover:bg-blue-500/10 transition-all duration-500"></div>
              <div className="absolute bottom-0 left-0 w-40 h-40 bg-green-500/5 rounded-full blur-3xl group-hover:bg-green-500/10 transition-all duration-500"></div>

              <div className="relative">
                <div className="flex items-center gap-3 mb-6">
                  <div className="w-12 h-12 bg-gradient-to-br from-blue-500 to-emerald-500 rounded-xl flex items-center justify-center shadow-lg">
                    <Shield className="size-6 text-white" />
                  </div>
                  <h3 className="text-2xl font-bold text-gray-900 dark:text-white">
                    企业级数据安全
                  </h3>
                </div>
                <p className="text-gray-600 dark:text-gray-300 mb-8 text-lg leading-relaxed">
                  为您的项目提供可靠的数据安全保障，让您专注于业务开发
                </p>
                <div className="space-y-4 mb-8">
                  <div className="flex items-start gap-3 p-4 bg-gradient-to-r from-emerald-50 to-teal-50 dark:from-emerald-900/20 dark:to-teal-900/20 rounded-xl border border-emerald-100 dark:border-emerald-800/30 transition-all duration-300">
                    <div className="w-8 h-8 bg-gradient-to-br from-emerald-500 to-teal-500 rounded-full flex items-center justify-center flex-shrink-0 mt-0.5 shadow-sm">
                      <CheckCircle2 className="size-4 text-white" />
                    </div>
                    <div>
                      <p className="font-semibold text-gray-900 dark:text-white">持续维护</p>
                      <p className="text-sm text-gray-600 dark:text-gray-400">积极更新，欢迎贡献</p>
                    </div>
                  </div>
                  <div className="flex items-start gap-3 p-4 bg-gradient-to-r from-sky-50 to-cyan-50 dark:from-sky-900/20 dark:to-cyan-900/20 rounded-xl border border-sky-100 dark:border-sky-800/30 transition-all duration-300">
                    <div className="w-8 h-8 bg-gradient-to-br from-sky-500 to-cyan-500 rounded-full flex items-center justify-center flex-shrink-0 mt-0.5 shadow-sm">
                      <CheckCircle2 className="size-4 text-white" />
                    </div>
                    <div>
                      <p className="font-semibold text-gray-900 dark:text-white">完全开源</p>
                      <p className="text-sm text-gray-600 dark:text-gray-400">开源项目，可在 GitHub 上获取</p>
                    </div>
                  </div>
                  <div className="flex items-start gap-3 p-4 bg-gradient-to-r from-fuchsia-50 to-pink-50 dark:from-fuchsia-900/20 dark:to-pink-900/20 rounded-xl border border-fuchsia-100 dark:border-fuchsia-800/30 transition-all duration-300">
                    <div className="w-8 h-8 bg-gradient-to-br from-fuchsia-500 to-pink-500 rounded-full flex items-center justify-center flex-shrink-0 mt-0.5 shadow-sm">
                      <CheckCircle2 className="size-4 text-white" />
                    </div>
                    <div>
                      <p className="font-semibold text-gray-900 dark:text-white">易于集成</p>
                      <p className="text-sm text-gray-600 dark:text-gray-400">几行代码即可集成到项目中</p>
                    </div>
                  </div>
                </div>
                <div className="flex flex-wrap gap-4">
                  <Link
                    href="/docs/quick-start"
                    className="inline-flex items-center gap-2 px-6 py-3 bg-gradient-to-r from-amber-400 to-orange-400 text-white rounded-xl font-medium hover:from-amber-500 hover:to-orange-500 transition-all duration-300 shadow-md"
                  >
                    <ArrowRight className="size-5" />
                    阅读文档
                  </Link>
                  <a
                    href="https://github.com/rpamis/rpamis-security"
                    target="_blank"
                    rel="noopener noreferrer"
                    className="inline-flex items-center gap-2 px-6 py-3 bg-gradient-to-r from-gray-800 to-gray-900 dark:from-gray-700 dark:to-gray-800 text-white rounded-xl font-medium hover:from-gray-900 hover:to-black dark:hover:from-gray-600 dark:hover:to-gray-700 transition-all duration-300 shadow-lg"
                  >
                    <Github className="size-5" />
                    打开 GitHub
                  </a>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Footer */}
        <footer className="border-t border-gray-200 dark:border-gray-800 pt-8 pb-8">
          <div className="text-center text-gray-600 dark:text-gray-400 text-sm">
            <p>© 2026 Rpamis. All rights reserved.</p>
          </div>
        </footer>
      </div>
    </div>
  );
}
