<p align="center">
  <img
    src="img/logo.png"
    alt="Logo"
    width="200"
  />
</p>

<h3 align="center">🔐 Rpamis-Security</h3>

[中文](README-zh.md) | [English](README.md) | [📖文档](https://rpamis.github.io/rpamis-security/)

[![Version](https://img.shields.io/maven-central/v/com.rpamis/rpamis-security-spring-boot-starter?style=flat-square)](https://central.sonatype.com/artifact/com.rpamis/rpamis-security-spring-boot-starter/1.1.2)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square)](LICENSE)
[![Java](https://img.shields.io/badge/JDK-8%2B-orange.svg?style=flat-square)](https://openjdk.org/)
[![Codecov](https://img.shields.io/codecov/c/gh/rpamis/rpamis-security?color=%23&style=flat-square)](https://app.codecov.io/github/rpamis/rpamis-security)
[![Deepwiki](https://deepwiki.com/badge.svg)](https://deepwiki.com/rpamis/rpamis-security)

---

<p align="center">
  <b>🎉 一个基于 MyBatis 插件开发的企业级数据安全组件</b><br>
  <sub>提供注解式数据脱敏和数据库自动加解密功能</sub>
</p>

---

## ✨ 核心特性

**🚀 开箱即用的企业级数据安全解决方案，让您专注于业务开发**

| 特性 | 描述 |
|------|------|
| 🎭 **数据脱敏** | 支持9种内置脱敏规则，灵活的自定义脱敏，支持任意实体类型 |
| 🔒 **数据库加解密** | 基于Mybatis插件的自动加解密，入库加密，出库解密 |
| 🛡️ **国密SM4** | 支持国家标准SM4对称加密算法，安全可靠 |
| 📦 **任意类型支持** | 支持任意实体、List、Map，无论是否具有泛型，均支持脱敏和加解密 |
| 🔄 **嵌套脱敏** | 支持多层嵌套实体的脱敏，满足复杂场景需求 |
| ⚡ **零影响配置** | 加解密失败支持原值返回，不影响业务正常运行 |
| 📋 **深拷贝设计** | 新增入库后不改变源对象引用，支持save操作后继续操作对象 |
| 🔧 **高可扩展性** | 支持自定义加密算法、加解密类型处理器、脱敏类型处理器 |

## 📦 快速安装

> 💡 **版本说明**：请根据您的 JDK 版本选择合适的组件版本
> - JDK 17+ 请使用 `1.1.2` 版本
> - JDK 8-17 请使用 `1.0.5` 版本

### ☕ JDK 17 及以上

```xml
<dependency>
    <groupId>com.rpamis</groupId>
    <artifactId>rpamis-security-spring-boot-starter</artifactId>
    <version>1.1.2</version>
</dependency>
```

### 📦 JDK 8 - JDK 17

```xml
<dependency>
    <groupId>com.rpamis</groupId>
    <artifactId>rpamis-security-spring-boot-starter</artifactId>
    <version>1.0.5</version>
</dependency>
```

## 🎯 快速使用

### 🔒 加解密注解

```java
public class User {
    private Long id;

    private String username;
    
    @SecurityField
    private String password;
}
```

### 🎭 脱敏注解

```java
public class User {
    private Long id;

    private String username;
    
    @Masked(type = MaskType.NAME_MASK)
    private String name;
}
```

## 📚 更多资源

| 资源 | 链接 |
|------|------|
| 📖 **完整文档** | [查看网站](https://rpamis.github.io/rpamis-security/) |
| 🚀 **快速开始** | [快速开始指南](https://rpamis.github.io/rpamis-security/docs/quick-start) |
| 📐 **架构设计** | [架构设计文档](https://rpamis.github.io/rpamis-security/docs/architecture) |
| 💡 **使用示例** | [示例代码](https://rpamis.github.io/rpamis-security/docs/examples) |
| 🔧 **API 参考** | [API 文档](https://rpamis.github.io/rpamis-security/docs/api) |
| 🧠 **原理解析** | [技术背景](https://benym.cn/notes/08-open-source-project/01-rpamis/03-security/02-rpamis-security-technical-background) / [原理分析](https://benym.cn/notes/08-open-source-project/01-rpamis/03-security/03-rpamis-security-principle-analysis) |

## 🏆 组件优势

| 特性 | Rpamis-Security | 同类项目 |
|------|-----------------|----------|
| 支持任意实体类型脱敏 | ✅ 支持 List、Map、无泛型实体 | ❌ 仅支持单一实体 |
| 支持嵌套脱敏 | ✅ 支持多层嵌套 | ❌ 不支持 |
| 自动加解密 | ✅ 支持动态 SQL | ❌ 功能受限 |
| 国密 SM4 | ✅ 支持 | 部分支持 |
| 加解密失败处理 | ✅ 支持原值返回 | ❌ 不支持 |
| 深拷贝设计 | ✅ 不改变源对象引用 | ❌ 不支持 |
| 单测覆盖率 | ✅ 82%+ / 78+ 场景 | ❌ 无 |

## 🤝 参与贡献

<p align="center">
  <b>Rpamis-Security 100% 由开源社区的热情驱动</b><br>
  <sub>欢迎您的贡献和反馈！查看 <a href="https://github.com/rpamis/rpamis-security/blob/master/.github/CONTRIBUTING.md">贡献指南</a> 了解更多</sub>
</p>

## 📄 开源协议

本项目基于 [Apache 2.0](LICENSE) 协议开源。

---

<p align="center">
  <sub>Made with ❤️ by <a href="https://github.com/rpamis">Rpamis Team</a></sub>
</p>
