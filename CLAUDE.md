# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**rpamis-security** is a MyBatis-based security component providing enterprise-grade data security solutions including data desensitization and database encryption/decryption. It uses annotation-driven programming for seamless integration.

**Key Features:**
- Annotation-based security configuration
- 9 built-in desensitization rules (name, phone, ID card, email, bank card, etc.)
- SM4 encryption algorithm (Chinese national standard)
- Automatic encryption on database storage, decryption on retrieval
- Nested object support for desensitization and encryption/decryption
- Support for List, Map, and custom entity types
- No impact on global Jackson serialization behavior

## Project Structure

```
rpamis-security/
├── rpamis-security-annotation/      # Annotation definitions
├── rpamis-security-core/           # Core security implementation
├── rpamis-security-spring-boot-starter/  # Spring Boot auto-configuration
├── rpamis-security-test/           # Unit tests (78 test cases)
├── docs/rpamis-security-docs/      # Documentation website (Fumadocs + Next.js)
└── pom.xml                         # Maven parent POM
```

### Module Architecture

| Module | Purpose |
|--------|---------|
| `rpamis-security-annotation` | Defines annotations: `@Desensitizationed`, `@Masked`, `@NestedMasked`, `@SecurityField`, `MaskType` enum |
| `rpamis-security-core` | Core implementation: MyBatis interceptors, AOP aspects, algorithm implementations, type processors |
| `rpamis-security-spring-boot-starter` | Spring Boot auto-configuration and properties |
| `rpamis-security-test` | Comprehensive test suite |

## Build System

**Build Tool:** Maven 3.x
**Java Version:** JDK 17+ (for v1.1.1+), JDK 8-17 (for v1.0.4)

### Build Commands

```bash
# Build the entire project (with tests)
mvn clean install

# Build without tests
mvn clean install -DskipTests

# Run tests
mvn test

# Build with release profile (for Maven Central)
mvn clean install -Prelease

# Build with snapshot profile
mvn clean install -Psonatype

# Format code with Spring JavaFormat
mvn spring-javaformat:apply

# Check code style
mvn spring-javaformat:validate
```

## Maven Coordinates

**JDK 17+:**
```xml
<dependency>
    <groupId>com.rpamis</groupId>
    <artifactId>rpamis-security-spring-boot-starter</artifactId>
    <version>1.1.1</version>
</dependency>
```

**JDK 8-17:**
```xml
<dependency>
    <groupId>com.rpamis</groupId>
    <artifactId>rpamis-security-spring-boot-starter</artifactId>
    <version>1.0.4</version>
</dependency>
```

## Configuration

```yaml
rpamis:
  security:
    enable: true
    ignore-decrypt-failed: true
    desensitization-enable: true
    custom-pointcut: '@within(org.springframework.web.bind.annotation.RestController)'
    algorithm:
      active: sm4
      sm4:
        key: 2U43wVWjLgToKBzG  # 16 bytes required
        prefix: Your_CUSTOM_PREFIX_
```

## Key Annotations

| Annotation | Purpose |
|------------|---------|
| `@Desensitizationed` | Marks methods that need desensitization (AOP aspect trigger) |
| `@Masked` | Marks fields for desensitization with various mask types |
| `@NestedMasked` | Marks fields for nested desensitization (complex object graphs) |
| `@SecurityField` | Marks fields for automatic encryption/decryption via MyBatis |

## Mask Types

- `MaskType.NO_MASK` - No desensitization
- `MaskType.NAME_MASK` - Name desensitization
- `MaskType.PHONE_MASK` - Phone number desensitization
- `MaskType.IDCARD_MASK` - ID card desensitization
- `MaskType.EMAIL_MASK` - Email desensitization
- `MaskType.BANKCARD_MASK` - Bank card desensitization
- `MaskType.ADDRESS_MASK` - Address desensitization
- `MaskType.ALL_MASK` - Full desensitization
- `MaskType.CUSTOM_MASK` - Custom desensitization (start/end positions)

## Documentation Website

The documentation site is built with Fumadocs + Next.js in `docs/rpamis-security-docs/`:

```bash
cd docs/rpamis-security-docs
pnpm install
pnpm dev
```

## Testing

The project includes **78 unit test cases** covering:
- MyBatis-Plus operations (insert, update, delete, query)
- MyBatis custom SQL operations
- Desensitization scenarios (single entity, List, Map, nested objects)
- Encryption/decryption scenarios
- Edge cases and error handling

## Git Workflow

- **Main Branch:** `master`
- **Current Branch:** `docs/fumadocs-website` (for documentation updates)
- **PRs:** Target `master` branch
- **CI/CD:** GitHub Actions in `.github/workflows/`
  - `deploy-docs.yml` - Deploys documentation to GitHub Pages

## Important Technical Details

- **MyBatis Interceptors:** `MybatisEncryptInterceptor`, `MybatisDecryptInterceptor`, `MybatisDynamicSqlEncryptInterceptor`
- **AOP Aspect:** `DesensitizationAspect` for method-level desensitization
- **Deep Copy:** Encryption/decryption uses deep copy to avoid modifying original object references
- **Cache Isolation:** Prevents duplicate encryption and ensures thread safety
- **Prefix Identification:** Encrypted values are prefixed to identify them and prevent double-encryption