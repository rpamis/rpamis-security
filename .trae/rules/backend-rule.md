你修改代码的时候应该忽略修改target目录
优先使用spring boot的设计样式来进行代码修改
对于仅修改后端代码的工作，你不应该修改任何前端代码
后端采用spring boot框架进行构建，你应该优先采用spring boot的组件来进行设计
每次编译时都需要带上对应的profile，例如：
```
mvn clean install -P release
```
或者
```
mvn clean install -P sonatype
```
完成代码修改之后你都需要使用spring-javaformat来格式化代码，例如：
```
mvn spring-javaformat:apply
```