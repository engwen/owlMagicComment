# OwlMagicComment
 
 使用本人代码请备注来源，谢谢
  * author engwen
  * email xiachanzou@outlook.com
  * time 2018/07/16.
# 
#### 

* 包名
com.owl.comment.annotations
* 引用方式为
```
<dependency>
    <groupId>com.github.engwen</groupId>
    <artifactId>owlMagicComment</artifactId>
    <version>*.*.*</version>
</dependency>
```

本包依赖于我的另一个项目OwlMagicUtil包，返回对象 MsgResultVO 请参考 https://github.com/engwen/owlMagicUtil

spring springMVC 项目需要在  spring mvc servlet 的配置文件中添加以下配置

     <context:component-scan base-package="com.owl.comment.annotations"/>
     <aop:aspectj-autoproxy/>
    
 SpringBoot 用户需要在 项目启动类上配置扫描
 
    @ComponentScan(basePackages = { "***","com.owl" ,"***"})

-------
> 自定义注解

1. @OwlCheckParams

     该注解用于controller层校验请求参数，包含notAllNull（不可全部为空），notNull（不能为空）以及canNull
     （可以为空）三个属性，为方便书写接口文档以及后期查询代码，此三个属性合并起来应当为本接口可接收的所
     有参数，notAllNull中的参数不能全部为null，否则该接口将返回 "请求参数 \*\* 不能全为空"，notNull中的
     参数不能为空，否则该接口将返回 "请求参数 \*\* 不能为空"，canNull中的参数可以为空。
     使用本注解需设置默认返回的对象为MsgResultVO

     例如：

    原始代码：
            
        @RequestMapping("/signin")
        public MsgResultVO signin(User user) {
            MsgResultVO result = new MsgResultVO();
            if(null==user.getPassword || "" == user.getPassword ){
                result.errorMsg("密码不能为空");
            } else if(null==user.getAccount || ""==user.getAccount ){
                result.errorMsg("账号不能为空");
            } else {
                result = userService.signin(user);
            }
            return result;
        }
        
      使用注解后代码:

        @RequestMapping("/signin")
        @OwlCheckParams(notNull={"account","password"})
        public MsgResultVO signin(User user) {
            return userService.signin(user);
        }
      返回数据：{"result":false,"resultCode":"0002","resultMsg":"请求参数 \[account,password\] 不能为空","resultData":null,"params":{}}
    
    节省编写判断代码的时间
    

           
本包引入的jar包包括
 
```
        <dependency>
               <groupId>com.github.engwen</groupId>
               <artifactId>owlMagicUtil</artifactId>
               <version>2.3</version>
        </dependency>
        <!--aop 核心依赖以及完成本功能所需依赖 -->
       <dependency>
                  <groupId>javax</groupId>
                  <artifactId>javaee-api</artifactId>
                  <version>7.0</version>
              </dependency>
      
              <dependency>
                  <groupId>org.springframework</groupId>
                  <artifactId>spring-web</artifactId>
                  <version>4.2.5.RELEASE</version>
              </dependency>
      
              <dependency>
                  <groupId>org.aspectj</groupId>
                  <artifactId>aspectjrt</artifactId>
                  <version>1.9.1</version>
              </dependency>
      
              <!-- 添加日志相关jar包 -->
              <dependency>
                  <groupId>log4j</groupId>
                  <artifactId>log4j</artifactId>
                  <version>1.2.17</version>
              </dependency>
```


1.1 

- 添加

 @OwlLogInfo
    
   该注解将会在使用它的地方打印"now in class %s , method %s" ，注意，这并不能给你一些额外的有用信息，只是用
   来输出一些日志帮助你快速定位被调用的接口。
    
1.1.1 

- 升级

   修复了一些BUG，优化了一些代码
   
1.1.2 

- 升级

 去除返回值中的[]，优化了一些代码
  
- 添加
   
 @OwlSetNullData(value={"",""}) 或  @OwlSetNullData("") @OwlSetNullData({"",""})
 
 该注解将会在使用它地方清除指定的返回值。有时某些属性并不想让它返回给前台，但是后期去底层修改又显得非常麻烦，
 sql或是service的改动往往会导致其它使用这些的地方发生意想不到的事情，基于这些考虑，我添加了这个注解，用于将
 返回值去除。
 注意：这个方法只支持 MsgResultVO 中的封装对象，也就是说，和 OwlCheckParams 这个接口一样，你需要将返回值设定
 为 MsgResultVO<T> ，你可以将返回的对象放在 resultData 中，但它必须是自定义对象或者Map，其他的对象都不会被支
 持，它所支持的属性也只是包装类，String，Long，Integer，Float，Double，List和Date
 
 例如 ：
        
        @RequestMapping("/signin")
        @OwlCheckParams(notNull={"account","password"})
        @OwlSetNullData("password")
        public MsgResultVO signin(User user) {
            return userService.signin(user);
        }
        返回值
        {"result":true,"resultCode":"0000","resultMsg":"请求成功","resultData":{"id":1,"name":"admin",
        "password":null,"account":"admin","email":"admin@admin.com","mobile":null,"status":true,
        "lastSigninTime":1541668514000,"createTime":1541668514000,"updateTime":1542622682000},"params":{}}
        
1.1.3 
 
- 升级
   
 @OwlSetNullData(paramsValue={"",""}) 或  @OwlSetNullData(backValue={""})
 
 添加设定请求参数集合 paramsValue 为null，设定返回参数集合为nul修改为 backValue
 
 
1.1.4

- 添加

 @OwlBackToObject(msg = "",code = "",data = "",classPath = "com.*.*.*.TestVO")
 
 该注解将会改变返回值类型，将msg code data设定到 指定的返回值类型 （classPath = "com.*.*.*.TestVO"） 中，
 由于返回类型改变，需要将方法返回值设定为Object对象
 
 例：
 
        @RequestMapping("/test1")
        @OwlBackToObject(msg = "msg",code = "code",data = "data",classPath = "com.owl.shiro.util.TestVO")
        public Object test1() {
            MsgResultVO result = new MsgResultVO();
            result.errorResult(MsgConstant.REQUEST_NO_SIGNIN);
            result.setResultData(new Date());
            return result;
        }
        
 上例中，返回值类型将由 MsgResultVO 转为 TestVO ，内容为：{"msg":"用户未登录","code":"0004","data":1545889878416}
 

- 添加

 @OwlBackToMsgResult(msg = "",code = "",data = "")
 
 该注解将会改变返回值类型，将msg code data设定到 MsgResultVO 中，
 由于返回类型改变，需要将方法返回值设定为Object对象
 
 例：
 
        @RequestMapping("/test2")
        @OwlBackToMsgResult(msg = "msg",code = "code",data = "data")
        public Object test2() {
               TestVO<Date> result = new TestVO();
               result.setMsg("test");
               result.setCode("0000");
               result.setData(new Date());
               return result;
        }
        
 上例中，返回值类型将由 TestVO 转为 MsgResultVO ，内容为：{"result":null,"resultCode":"0000","resultMsg":"test","resultData":1545890084447,"params":{}}
 
 
 1.1.5  -  1.1.7
 
 
- 添加

 注解 @OwlTry
 >现在你可以不用再写try catch啦，当然，默认的返回对象依然是MsgResultVO，如果你不能使用它那么真的非常遗憾

 MVC抽象模块。
 
 >添加 controller service dao 基本类以及他们的工具类实现，普通类现在你只需要继承 CellBaseControllerAb CellBaseServiceAb
 以及CellBaseDao 即可使用对应的工具类 CellBaseControllerUtil CellBaseServiceUtil 工具类完成基本的 curd 功能。list对象支持
 分页。关系类这里提供能了 RelationBaseControllerAb 和 RelationBaseServiceAb  以及对应的 Util，底层的 xml 也已经写好了模板
 为保持一致，个人推荐使用接收数据的对象为DTO，返回对象为VO，数据库对应为model。
 
 Bean的工具获取。
 >SpringContextUtil 可以直接通过class或注入的名称获取 bean 对象，对多线程程序更加友好
 
 
- 优化
 >现在检查参数的注解将会检查对象的父类。
 
 >为了解决对异常的检查，在 1.1.7 版本中将会开放 MVC 架构中的所有 try catch
 
 