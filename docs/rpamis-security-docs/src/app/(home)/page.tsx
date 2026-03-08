'use client';

import Link from 'next/link';
import { Cards, Card } from '@/components/card';
import { Callout } from '@/components/callout';
import { Accordions, Accordion } from '@/components/accordion';
import { Tabs, TabsList, TabsTrigger, TabsContent } from '@/components/tabs';
import { useEffect, useRef } from 'react';
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
        对于 <strong>JDK 17+</strong> 使用版本 <code>1.1.1</code>，
        对于 <strong>JDK 8-JDK 17</strong> 使用版本 <code>1.0.4</code>。
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
  const canvasRef = useRef<HTMLCanvasElement>(null);

  useEffect(() => {
    const canvas = canvasRef.current;
    if (!canvas) return;

    const ctx = canvas.getContext('2d');
    if (!ctx) return;

    // 设置画布尺寸
    const resizeCanvas = () => {
      canvas.width = window.innerWidth;
      canvas.height = window.innerHeight;
    };
    resizeCanvas();
    window.addEventListener('resize', resizeCanvas);

    // 球形点阵系统 - 优化性能版本
    const gridSize = 8; // 增大网格，减少粒子数量
    const dots: {
      x: number;
      y: number;
      size: number;
      opacity: number;
      phase: number;
      speed: number;
    }[] = [];

    // 初始化球形点阵
    const centerX = canvas.width * 0.75;
    const centerY = canvas.height * 0.35;
    const maxRadius = 280;

    for (let x = centerX - maxRadius; x < centerX + maxRadius; x += gridSize) {
      for (let y = centerY - maxRadius; y < centerY + maxRadius; y += gridSize) {
        const dx = x - centerX;
        const dy = y - centerY;
        const distance = Math.sqrt(dx * dx + dy * dy);

        // 只在球形区域内创建点
        if (distance < maxRadius) {
          // 计算球形效果的大小和不透明度
          const normalizedDistance = distance / maxRadius;
          const falloff = 1 - normalizedDistance;
          const sizeFalloff = Math.pow(falloff, 0.5);

          dots.push({
            x: x,
            y: y,
            size: (Math.random() * 2.5 + 1) * sizeFalloff, // 稍微增大点的尺寸
            opacity: Math.random() * 0.8 + 0.2, // 提高基础不透明度
            phase: Math.random() * Math.PI * 2,
            speed: Math.random() * 0.0012 + 0.0003, // 稍微降低速度
          });
        }
      }
    }

    // 绘制球形点阵（移除轨迹以提升性能）
    const drawDots = (time: number) => {
      dots.forEach((dot) => {
        // 波动效果（让点有微小的运动）
        const waveX = Math.sin(time * dot.speed + dot.phase) * 2;
        const waveY = Math.cos(time * dot.speed * 1.3 + dot.phase) * 1.5;
        const px = dot.x + waveX;
        const py = dot.y + waveY;

        // 呼吸效果
        const breathe = 0.85 + Math.sin(time * dot.speed + dot.phase) * 0.15;
        const size = dot.size * breathe;

        // 不透明度波动
        const opacityWave = 0.6 + Math.sin(time * dot.speed * 1.2 + dot.phase) * 0.4;
        const finalOpacity = dot.opacity * opacityWave;

        // 暖色调 - 中心更亮
        const hue = 30;
        const saturation = 85 + Math.sin(time * 0.0001 + dot.phase) * 10;

        ctx.beginPath();
        ctx.arc(px, py, size, 0, Math.PI * 2);
        ctx.fillStyle = `hsla(${hue}, ${saturation}%, 55%, ${finalOpacity})`;
        ctx.fill();
      });
    };

    // 绘制中心光晕（球形）
    const drawGlow = (time: number) => {
      const pulse = 0.7 + Math.sin(time * 0.0005) * 0.3;

      const gradient = ctx.createRadialGradient(
        centerX, centerY, 0,
        centerX, centerY, 400
      );
      gradient.addColorStop(0, `rgba(249, 115, 22, ${0.25 * pulse})`);
      gradient.addColorStop(0.3, `rgba(251, 146, 60, ${0.15 * pulse})`);
      gradient.addColorStop(0.6, `rgba(253, 224, 71, ${0.08 * pulse})`);
      gradient.addColorStop(1, 'rgba(251, 146, 60, 0)');

      ctx.fillStyle = gradient;
      ctx.fillRect(0, 0, canvas.width, canvas.height);
    };

    // 动画循环
    let startTime = Date.now();
    let lastTime = startTime;
    const animate = () => {
      const time = Date.now() - startTime;

      // 性能优化：使用 requestAnimationFrame 的原生时间戳
      const deltaTime = Date.now() - lastTime;
      lastTime = Date.now();

      ctx.clearRect(0, 0, canvas.width, canvas.height);

      // 绘制渐变背景
      const bgGradient = ctx.createLinearGradient(0, 0, canvas.width, canvas.height);
      bgGradient.addColorStop(0, 'rgba(249, 115, 22, 0.03)');
      bgGradient.addColorStop(0.5, 'rgba(251, 146, 60, 0.02)');
      bgGradient.addColorStop(1, 'rgba(253, 224, 71, 0.01)');
      ctx.fillStyle = bgGradient;
      ctx.fillRect(0, 0, canvas.width, canvas.height);

      // 绘制中心光晕
      drawGlow(time);

      // 绘制球形点阵
      drawDots(time);

      requestAnimationFrame(animate);
    };

    animate();

    return () => {
      window.removeEventListener('resize', resizeCanvas);
    };
  }, []);

  return (
    <div className="min-h-screen bg-gradient-to-b from-gray-50 to-white dark:from-gray-950 dark:to-gray-900 relative overflow-hidden">
      {/* Canvas 粒子背景 */}
      <canvas ref={canvasRef} className="absolute inset-0 z-0" />

      {/* 渐变光晕效果 */}
      <div className="absolute inset-0 z-0 pointer-events-none">
        <div className="absolute top-1/4 left-1/4 w-96 h-96 bg-blue-400 rounded-full blur-3xl opacity-20 animate-pulse"></div>
        <div className="absolute top-1/2 right-1/4 w-80 h-80 bg-purple-500 rounded-full blur-3xl opacity-20 animate-pulse" style={{animationDelay: '1s'}}></div>
        <div className="absolute bottom-1/4 left-1/2 w-72 h-72 bg-cyan-400 rounded-full blur-3xl opacity-20 animate-pulse" style={{animationDelay: '2s'}}></div>
      </div>

      <div className="container mx-auto px-4 py-12 relative z-10">
        {/* Hero Section */}
        <div className="text-center mb-16">
          <div className="inline-flex items-center gap-2 px-4 py-2 bg-blue-100 dark:bg-blue-900/30 text-blue-700 dark:text-blue-300 rounded-full text-sm font-medium mb-6 backdrop-blur-sm">
            <Zap className="size-4" />
            <span>企业级数据安全组件</span>
          </div>
          <h1 className="text-4xl md:text-6xl font-bold text-gray-900 dark:text-white mb-6 leading-tight">
            Rpamis-Security
          </h1>
          <p className="text-xl text-gray-600 dark:text-gray-300 mb-8 max-w-3xl mx-auto">
            一个基于 MyBatis 插件开发的企业级数据安全组件，
            提供<strong className="text-gray-900 dark:text-white">注解式数据脱敏</strong>和
            <strong className="text-gray-900 dark:text-white">数据库自动加解密</strong>功能
          </p>
          <div className="flex justify-center gap-4 mb-10">
            <span className="px-4 py-2 border border-gray-300 dark:border-gray-700 rounded-full text-sm font-medium bg-white/80 dark:bg-gray-800/80 backdrop-blur-sm">
              <Star className="size-4 inline-block mr-1 fill-yellow-400 text-yellow-400" />
              v1.1.1
            </span>
            <span className="px-4 py-2 border border-gray-300 dark:border-gray-700 rounded-full text-sm font-medium bg-white/80 dark:bg-gray-800/80 backdrop-blur-sm">
              <CheckCircle2 className="size-4 inline-block mr-1 text-green-500" />
              82% 单测覆盖率
            </span>
          </div>
          <div className="flex flex-wrap justify-center gap-4">
            <Link
              href="/docs/quick-start"
              className="inline-flex items-center px-6 py-3 bg-black dark:bg-white text-white dark:text-black rounded-lg hover:bg-gray-800 dark:hover:bg-gray-200 transition-colors font-medium transform hover:-translate-y-0.5"
            >
              快速开始
              <ArrowRight className="size-4 ml-2" />
            </Link>
            <a
              href="https://github.com/rpamis/rpamis-security"
              target="_blank"
              rel="noopener noreferrer"
              className="inline-flex items-center px-6 py-3 border border-gray-300 dark:border-gray-600 bg-white/80 dark:bg-gray-800/80 backdrop-blur-sm rounded-lg hover:bg-gray-100 dark:hover:bg-gray-700 transition-colors transform hover:-translate-y-0.5"
            >
              <Github className="size-4 mr-2" />
              GitHub
            </a>
          </div>
        </div>

        {/* Features Section */}
        <div className="mb-20">
          <h2 className="text-2xl md:text-4xl font-bold text-center mb-4 text-gray-900 dark:text-white">
            核心特性 ✨
          </h2>
          <p className="text-center text-gray-600 dark:text-gray-400 mb-12 max-w-2xl mx-auto">
            开箱即用的企业级数据安全解决方案，让您专注于业务开发
          </p>
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-5">
            {features.map((feature, index) => {
              // 彩色渐变方案
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
                  className="group relative bg-white/60 dark:bg-gray-800/60 backdrop-blur-lg rounded-xl border border-gray-200 dark:border-gray-700 p-5 transition-all duration-300 hover:border-transparent"
                >
                  <div className={`absolute inset-0 rounded-xl ${gradient} opacity-0 group-hover:opacity-10 transition-opacity`}></div>
                  <div className="relative">
                    <div className={`flex items-center justify-center w-10 h-10 rounded-lg ${gradient} text-white mb-4 group-hover:scale-110 transition-transform duration-300`}>
                      {feature.icon}
                    </div>
                    <h3 className="text-base font-semibold text-gray-900 dark:text-white mb-2">
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

        {/* Installation Section */}
        <div className="mb-20">
          <h2 className="text-2xl md:text-4xl font-bold text-center mb-4 text-gray-900 dark:text-white">
            快速安装 🚀
          </h2>
          <p className="text-center text-gray-600 dark:text-gray-400 mb-10 max-w-2xl mx-auto">
            只需几个简单步骤，即可将 Rpamis-Security 集成到您的项目中
          </p>

          <Callout type="info" title="💡 版本说明">
            <p>
              请根据您的 JDK 版本选择合适的组件版本。JDK 17+ 请使用 1.1.1 版本，JDK 8-17 请使用 1.0.4 版本。
            </p>
          </Callout>

          <div className="max-w-4xl mx-auto mt-8">
            <Tabs defaultValue="maven" className="rounded-xl border bg-white dark:bg-gray-800 overflow-hidden">
              <TabsList className="border-b bg-gray-50 dark:bg-gray-900">
                <TabsTrigger value="maven">Maven</TabsTrigger>
                <TabsTrigger value="gradle">Gradle</TabsTrigger>
              </TabsList>
              <TabsContent value="maven" className="p-6">
                <h3 className="text-lg font-semibold mb-4 text-gray-900 dark:text-white">Maven 依赖</h3>
                <div className="space-y-4">
                  <div>
                    <h4 className="text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                      ☕ JDK 17 及以上
                    </h4>
                    <pre className="bg-gray-100 dark:bg-gray-900 p-4 rounded-lg overflow-x-auto text-sm">
                      <code className="text-gray-800 dark:text-gray-200">
{`<dependency>
    <groupId>com.rpamis</groupId>
    <artifactId>rpamis-security-spring-boot-starter</artifactId>
    <version>1.1.1</version>
</dependency>`}
                      </code>
                    </pre>
                  </div>
                  <div>
                    <h4 className="text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                      📦 JDK 8-JDK 17
                    </h4>
                    <pre className="bg-gray-100 dark:bg-gray-900 p-4 rounded-lg overflow-x-auto text-sm">
                      <code className="text-gray-800 dark:text-gray-200">
{`<dependency>
    <groupId>com.rpamis</groupId>
    <artifactId>rpamis-security-spring-boot-starter</artifactId>
    <version>1.0.4</version>
</dependency>`}
                      </code>
                    </pre>
                  </div>
                </div>
              </TabsContent>
              <TabsContent value="gradle" className="p-6">
                <h3 className="text-lg font-semibold mb-4 text-gray-900 dark:text-white">Gradle 依赖</h3>
                <div className="space-y-4">
                  <div>
                    <h4 className="text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                      ☕ JDK 17 及以上
                    </h4>
                    <pre className="bg-gray-100 dark:bg-gray-900 p-4 rounded-lg overflow-x-auto text-sm">
                      <code className="text-gray-800 dark:text-gray-200">
{`implementation 'com.rpamis:rpamis-security-spring-boot-starter:1.1.1'`}
                      </code>
                    </pre>
                  </div>
                  <div>
                    <h4 className="text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                      📦 JDK 8-JDK 17
                    </h4>
                    <pre className="bg-gray-100 dark:bg-gray-900 p-4 rounded-lg overflow-x-auto text-sm">
                      <code className="text-gray-800 dark:text-gray-200">
{`implementation 'com.rpamis:rpamis-security-spring-boot-starter:1.0.4'`}
                      </code>
                    </pre>
                  </div>
                </div>
              </TabsContent>
            </Tabs>
          </div>
        </div>

        {/* FAQ Section */}
        <div className="mb-12">
          <h2 className="text-2xl md:text-4xl font-bold text-center mb-4 text-gray-900 dark:text-white">
            常见问题 ❓
          </h2>
          <p className="text-center text-gray-600 dark:text-gray-400 mb-10 max-w-2xl mx-auto">
            看看其他开发者都在问什么
          </p>
          <div className="max-w-3xl mx-auto">
            <Accordions type="single">
              {faqs.map((faq, index) => (
                <Accordion key={index} title={faq.title}>
                  {faq.content}
                </Accordion>
              ))}
            </Accordions>
          </div>
        </div>
      </div>
    </div>
  );
}
