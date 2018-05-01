# sdkg
### 接口sdk生成Maven插件
根据自定义接口文档生成接口sdk代码，该代码可以直接用于实际项目中
根据自定义接口文档生成基于testng框架的接口测试用例代码，该代码可直接用于研发过程的联调、自动化测试和冒烟测试

### 实现原理
基于maven插件实现
SDK代码生成插件使用jsoup解析接口文档，用codelmodel输出代码，sdk中使用jsonlib解析返回报文转换成相应的报文JavaBean
SDK示例工程使用testng框架的参数化方法和maven的profile实现测试用例代码和数据分离，并可支持多套环境
SDK示例工程使用testng框架的参数化方法可以支持对同一个测试用例灌入多组测试数据
使用build-helper-maven-plugin
SDK示例工程使用build-helper-maven-plugin插件演示了一组拓展api的玩法？


### 常见问题
测试参数中如果有中文，注意修改dos窗口代码页为utf-8，参考命令chcp 65001
测试参数中如果有中文，eclipse中直接执行testng测试用例会报错，参考报错信息如下：
Software caused connection abort: socket write error
