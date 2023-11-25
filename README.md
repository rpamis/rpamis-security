## Rpamis-security

🎄Rpamis-security项目是一个基于Mybatis插件开发的安全组件，旨在提供更优于市面上组件的脱敏、加解密落库等企业数据安全解决方案。组件提供注解式编程方式，开发者只需要对需要处理的字段或方法加上对应注解，无需关心安全相关需求，由组件全自动完成脱敏、加解密等功能

<p align="center">
  <a href="https://central.sonatype.com/artifact/com.rpamis/rpamis-security-spring-boot-starter/1.0.0">
    <img alt="maven" src="https://img.shields.io/maven-central/v/com.rpamis/rpamis-security?style=flat-square">
  </a>

  <a href="https://www.apache.org/licenses/LICENSE-2.0">
    <img alt="code style" src="https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square">
  </a>
</p>

-------------------------------------------------------------------------------

[**中文**](README.md)|[**English**](README-EN.md)

-------------------------------------------------------------------------------

### 快速开始

SpringBoot项目接入方式

```xml
<dependency>
    <groupId>com.rpamis</groupId>
    <artifactId>rpamis-security-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```
yml配置

```yaml
rpamis:
  # rpamis-security配置
  security:
    # 是否开启安全组件，落库加密，出库脱密，如果不指定加密算法，则默认返回原值
    # 当此开关为false时，无论脱敏切面是否开启，均不生效
    enable: true
    # 加密算法类型，内置sm4，可自行扩展
    algorithm: sm4
    # 加密算法密钥，需要自己生成，满足16位即可，下面只是样例
    sm4key: 2U43wVWjLgToKBzG
    # 忽略解密失败，如果解密失败则返回原值，否则抛出异常，如果不填写默认true
    ignore-decrypt-failed: true
    # 是否开启脱敏切面
    desensitization-enable: true
```

组件特点

| rpamis-security                                          | 组件优势                                                     | 同类项目                                                     |
| -------------------------------------------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 支持任意实体类型脱敏                                     | ✅自定义实体、List、Map，无论是否具有实体泛型，**只要返回值中含有脱敏注解，均支持脱敏**，**非JackSon序列化式方案，不影响全局JackSon输出行为** | **❌仅支持单一实体脱敏，当不指定泛型时无法脱敏**，**Jackson序列化式方案，可能影响JackSon输出行为** |
| 支持任意实体类型嵌套脱敏                                 | ✅对于标注有嵌套脱敏注解的实体，其内部自定义实体、List、Map，无论是否具有实体泛型，**只要返回值中含有脱敏注解，均支持脱敏** | **❌不支持嵌套脱敏**                                          |
| 支持任意实体类型落库数据自动加解密                       | ✅对于**任意标注有加密字段的实体**，在进入Mybatis/MybatisPlus落库时自动进行加密，**在数据出库时自动进行解密，支持动态SQL加解密** | ❌仅支持单一实体自动加解密，**无法支持List、Map内含多实体自动加解密**，**无法支持动态SQL加解密** |
| 支持国家标准加密算法Sm4                                  | 支持国密Sm4对称加密算法，支持扩展                            | sm2/sm3/sm4/md5等多种算法                                    |
| 脱敏、加解密多项选择可配置                               | ✅**支持脱敏、加解密开关、支持加解密失败0影响**               | **❌不支持**                                                  |
| 新增入库后不改变源对象引用                               | ✅**支持，加解密过程为深拷贝，支持save操作后继续操作对象，且对象引用不被加密** | **❌不支持**                                                  |
| 新增后，如果修改同一个对象引用，再进行更新，能够正常加密 | **支持**                                                     | **支持**                                                     |
| 可拓展式加密算法、加解密类型处理器、脱敏类型处理器       | **✅支持**                                                    | **❌不支持**                                                  |
| 自定义脱敏标识，起始位置，结束位置                       | ✅**支持**                                                    | ❌**不支持**                                                  |
| 完整的单测用例                                           | ✅**给出完整的单测用例，单测覆盖率达80%(含get/set)**          | ❌**无**                                                      |

## 单测覆盖率

![](/img/rpamis-security-cover.png)

## 使用方法

### 内置脱敏规则

组件内置了9种脱敏规则

- `MaskType.NO_MASK`-不脱敏
- `MaskType.NAME_MASK`-姓名脱敏
- `MaskType.PHONE_MASK`-电话脱敏
- `MaskType.IDCARD_MASK`-身份证脱敏
- `MaskType.EMAIL_MASK`-邮箱脱敏
- `MaskType.BANKCARD_MASK`-银行卡脱敏
- `MaskType.ADDRESS_MASK`-地址脱敏
- `MaskType.ALL_MASK`-全脱敏
- `MaskType.CUSTOM_MASK`-自定义脱敏

所有脱敏规则均支持自定义脱敏标识符，默认为*，其中自定义脱敏支持用户选择脱敏字段的开始位置和结束位置

### 脱敏使用-单一脱敏

对于需要脱敏的字段，使用`@Masked`进行标识

如以下实体

```java
@Data
public class TestVO implements Serializable {

    private static final long serialVersionUID = 1142843493987112387L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 姓名
     */
    @Masked(type = MaskType.NAME_MASK)
    private String name;

    /**
     * 身份证号
     */
    @Masked(type = MaskType.IDCARD_MASK)
    private String idCard;

    /**
     * 手机号
     */
    @Masked(type = MaskType.PHONE_MASK)
    private String phone;

    /**
     * 自定义标识字段
     */
    @Masked(type = MaskType.CUSTOM_MASK, start = 2, end = 5, symbol = "#")
    private String customFiled;
}
```

在`Controller`层标注`@Desensitizationed`注解，标识方法级的脱敏

如不包含该注解即使实体类中含有脱敏注解，在返回前端时将不会自动脱敏，用于更细粒度的脱敏控制

如下

```java
/**
 * 获取脱敏数据-base类型
 *
 * @return TestVO
 */
@PostMapping("/baseType")
@Desensitizationed
public TestVO testBase() {
    TestVersionDO result = testVersionDOService.testDesensite();
    return RpamisBeanUtil.copy(result, TestVO.class);
}
```

### 脱敏使用-嵌套脱敏

嵌套脱敏用于脱敏实体字段中同样含有脱敏实体的情况，对于需要嵌套脱敏的字段，用`@NestedMasked`注解进行标注

样例实体类如下

```java
@Data
public class TestNestVO implements Serializable {

    private static final long serialVersionUID = -5559148350211559748L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 姓名
     */
    @Masked(type = MaskType.NAME_MASK)
    private String name;

    /**
     * 嵌套校验-直接返回实体
     */
    @NestedMasked
    private TestVO testVO;

    /**
     * 嵌套校验-返回List
     */
    @NestedMasked
    private List<TestVO> testVOList;

    /**
     * 嵌套校验-返回Map
     */
    @NestedMasked
    private Map<String, TestVO> testVOMap;
}
```

上述实体将脱敏name，以及testVO、testVOList、testVOMap实体中所有被`@NestedMasked`标注的字段

外层使用方式和单一脱敏保持一致

如下

```java
/**
 * 获取脱敏数据-嵌套脱敏-base
 *
 * @return TestNestVO
 */
@PostMapping("/nest/baseType")
@Desensitizationed
public TestNestVO testNestVO() {
    TestVersionDO testVersionDO = testVersionDOService.testDesensite();
    TestVO test = RpamisBeanUtil.copy(testVersionDO, TestVO.class);
    TestNestVO testNestVO = new TestNestVO();
    testNestVO.setId(1L);
    testNestVO.setName("张三");
    testNestVO.setTestVO(test);
    return testNestVO;
}
```

### 加解密使用

对于传递给`Mybatis Mapper`的实体或`Mybatis Plus`内置`Insert/update/Wrapper`等操作，字段将在落库时自动加密

对于`Mybatis/Mybatis Plus`的查询操作，加密字段出库时将自动脱密

加解密字段通过`@SecurityField`注解进行标注即可，当yml配置开启加解密后，无需结合其余注解，过程全自动化

实体如下

```java
@TableName(value ="test_version")
@Data
public class TestVersionDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 姓名
     */
    @TableField(value = "name")
    @SecurityField
    private String name;

    /**
     * 身份证号
     */
    @TableField(value = "id_card")
    @SecurityField
    private String idCard;

    /**
     * 电话
     */
    @TableField(value = "phone")
    @SecurityField
    private String phone;

    /**
     * 版本号
     */
    @TableField(value = "version")
    private Integer version;

}
```

注意：加密后字段较长，使用时请注意加密字段数据库长度，如身份证18位，加密后可达64位

### 单测用例

[点击这里](https://github.com/benym/rpamis-security/blob/master/rpamis-security-test/src/test/java/com/rpamis/security/test/SecurityTest.java)找到对应的单测用例

| 测试用例                                                     | 测试结果 |
| ------------------------------------------------------------ | -------- |
| Mybatis-plus insert接口，新增数据后查询，同时校验加解密结果  | ✅通过    |
| Mybatis-plus saveBatch接口，新增数据后查询，同时校验加解密结果 | ✅通过    |
| Mybatis-plus update接口，新增数据后查询，再更新数据，同时校验加解密结果 | ✅通过    |
| Mybatis-plus updateWrapper，新增数据后查询，再更新数据，同时校验加解密结果 | ✅通过    |
| Mybatis-plus delete接口，新增数据后删除，同时校验加解密结果  | ✅通过    |
| Mybatis自定义insert接口，新增数据后查询，同时校验加解密结果  | ✅通过    |
| Mybatis自定义insertBatch接口(foreach动态SQL拼接)，新增数据后查询，同时校验加解密结果 | ✅通过    |
| Mybatis自定义update接口，新增数据后查询，再更新数据，同时校验加解密结果 | ✅通过    |
| Mybatis自定义delete接口，新增数据后删除，同时校验加解密结果  | ✅通过    |
| 获取脱敏数据-单一自定义实体                                  | ✅通过    |
| 获取脱敏数据-List类型                                        | ✅通过    |
| 获取脱敏数据-Map类型                                         | ✅通过    |
| 获取脱敏数据-统一返回体(泛型自定义实体)                      | ✅通过    |
| 获取脱敏数据-统一返回体(无泛型)                              | ✅通过    |
| 获取脱敏数据-嵌套脱敏-单一自定义实体                         | ✅通过    |
| 获取脱敏数据-嵌套脱敏-List类型                               | ✅通过    |
| 获取脱敏数据-嵌套脱敏-Map类型                                | ✅通过    |
| 获取解密数据-Mybatis-plus-selectOne                          | ✅通过    |
| 获取解密数据-Mybatis-plus-selectList                         | ✅通过    |
| 获取解密数据-Mybatis-selectOne                               | ✅通过    |
| 获取解密数据-Mybatis-selectList                              | ✅通过    |
| 获取解密数据-Mybatis-selectMap                               | ✅通过    |
| 新增入库后不改变源对象引用-深拷贝                            | ✅通过    |
| 新增后，如果修改同一个对象引用，再进行更新，能够正常加密     | ✅通过    |

