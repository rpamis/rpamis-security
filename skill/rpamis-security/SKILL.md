---
name: "rpamis-security"
description: "MyBatis-based enterprise data security component for data masking and encryption/decryption. Invoke when implementing data masking, database encryption, SM4 encryption, or working with @Masked/@SecurityField annotations."
---

# Rpamis-Security Skill

Enterprise-grade MyBatis data security component providing annotation-based data masking and automatic database encryption/decryption with SM4 support.

## 🎯 When to Use This Skill

**Invoke this skill when you need to:**
- Implement data masking for sensitive information (names, phones, ID cards, emails, bank cards)
- Set up automatic database field encryption/decryption
- Work with SM4 (Chinese national standard) encryption algorithm
- Use `@Masked` or `@SecurityField` annotations
- Customize masking rules or encryption algorithms
- Handle nested entity masking scenarios
- Troubleshoot encryption/decryption issues
- Migrate from older versions or similar security frameworks

## 📦 Quick Integration

### Step 1: Add Maven Dependency

**For JDK 17+ (Recommended):**
```xml
<dependency>
    <groupId>com.rpamis</groupId>
    <artifactId>rpamis-security-spring-boot-starter</artifactId>
    <version>1.1.4</version>
</dependency>
```

**For JDK 8-17:**
```xml
<dependency>
    <groupId>com.rpamis</groupId>
    <artifactId>rpamis-security-spring-boot-starter</artifactId>
    <version>1.0.7</version>
</dependency>
```

### Step 2: Configure Application

**application.yml (Complete Configuration):**
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
        key: 1234567890123456
        prefix: ENC_SM4_
```

**Configuration Parameters Explained:**

| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `enable` | Boolean | false | Master switch for security component. When false, all features disabled |
| `ignore-decrypt-failed` | Boolean | true | Return original value on decryption failure instead of throwing exception |
| `desensitization-enable` | Boolean | true | Enable/disable masking AOP aspect |
| `custom-pointcut` | String | "" | Custom AOP pointcut expression (e.g., RestController) |
| `algorithm.active` | String | null | Active encryption algorithm (sm4) |
| `algorithm.sm4.key` | String | null | SM4 encryption key (must be 16 characters) |
| `algorithm.sm4.prefix` | String | ENC_SM4_ | Prefix to identify encrypted values |

### Step 3: Use Annotations

**For Data Masking:**
```java
import com.rpamis.security.annotation.Masked;
import com.rpamis.security.mask.MaskType;

public class UserVO {
    @Masked(type = MaskType.NAME_MASK)
    private String name;
    
    @Masked(type = MaskType.PHONE_MASK)
    private String phone;
    
    @Masked(type = MaskType.IDCARD_MASK)
    private String idCard;
}
```

**For Nested Masking:**
```java
import com.rpamis.security.annotation.Masked;
import com.rpamis.security.annotation.NestedMasked;
import com.rpamis.security.mask.MaskType;

public class UserVO {
    @Masked(type = MaskType.NAME_MASK)
    private String name;
    
    @NestedMasked
    private ContactInfoVO contactInfo;
}
```

**For Database Encryption:**
```java
import com.rpamis.security.annotation.SecurityField;

public class UserDO {
    private Long id;
    
    @SecurityField
    private String password;
    
    @SecurityField
    private String idCard;
}
```

## 🎭 Data Masking Guide

### Built-in Masking Types

| MaskType | Input Example | Output Example | Use Case |
|----------|---------------|----------------|----------|
| `NO_MASK` | 张三 | 张三 | No masking needed |
| `NAME_MASK` | 张三 | 张* | User names |
| `PHONE_MASK` | 13812345678 | 138****5678 | Phone numbers |
| `IDCARD_MASK` | 110101199001011234 | 110101********1234 | ID cards |
| `EMAIL_MASK` | example@domain.com | e****e@domain.com | Email addresses |
| `BANKCARD_MASK` | 6222021234567890 | 6222****7890 | Bank cards |
| `ADDRESS_MASK` | 北京市朝阳区 | 北京市*** | Addresses |
| `ALL_MASK` | 任意内容 | ****** | Complete masking |
| `CUSTOM_MASK` | ABCDEFG | AB###FG | Custom patterns |

### Custom Masking Example

```java
public class UserVO {
    @Masked(type = MaskType.CUSTOM_MASK, start = 2, end = 5, symbol = "#")
    private String customField;
}
```

**Result:** `ABCDEFG` → `AB###FG`

### Nested Masking

Use `@NestedMasked` annotation to mark nested entity fields that need masking:

```java
import com.rpamis.security.annotation.Masked;
import com.rpamis.security.annotation.NestedMasked;
import com.rpamis.security.mask.MaskType;

@Data
public class UserDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Masked(type = MaskType.NAME_MASK)
    private String name;
    
    @NestedMasked
    private ContactInfoVO contactInfo;
}

@Data
public class ContactInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Masked(type = MaskType.PHONE_MASK)
    private String phone;
    
    @Masked(type = MaskType.EMAIL_MASK)
    private String email;
    
    @NestedMasked
    private AddressVO address;
}

@Data
public class AddressVO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Masked(type = MaskType.ADDRESS_MASK)
    private String homeAddress;
    
    @Masked(type = MaskType.ADDRESS_MASK)
    private String officeAddress;
}
```

**Controller Example:**

```java
@RestController
@RequestMapping("/api")
public class UserDetailController {
    
    @PostMapping("/user/detail")
    @Desensitizationed
    public UserDetailVO getUserDetail() {
        AddressVO address = new AddressVO();
        address.setHomeAddress("北京市朝阳区建国路88号");
        address.setOfficeAddress("北京市海淀区中关村");
        
        ContactInfoVO contactInfo = new ContactInfoVO();
        contactInfo.setPhone("13812345678");
        contactInfo.setEmail("zhangsan@example.com");
        contactInfo.setAddress(address);
        
        UserDetailVO userDetail = new UserDetailVO();
        userDetail.setName("张三");
        userDetail.setContactInfo(contactInfo);
        return userDetail;
    }
}
```

**Output:**
```json
{
  "name": "张*",
  "contactInfo": {
    "phone": "138****5678",
    "email": "z****n@example.com",
    "address": {
      "homeAddress": "北京市朝阳区***",
      "officeAddress": "北京市海淀区***"
    }
  }
}
```

**Key Points:**
- Use `@NestedMasked` on nested entity fields
- Nested entities can also contain `@NestedMasked` fields (multi-level nesting)
- All `@Masked` fields in nested entities will be processed automatically
- Works with `@Desensitizationed` annotation on controller methods

### Enable Masking in Controller

Add `@Desensitizationed` annotation to controller methods:

```java
@RestController
@RequestMapping("/user")
public class UserController {
    
    @GetMapping("/info")
    @Desensitizationed
    public UserVO getUserInfo() {
        return userService.getUser();
    }
    
    @GetMapping("/list")
    @Desensitizationed
    public List<UserVO> getUserList() {
        return userService.getUserList();
    }
}
```

**Supported Return Types:**
- Single entity: `UserVO`
- List: `List<UserVO>`
- Map: `Map<String, UserVO>`
- Generic wrapper: `Response<UserVO>`
- Non-generic wrapper: `Response`

## 🔒 Database Encryption Guide

### How It Works

1. **Insert Operation:** Automatically encrypts `@SecurityField` annotated fields before saving
2. **Select Operation:** Automatically decrypts encrypted fields after querying
3. **Update Operation:** Encrypts new values, handles deep copy to preserve source object
4. **Deep Copy Design:** Source object reference preserved after save operations

### MyBatis-Plus Integration

```java
@TableName("user")
public class UserDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    @TableField(value = "name")
    @SecurityField
    private String name;
    
    @TableField(value = "id_card")
    @SecurityField
    private String idCard;
    
    @TableField(value = "phone")
    @SecurityField
    private String phone;
}

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    
    public void saveUser(UserDO user) {
        userMapper.insert(user);
        // user.name still equals original value (deep copy)
    }
    
    public UserDO getUser(Long id) {
        return userMapper.selectById(id);
        // name, idCard, phone are automatically decrypted
    }
}
```

### MyBatis XML Integration

```xml
<mapper namespace="com.example.mapper.UserMapper">
    <insert id="insert" parameterType="UserDO">
        INSERT INTO user (name, id_card, phone)
        VALUES (#{name}, #{idCard}, #{phone})
    </insert>
    
    <select id="selectById" resultType="UserDO">
        SELECT id, name, id_card, phone
        FROM user
        WHERE id = #{id}
    </select>
</mapper>
```

### Handling Encrypted Data Updates

```java
@PostMapping("/update")
public void updateUser() {
    UserDO user = new UserDO();
    user.setName("张三");
    user.setIdCard("500101111118181952");
    userMapper.insert(user);
    
    // Update with same object reference
    user.setName("李四");
    userMapper.updateById(user);
    
    UserDO result = userMapper.selectById(user.getId());
    // result.name equals "李四"
}
```

### Wrapper Update with Manual Encryption

```java
@PostMapping("/update/wrapper")
public void updateWithWrapper() {
    UpdateWrapper<UserDO> updateWrapper = new UpdateWrapper<>();
    updateWrapper.lambda()
        .set(UserDO::getName, securityAlgorithm.encrypt("李四"))
        .eq(UserDO::getId, userId);
    userMapper.update(null, updateWrapper);
}
```

## 🔧 Advanced Features

### Custom Encryption Algorithm

```java
@Component
public class CustomAlgorithmImpl implements SecurityAlgorithm {
    
    @Override
    public String encrypt(String content) {
        // Your custom encryption logic
        return encryptedContent;
    }
    
    @Override
    public String decrypt(String content) {
        // Your custom decryption logic
        return decryptedContent;
    }
}
```

### Custom Mask Function

```java
@Component
public class CustomMaskFunction implements MaskFunction {
    
    @Override
    public String mask(String content) {
        // Your custom masking logic
        return maskedContent;
    }
}
```

### Check if Data is Encrypted

```java
@Autowired
private SecurityUtils securityUtils;

public void checkEncryption(String value) {
    boolean isEncrypted = securityUtils.checkHasBeenEncrypted(value);
    // Returns true if value starts with ENC_SM4_
}
```

## 🧪 Testing Guide

### Unit Test Example

```java
@SpringBootTest
public class SecurityTest {
    
    @Autowired
    private UserMapper userMapper;
    
    @Test
    public void testInsertAndDecrypt() {
        UserDO user = new UserDO();
        user.setName("张三");
        user.setIdCard("500101111118181952");
        user.setPhone("12345678965");
        
        userMapper.insert(user);
        
        UserDO result = userMapper.selectById(user.getId());
        assertEquals("张三", result.getName());
        assertEquals("500101111118181952", result.getIdCard());
        assertEquals("12345678965", result.getPhone());
    }
    
    @Test
    @Desensitizationed
    public void testMasking() {
        UserVO user = userService.getUser();
        assertEquals("张*", user.getName());
        assertEquals("500***********1952", user.getIdCard());
    }
}
```

## ⚡ Performance Optimization

### 1. Batch Operations

```java
List<UserDO> userList = new ArrayList<>();
userList.add(user1);
userList.add(user2);
userService.saveBatch(userList);
```

### 2. Selective Field Encryption

Only encrypt sensitive fields:

```java
public class UserDO {
    private Long id;
    
    private String username;
    
    @SecurityField
    private String password;
    
    @SecurityField
    private String idCard;
    
    private Integer age;
}
```

### 3. Cache Isolation

The framework automatically isolates encryption/decryption cache to prevent interference:

```java
@Test
public void testCacheIsolate() {
    TestVersionV2DO encrypted = mapper.selectById(1);
    TestVersionDO normal = new TestVersionDO();
    normal.setName("张三");
    mapper.insert(normal);
    // normal.name is not affected by previous decryption
}
```

## 🛡️ Security Best Practices

### 1. Key Management

**DO NOT** hardcode encryption keys in source code:

```yaml
rpamis:
  security:
    algorithm:
      sm4:
        key: ${SM4_KEY:default-key-for-dev}
```

Use environment variables or secret management services.

### 2. Key Rotation

- Change encryption keys periodically
- Maintain key version history for backward compatibility
- Plan migration strategy for existing encrypted data

### 3. Access Control

- Limit access to encryption keys
- Use different keys for different environments (dev, test, prod)
- Audit key usage and access logs

### 4. Error Handling

```yaml
rpamis:
  security:
    ignore-decrypt-failed: true
```

Set to `false` in production to detect tampering attempts.

## 🔍 Troubleshooting

### Issue 1: Encryption Not Working

**Symptoms:** Data stored in plaintext

**Checklist:**
1. ✓ `rpamis.security.enable` is `true`
2. ✓ `@SecurityField` annotation present on field
3. ✓ Correct Maven dependency version
4. ✓ SM4 key configured (16 characters)

**Debug:**
```java
@Autowired
private SecurityAlgorithm securityAlgorithm;

String encrypted = securityAlgorithm.encrypt("test");
System.out.println(encrypted);
```

### Issue 2: Masking Not Applied

**Symptoms:** Sensitive data returned without masking

**Checklist:**
1. ✓ `rpamis.security.desensitization-enable` is `true`
2. ✓ `@Masked` annotation present on field
3. ✓ `@Desensitizationed` annotation on controller method
4. ✓ AOP enabled in Spring Boot (`@EnableAspectJAutoProxy`)

**Debug:**
```java
@GetMapping("/test")
@Desensitizationed
public UserVO test() {
    UserVO user = new UserVO();
    user.setName("张三");
    return user;
}
```

### Issue 3: SM4 Encryption Errors

**Symptoms:** Encryption/decryption exceptions

**Checklist:**
1. ✓ SM4 key is exactly 16 characters
2. ✓ SM4 algorithm supported in JDK (JDK 8u161+ or JDK 11+)
3. ✓ No special characters in key

**Solution:**
```yaml
rpamis:
  security:
    algorithm:
      sm4:
        key: 1234567890123456
```

### Issue 4: Decryption Returns Original Value

**Symptoms:** Encrypted data not decrypted

**Possible Causes:**
1. Data not encrypted (missing `@SecurityField` on insert)
2. Prefix mismatch (custom prefix configured)
3. Legacy data without prefix (version < 1.0.2)

**Solution:**
```yaml
rpamis:
  security:
    ignore-decrypt-failed: true
```

### Issue 5: Nested Decryption Not Working

**Symptoms:** Nested entity fields not decrypted

**Checklist:**
1. ✓ Nested entity has `@SecurityField` annotations
2. ✓ MyBatis result mapping correct

**Example:**
```java
public class OrderVO {
    private UserDO user;
}

public class UserDO {
    @SecurityField
    private String idCard;
}
```

## 📊 Migration Guide

### From Version 1.0.x to 1.1.x

**Breaking Changes:**
- JDK 17+ required for 1.1.4
- SM4 prefix added (`ENC_SM4_`)

**Migration Steps:**
1. Update Maven dependency to 1.1.4
2. Configure SM4 prefix in application.yml
3. Existing encrypted data automatically compatible
4. Test all encryption/decryption scenarios

### From Similar Frameworks

**From MyBatis-Plus Encrypt:**
1. Replace `@Encrypt` with `@SecurityField`
2. Configure SM4 key
3. Update MyBatis interceptor configuration

**From ShardingSphere Encrypt:**
1. Replace encrypt configuration with annotations
2. Migrate encrypted data (may require re-encryption)
3. Update application configuration

## 📚 Real-World Examples

### Example 1: User Management System

```java
@Entity
@TableName("sys_user")
public class SysUser {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String username;
    
    @SecurityField
    private String password;
    
    @SecurityField
    private String idCard;
    
    @SecurityField
    private String phone;
    
    @SecurityField
    private String email;
    
    private Integer status;
}

@RestController
@RequestMapping("/user")
public class UserController {
    
    @PostMapping("/register")
    public void register(@RequestBody SysUser user) {
        userService.save(user);
    }
    
    @GetMapping("/info/{id}")
    @Desensitizationed
    public UserVO getUserInfo(@PathVariable Long id) {
        SysUser user = userService.getById(id);
        return convertToVO(user);
    }
}

public class UserVO {
    private Long id;
    
    @Masked(type = MaskType.NAME_MASK)
    private String name;
    
    @Masked(type = MaskType.PHONE_MASK)
    private String phone;
    
    @Masked(type = MaskType.IDCARD_MASK)
    private String idCard;
    
    @Masked(type = MaskType.EMAIL_MASK)
    private String email;
}
```

### Example 2: Order System with Nested Encryption

```java
@TableName("orders")
public class Order {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String orderNo;
    
    @SecurityField
    private String customerPhone;
    
    @SecurityField
    private String customerAddress;
    
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<OrderItem> items;
}

public class OrderItem {
    private String productName;
    
    @SecurityField
    private String receiverPhone;
}

@GetMapping("/order/{id}")
@Desensitizationed
public OrderVO getOrder(@PathVariable Long id) {
    Order order = orderService.getById(id);
    return convertToVO(order);
}
```

## 🏗️ Project Structure

```
rpamis-security/
├── rpamis-security-annotation/
│   ├── @Masked - Masking annotation
│   ├── @NestedMasked - Nested masking annotation
│   ├── @SecurityField - Encryption annotation
│   ├── @Desensitizationed - Controller masking trigger
│   └── MaskType - Masking type enum
├── rpamis-security-core/
│   ├── algorithm/ - Encryption algorithms (SM4)
│   ├── aop/ - Masking AOP aspect
│   ├── mybatis/ - MyBatis interceptors
│   ├── field/ - Field processors
│   └── factory/ - Mask function factory
├── rpamis-security-spring-boot-starter/
│   ├── SecurityAutoConfiguration
│   └── SecurityProperties
└── rpamis-security-test/
    └── 130+ test scenarios (88%+ coverage)
```

## 📖 Resources

- 📖 [Full Documentation](https://docs.security.rpamis.com)
- 🚀 [Quick Start Guide](https://docs.security.rpamis.com/docs/quick-start)
- 📐 [Architecture Design](https://docs.security.rpamis.com/docs/architecture)
- 💡 [Code Examples](https://docs.security.rpamis.com/docs/examples)
- 🔧 [API Reference](https://docs.security.rpamis.com/docs/api)
- 🧠 [Technical Background](https://benym.cn/notes/08-open-source-project/01-rpamis/03-security/02-rpamis-security-technical-background)
- 🧠 [Principle Analysis](https://benym.cn/notes/08-open-source-project/01-rpamis/03-security/03-rpamis-security-principle-analysis)

## 🎓 Key Advantages

| Feature | Rpamis-Security              | Similar Projects |
|---------|------------------------------|------------------|
| Any entity type masking | ✅ List, Map, non-generic     | ❌ Single entity only |
| Nested masking | ✅ Multi-level nesting        | ❌ Not supported |
| Auto encryption/decryption | ✅ Dynamic SQL support        | ❌ Limited functionality |
| SM4 encryption | ✅ Supported                  | Partial support |
| Encryption failure handling | ✅ Return original value      | ❌ Not supported |
| Deep copy design | ✅ Preserves source reference | ❌ Not supported |
| Test coverage | ✅ 88%+ / 130+ scenarios       | ❌ None |

## 💡 Pro Tips

1. **Use `ignore-decrypt-failed: true` in development** for easier debugging
2. **Set `ignore-decrypt-failed: false` in production** to detect data tampering
3. **Always test with real database** to ensure encryption/decryption works correctly
4. **Use `@Desensitizationed` on controller methods** not service methods
5. **Keep encryption keys in environment variables** never in source code
6. **Test nested entity scenarios** thoroughly
7. **Monitor performance** for batch operations
8. **Document which fields are encrypted** for team collaboration

## 🚨 Common Pitfalls

1. **Forgetting `@Desensitizationed`** on controller methods
2. **Wrong key length** for SM4 (must be 16 characters)
3. **Hardcoding encryption keys** in source code
4. **Not testing decryption** after encryption
5. **Ignoring deep copy behavior** when updating same object
6. **Missing AOP configuration** in Spring Boot
7. **Using wrong version** for JDK compatibility

---

**Need help?** Check the [documentation](https://docs.security.rpamis.com) or [open an issue](https://github.com/rpamis/rpamis-security/issues).
