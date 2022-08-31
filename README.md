## Instruction

## 环境

[![ayvzw.png](https://s1.328888.xyz/2022/08/31/ayvzw.png)](https://imgloc.com/i/ayvzw)

### sonarqube版本

sonarqube-8.9.9.56886 community version

[![azF6s.png](https://s1.328888.xyz/2022/08/31/azF6s.png)](https://imgloc.com/i/azF6s)

## 编译打包

进入项目更目录下，运行

~~~
mvn clean install -f pom_SQ_8_9_LTS.xml
~~~

生成的jar包位于target目录下

java-custom-rules-example-1.0.0-SNAPSHOT.jar

## 安装插件

进入soarqube目录的extensions下的plugins目录下

然后重新启动sonarqube

在Quality Profile页面create新的profile，在repository下找到MyCompany Custom Repository，激活对应的规则

对项目进行分析 

