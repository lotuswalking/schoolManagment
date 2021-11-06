#这是一个用来练手的Java Spring Boot App.

##当前已经完成登录部分.连接Mysql数据库.在启动首页的时候进行用户数据初始化,已经可以初始化数据.但是登录看起来还不成

通过计划任务实现了数据初始化

数据库已经替换为内存型的H2数据库

完成了students显示页面

在首页增加了注销按钮

完成了多数据配置工作.运行起来不错,修改文件列表:

###1. 新建数据库配置文件.在configuration目录下

###2. 在application.yml为每个连接创建连接信息

###3. 将两个repository分开,如果所有repository在一个包里面,将无法进行切换

###4. 后续问题,如何热切换数据库?如果按照年份来创建不同数据库,应该如何方便的使用?要不要用ovieride?

###5.使用thymeleaf security 5模块进行网页上面的用户名称已经权限组配置

###6. [参考引文]([https://www.thymeleaf.org/doc/articles/springsecurity.html])
