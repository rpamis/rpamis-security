<p align="center">
  <img
    src="img/logo.png"
    alt="Logo"
    width="200"
  />
</p>
<h3 align="center">🔐 Rpamis-Security</h3>

<p align="center">
  <a href="README-zh.md">中文</a>
  &nbsp;|&nbsp;
  <a href="README.md">English</a>
  &nbsp;|&nbsp;
  <a href="https://docs.security.rpamis.com">📖 Documentation</a>
</p>

<p align="center">
  <a href="https://central.sonatype.com/artifact/com.rpamis/rpamis-security-spring-boot-starter/1.1.4">
    <img alt="maven" src="https://img.shields.io/maven-central/v/com.rpamis/rpamis-security-spring-boot-starter?style=flat-square">
  </a>
  <a href="https://www.apache.org/licenses/LICENSE-2.0">
    <img alt="license" src="https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square">
  </a>
  <a href="">
    <img alt="jdk" src="https://img.shields.io/badge/JDK-8%2B-orange.svg?style=flat-square">
  </a>
  <a href="https://app.codecov.io/github/rpamis/rpamis-security" > 
    <img alt="codecov" src="https://img.shields.io/codecov/c/gh/rpamis/rpamis-security?color=%23&style=flat-square"/> 
  </a>
  <a href="https://deepwiki.com/rpamis/rpamis-security">
    <img alt="deepwiki" src="https://deepwiki.com/badge.svg">
  </a>
</p>

---

<p align="center">
  <b>🎉 A MyBatis-based enterprise data security component</b><br>
  <sub>Providing annotation-based data masking and automatic database encryption/decryption</sub>
</p>

---

## ✨ Core Features

**🚀 Ready-to-use enterprise data security solution, letting you focus on business development**

- 🎭 **Data Masking** - Supports 9 built-in masking rules, flexible custom masking, supports any entity type
- 🔒 **Database Encryption** - MyBatis plugin-based automatic encryption/decryption, encrypt on insert, decrypt on select
- 🛡️ **SM4 Support** - Supports national standard SM4 symmetric encryption algorithm, secure and reliable
- 📦 **Any Type Support** - Supports any entity, List, Map, with or without generics, all support masking and encryption/decryption
- 🔄 **Nested Masking** - Supports multi-level nested entity masking, meeting complex scenario requirements
- ⚡ **Zero Impact** - Encryption/decryption failure supports returning original value, does not affect normal business operation
- 📋 **Deep Copy Design** - New insertions do not change source object reference, supports continued object operations after save
- 🔧 **High Extensibility** - Supports custom encryption algorithms, encryption/decryption type handlers, masking type handlers

## 📦 Quick Installation

> 💡 **Version Note**: Choose the appropriate version based on your JDK
> - JDK 17+ use version `1.1.4`
> - JDK 8-17 use version `1.0.7`

### ☕ JDK 17 and above

```xml
<dependency>
    <groupId>com.rpamis</groupId>
    <artifactId>rpamis-security-spring-boot-starter</artifactId>
    <version>1.1.4</version>
</dependency>
```

### 📦 JDK 8 - JDK 17

```xml
<dependency>
    <groupId>com.rpamis</groupId>
    <artifactId>rpamis-security-spring-boot-starter</artifactId>
    <version>1.0.7</version>
</dependency>
```

## 🎯 Quick Usage

### 🔒 Encryption Annotation

```java
public class User {
    private Long id;

    private String username;
    
    @SecurityField
    private String password;
}
```

### 🎭 Masking Annotation

```java
public class User {
    private Long id;

    private String username;
    
    @Masked(type = MaskType.NAME_MASK)
    private String name;
}
```

## 📚 Resources

| Resource | Link |
|----------|------|
| 📖 **Full Documentation** | [Documentation Website](https://docs.security.rpamis.com) |
| 🚀 **Quick Start** | [Quick Start Guide](https://docs.security.rpamis.com/docs/quick-start) |
| 📐 **Architecture** | [Architecture Design](https://docs.security.rpamis.com/docs/architecture) |
| 💡 **Examples** | [Code Examples](https://docs.security.rpamis.com/docs/examples) |
| 🔧 **API Reference** | [API Documentation](https://docs.security.rpamis.com/docs/api) |
| 🧠 **Principle Analysis** | [Technical Background](https://benym.cn/notes/08-open-source-project/01-rpamis/03-security/02-rpamis-security-technical-background) / [Principle Analysis](https://benym.cn/notes/08-open-source-project/01-rpamis/03-security/03-rpamis-security-principle-analysis) |
| 🤖 **Agent Skill** | [AI Agent Integration Guide](skill/rpamis-security/SKILL.md) - For AI-powered development assistance |

## 🏆 Advantages

| Feature | Rpamis-Security                   | Similar Projects |
|---------|-----------------------------------|------------------|
| Any entity type masking | ✅ List, Map, non-generic entities | ❌ Single entity only |
| Nested masking | ✅ Multi-level nesting             | ❌ Not supported |
| Auto encryption/decryption | ✅ Dynamic SQL support             | ❌ Limited functionality |
| SM4 encryption | ✅ Supported                       | Partial support |
| Encryption failure handling | ✅ Return original value           | ❌ Not supported |
| Deep copy design | ✅ Preserves source reference      | ❌ Not supported |
| Test coverage | ✅ 88%+ / 130+ scenarios           | ❌ None |

## 🤝 Contributing

<p align="center">
  <b>Rpamis-Security is 100% driven by the open source community</b><br>
  <sub>Contributions and feedback are welcome! Check out the <a href="https://github.com/rpamis/rpamis-security/blob/master/CONTRIBUTING.md">Contributing Guide</a> for more details</sub>
</p>

## 📄 License

This project is licensed under the [Apache 2.0](LICENSE) License.

---

<p align="center">
  <sub>Made with ❤️ by <a href="https://github.com/rpamis">Rpamis Team</a></sub>
</p>
