# api-service
[![Build Status](https://tfsprodcus2.visualstudio.com/A13d48cab-0f39-475d-9247-68bf476db38c/nju-itxia-backend/_apis/build/status/NJU-itxia.api-service?branchName=dev)](https://tfsprodcus2.visualstudio.com/A13d48cab-0f39-475d-9247-68bf476db38c/nju-itxia-backend/_build/latest?definitionId=2&branchName=dev)

## 概览

TODO

## 调试运行

### 准备条件：

- JDK 11
- （仅测试）MySql 8，数据库配置请在application.properties修改
- gradle

### 运行

构建并测试

```shell
./gradlew build
```

构建，跳过测试

```shell
./gradlew build -x test
```

