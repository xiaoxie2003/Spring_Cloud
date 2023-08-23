1.功能：
    基本功能：登录/退出，浏览菜品，查看详情，加入购物车，修改（增加，减少，删除）下订数量，填写外卖送货信息等功能
    后期功能：分页浏览，用户历史访问记录，商品的点赞，用户权限，记录用户设备，统计用户活跃度

2.技术：
    项目技术架构描述:
        系统整合:  springBoot+spring
        Web层框架:  springMVC
        Dao层: mybatisPlus
        数据源:  druid
        项目构建:  maven
        数据库: mysql8
        单元测试框架: spring boot test + junit,  采用 mock
        API发布管理: Swagger
        前后端联调工具:  postman
        项目运行容器:  docker   => K8S
        版本控制: git

        ＝＝＝＝＝＝发送注册邮件，发送日报表的功能
        定时任务:   quartz
        模板引擎: freemarker
        邮件:  javax.mail

        前端框架: vuejs
        缓存:   spring data +  redis   ->　　　查询缓存　　（查多于修改）　
        跨域问题:  浏览器的安全机制,  同源策略   -> 前后端项目分离，通过浏览器访问后端.

        ==============================================
        权限控制:  spring security
        报表:  水晶报表,....
        图表:  jfreechart,

        移动开发: uni-app.  基于vue