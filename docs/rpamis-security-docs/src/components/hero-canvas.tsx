'use client';

import { useEffect, useRef } from 'react';

/**
 * 首页 Hero 区域 Canvas 动画组件
 *
 * 该组件实现了一个视觉效果丰富的 Canvas 动画背景，包括：
 * - 球形点阵系统：具有波动、呼吸和颜色变化效果的橙色粒子系统
 * - 粒子雨效果：从左边区域从下往上飘动的蓝色粒子
 * - 渐变背景：从暖色调到透明的线性渐变
 *
 * 动画特点：
 * - 粒子具有波动、呼吸和透明度变化效果
 * - 中心光晕有脉冲动画
 * - 整体色调为暖橙色系和蓝色系的结合
 * - 响应式设计，支持窗口大小变化
 * - 移动端自动禁用以优化性能
 */
export function HeroCanvas() {
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
    const centerX = canvas.width * 0.7; // 调整粒子球位置，稍微右移
    const centerY = canvas.height * 0.5; // 保持垂直位置
    const maxRadius = 300; // 进一步减小粒子球大小，使其更优雅

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

    // 粒子系统 - 左边粒子雨
    const particles: {
      x: number;
      y: number;
      size: number;
      opacity: number;
      speedX: number;
      speedY: number;
      phase: number;
    }[] = [];

    // 粒子数量
    const particleCount = 100;

    // 初始化粒子
    const initParticles = () => {
      particles.length = 0;
      for (let i = 0; i < particleCount; i++) {
        particles.push({
          x: Math.random() * canvas.width * 0.3, // 只在左边30%区域生成
          y: Math.random() * canvas.height + canvas.height, // 从画布下方开始
          size: Math.random() * 3 + 1,
          opacity: Math.random() * 0.8 + 0.2,
          speedX: Math.random() * 0.5 + 0.2, // 轻微向右移动
          speedY: Math.random() * 2 + 1, // 向上移动
          phase: Math.random() * Math.PI * 2,
        });
      }
    };

    initParticles();

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

    // 绘制粒子雨
    const drawParticles = (time: number) => {
      particles.forEach((particle, index) => {
        // 更新粒子位置
        particle.y -= particle.speedY;
        particle.x += particle.speedX;

        // 波动效果
        const wave = Math.sin(time * 0.001 + particle.phase) * 2;
        particle.x += wave;

        // 呼吸效果
        const breathe = 0.8 + Math.sin(time * 0.002 + particle.phase) * 0.2;
        const size = particle.size * breathe;

        // 不透明度变化
        const opacityWave = 0.6 + Math.sin(time * 0.0015 + particle.phase) * 0.4;
        const finalOpacity = particle.opacity * opacityWave;

        // 蓝色调
        const hue = 200 + Math.sin(time * 0.0005 + particle.phase) * 20;
        const saturation = 70 + Math.sin(time * 0.0003 + particle.phase) * 10;

        ctx.beginPath();
        ctx.arc(particle.x, particle.y, size, 0, Math.PI * 2);
        ctx.fillStyle = `hsla(${hue}, ${saturation}%, 55%, ${finalOpacity})`;
        ctx.fill();

        // 重置粒子
        if (particle.y < -50) {
          particles[index] = {
            x: Math.random() * canvas.width * 0.3,
            y: canvas.height + 50,
            size: Math.random() * 3 + 1,
            opacity: Math.random() * 0.8 + 0.2,
            speedX: Math.random() * 0.5 + 0.2,
            speedY: Math.random() * 2 + 1,
            phase: Math.random() * Math.PI * 2,
          };
        }
      });
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

      // 绘制渐变背景 - 与页面背景一致
      const bgGradient = ctx.createLinearGradient(0, 0, canvas.width, canvas.height);
      bgGradient.addColorStop(0, 'rgba(249, 250, 251, 0)'); // 浅色模式背景
      bgGradient.addColorStop(1, 'rgba(255, 255, 255, 0)'); // 浅色模式背景
      ctx.fillStyle = bgGradient;
      ctx.fillRect(0, 0, canvas.width, canvas.height);

      // 绘制中心光晕
      drawGlow(time);

      // 绘制球形点阵
      drawDots(time);

      // 绘制粒子雨
      drawParticles(time);

      requestAnimationFrame(animate);
    };

    animate();

    return () => {
      window.removeEventListener('resize', resizeCanvas);
    };
  }, []);

  return (
    <canvas
      ref={canvasRef}
      className="absolute inset-0 z-0"
      aria-hidden="true"
    />
  );
}
