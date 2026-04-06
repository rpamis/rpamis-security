## 如何贡献

## 感谢你为 Rpamis Security 贡献！

我们真心地感谢为这个项目提出过 Issue 和 PR 的开发者。我们希望有更多社区的开发者加入进来，一起把项目做好。

## 贡献流程

在贡献代码之前，请您稍微花一些时间了解为 Rpamis Security 贡献代码的流程。

### 贡献什么？

我们随时都欢迎任何贡献，无论是简单的错别字修正，BUG 修复还是增加新功能。请踊跃提出问题或发起 PR。我们同样重视文档以及与其它开源项目的整合，欢迎在这方面做出贡献。

如果是一个比较复杂的修改，建议先在 Issue 中添加一个 Feature 标识，并简单描述一下设计和修改点。

### 从哪里入手？

如果您是初次贡献，可以先从 [good first issue](https://github.com/rpamis/rpamis-security/labels/good%20first%20issue) 和 [help wanted](https://github.com/rpamis/rpamis-security/labels/help%20wanted) 中认领一个比较简单的任务。

### Fork 仓库，并将其 Clone 到本地

- 点击 [本项目](https://github.com/rpamis/rpamis-security) 右上角的 `Fork` 图标 将 rpamis/rpamis-security  fork 到自己的空间。
- 将自己账号下的 rpamis-security 仓库 clone 到本地，例如我的账号是 `benym`，那就是执行 `git clone https://github.com/benym/rpamis-security.git` 进行 clone 操作。

### 配置 Github 信息

- 在自己的机器执行 `git config --list` ，查看 git 的全局用户名和邮箱。
- 检查显示的 user.name 和 user.email 是不是与自己 github 的用户名和邮箱相匹配。
- 如果公司内部有自己的 gitlab 或者使用了其他商业化的 gitlab，则可能会出现不匹配的情况。这时候，你需要为 rpamis-security 项目单独设置用户名和邮箱。
- 设置用户名和邮箱的方式请参考 github 官方文档，[设置用户名](https://help.github.com/articles/setting-your-username-in-git/#setting-your-git-username-for-a-single-repository)，[设置邮箱](https://help.github.com/articles/setting-your-commit-email-address-in-git/)。

### Merge 最新代码

fork 出来的代码后，原仓库 main 分支可能出现了新的提交，这时候为了避免提交的 PR 和 Main 中的提交出现冲突，需要及时 merge main 分支。

- 在你本机的 rpamis-security 目录下，执行 `git remote add upstream https://github.com/rpamis/rpamis-security` 将原始仓库地址配置为 upstream。
- 在你本机的 rpamis-security 目录下，执行 `git fetch upstream` 从 upstream 拉取更新。
- 在你本机的 rpamis-security 目录下，执行 `git checkout main` 切换到 main 分支。    
- 在你本机的 rpamis-security 目录下，执行 `git rebase upstream/main` rebase 最新代码。

### 配置 Rpamis Security 标准的代码格式

Rpamis Security 项目的代码规范与 Spring 项目的代码规范相同。请参考相关代码格式规范说明，提交代码前需要先配置好代码格式规范。

### 开发

开发自己的功能，**开发完毕后建议使用 `mvn clean package` 命令确保修改后的代码能在本地编译通过。执行该命令的同时还能以 spring 的方式自动格式化代码**。然后再提交代码，提交代码之前请注意创建一个新的有关本特性的分支，用该分支进行代码提交。

### 删除未使用的导入

为了确保代码的整洁，请删除 Java 文件中未使用的导入。可以通过执行 `mvn spotless:apply` 命令来自动删除未使用的导入。

### 提交最新代码

在编码完成之后，需要基于 pr 规范`[lint-pr-title.yml](.github/workflows/lint-pr-title.yml)`对提交信息进行 format & check，确保提交信息符合规范。
Commit 规范: git commit -m "类型(模块): 空格 符合规范的提交信息",例如 `feat(docs): contribute-zh 更新` 

### Merge 最新代码

- 同样，提交 PR 前，需要 rebase main 分支的代码（如果您的目标分支不是 main 分支，则需要 rebase 对应目标分支），具体操作步骤请参考之前的章节。
- 如果出现冲突，需要先解决冲突。

### 提交 PR

提交 PR，根据 `Pull request template` 写明修改点和实现的功能，等待 code review 和 合并，成为 Rpamis Security Contributor，为更好用的 Rpamis Security 做出贡献。
