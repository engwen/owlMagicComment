# OwlMagicComment
 
 使用本人代码请备注来源，谢谢
  * author engwen
  * email xiachanzou@outlook.com
  * time 2018/07/16.
  
  感谢JetBrains Community Support Team对本项目的支持，为本项目提供intellij idea工具，方便我更快速的开发和迭代
  https://www.jetbrains.com/?from=owlMagicComment 
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

     方法注解。该注解可用于controller层校验请求参数，包含notAllNull（不可全部为空），notNull（不能为空）以及canNull
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
      返回数据：{"result":false,"resultCode":"0002","resultMsg":"请求参数 account,password 不能为空","resultData":null,"params":{}}
    
1. @OwlLogInfo

    类、方法注解。在接口开始的时候打印进入接口的信息。"now in %s" 
    
    例如：
    请求  auth/signin
    
        @OwlLogInfo
        @RestController
        @RequestMapping(value = "auth", method = RequestMethod.POST, consumes = "application/json")
        public class AuthController {
       
            @RequestMapping("signin")
            @OwlCheckParams(notAllNull = {"account", "email", "mobile"}, notNull = {"password"})
            public MsgResultVO signin(@RequestBody OwlUser user) {
                return owlAuthService.signin(user);
            }
        }
        
     将会打印
        
        [INFO][2019-06-10 09:24:23][com.owl.shiro.controller.AuthController] - now in signin

1. @OwlCountTime    

    方法注解。用来计算方法开始和结束的时间差
    
            @RequestMapping("signin")
            @OwlCheckParams(notAllNull = {"account", "email", "mobile"}, notNull = {"password"})
            @OwlCountTime
            public MsgResultVO signin(@RequestBody OwlUser user) {
                return owlAuthService.signin(user);
            }
            
   打印
   
        [INFO][2019-06-10 10:02:53][com.owl.comment.asImpl.OwlCountTimeAS] - 方法 signin 花费 ： 0.068 s

1. @OwlSetNullData    

    方法注解。用来设置请求参数或者返回参数为null
    
    例如：
            
            @RequestMapping(value = "list")
            @OwlSetNullData(paramsValue = {"id"}, backValue = {"password"})
            public MsgResultVO<List<OwlUser>> list(@RequestBody OwlUser user) {
                 return owlUserService.list(user);
            }
    
1. @OwlTry    
    
    方法注解。使用它就相当于在方法的最外层添加了一个try catch方法。他的返回值为MsgResultVO。
    
1. @OwlBackToObjectAS    
    
    方法注解。当你不想使用MsgResultVO的时候，使用它可以将MsgResultVO改变成其它类型的返回值。
    你只需要提供 路径 classPath，以及存在的结果值，结果码，结果信息，结果对象等便可以将返回值变为你想要的
    对象
    
            @RequestMapping("signin")
            @OwlCheckParams(notAllNull = {"account", "email", "mobile"}, notNull = {"password"})
            @OwlBackToObject(classPath = "com.owl.shiro.model.TestVO",msg = "msg",code = "mdg",data = "data")
            public Object signin(@RequestBody OwlUser user) {
                return owlAuthService.signin(user);
            }
    
    返回TestVO：
        
        {"mdg":"0000","msg":"请求成功","data":{"id":1,"name":"系统管理员","password":null,"account":"admin",
        "email":"admin@admin.com","mobile":"18812345678","isBan":false,"status":true,"lastSigninTime":1558060991000}
     
     而之前的返回值对象为MsgResultVO：
        
        {"result":true,"resultCode":"0000","resultMsg":"请求成功","resultData":{"id":1,"name":"系统管理员",
        "password":null,"account":"admin","email":"admin@admin.com","mobile":"18812345678","isBan":false,
        "status":true,"lastSigninTime":1558060991000}
        
    记得使用本注解时需要修改返回值为object对象
     
1. @OwlBackToMsgResultAS    
    
    方法注解。当你想使用MsgResultVO作为返回值的时候，使用它可以将其它类型的返回值改变成MsgResultVO。使用方法和 
     @OwlBackToObjectAS
    几乎一样。但你不需要指定  classPath
    
>观察者模式  灵感来自 Flex AS 中的事件监听机制

目标是在接收到指定的事件时可以执行指定的监听事件代码，一个被观察者可以监听多个事件

使用方法：
 
1. 创建 OwlObserverEvent 事件
1. 创建被观察者类，并继承 OwlObserved 类
1. 为被观察者添加事件监听，并在自定义方法中编写监听事件后处理的逻辑过程
1. 在适当的时间抛出 OwlObserverEvent 事件

    注意 !!!  由于这里描绘的观察者将会在监听到指定事件的时候执行代码，因此使用的是 lamda 表达式，

    例如：
    
        public class TestOb extends OwlObserved {
                //被觀察者需要执行的代碼
                public Consumer<OwlObserved> SystemOutYYYYY() {
                    //做你想做的一切。别忘记本包中的 SpringContextUtil 可以在这里帮你获取 bean 哦
                    return  (obj)-> System.out.println("yyyyyyyy");
                }
            
                //被觀察者需要执行的代碼
                public Consumer<OwlObserved> SystemOutHHHH() {
                    //做你想做的一切。别忘记本包中的 SpringContextUtil 可以在这里帮你获取 bean 哦
                    return  (obj)-> System.out.println("hhhhhhh");
                }
        }

    之后
    
        @Test
        public void test() {
            TestOb testOb = new TestOb();
            OwlObserverEvent yyy = new OwlObserverEvent("SystemOutYYYYY");
            OwlObserverEvent hhh = new OwlObserverEvent("SystemOutHHHH");
            testOb.addEventListen(yyy, testOb.SystemOutYYYYY());
            testOb.addEventListen(hhh, testOb.SystemOutHHHH());
            OwlObserverAB.dispatchEvent(yyy, testOb.getClass());//针对指定事件，指定类接受此次事件
            OwlObserverAB.dispatchEvent(hhh);//针对指定事件，全部监听该事件的类
            testOb.dispatchEvent(hhh);// == OwlObserverAB.dispatchEvent(hhh);针对指定事件，全部监听该事件的类
            testOb.removeListen(yyy);
            OwlObserverAB.dispatchEvent(yyy);
            testOb.addEventListen(yyy, testOb.SystemOutYYYYY());
            OwlObserverAB.removeEventListen(yyy,testOb);
            OwlObserverAB.dispatchEvent(yyy);
        }

           
>MVC 简写部分（常用的CRUD使用方法）

1. SpringContextUtil
    
    我提供这个工具是为了正在多线程中方便你快速的获取指定的bean

1. Dao

   本模板中的 Dao 类接口 需要继承 CellBaseDao<T> 或  RelationBaseDao<T>，你可以在继承后编写自己的方法
   
   例如：
   
        public interface OwlMenuDao extends CellBaseDao<OwlMenu> {
            Set<OwlMenu> menuListByRole(IdListSO idListSO);
        }
   
1. xml

    本模板中的 CellDemo.xml 和RelationDemo.xml 打包在jar中，你可以直接复制其中的内容到自己的xml文件中，
    指定<mapper namespace="com.owl.dao.OwlUserMapper">，修改其中的配置属性，Table_ID_Name 即为当前表的id名称
    sql Select_Like 表示的模糊查询，Select_Exact 为精确查询
    注释<!-- 以下不需要改變  -->的下方是不需要修改的代码。但是在此之前你需要修改其中的属性以使它和你的数据库表
    一一对应

1. Service

    Service层需要继承 CellBaseServiceAb<T> 或者 RelationBaseServiceAb<T>。
    
     并通过
            
              @Resource
                private OwlMenuDao owlMenuDao;
              @Autowired
                public void setCellBaseDao() {
                    super.setCellBaseDao(owlMenuDao);
                }
                
    或：
             
               @Resource
               private OwlPageMenuDao owlPageMenuDao;
           
               @Autowired
               public void setRelationBaseDao() {
                   super.setRelationBaseDao(owlPageMenuDao);
               }     
         
     的方法为模板注入 Dao 类，你可以重写本类中的方法以使它按照你的方式进行业务处理。

1. Controller
    
    CellBaseController类定义了基础的增删改查
    
            MsgResultVO<T> create(T model);

            MsgResultVO<?> createList(List<T> list);

            MsgResultVO delete(T model);

            MsgResultVO deleteList(DeleteDTO deleteDTO);

            MsgResultVO banOrLeave(BanDTO banDTO);

            MsgResultVO banOrLeaveList(BanListDTO banListDTO);

            MsgResultVO<?> update(T model);

            MsgResultVO<T> details(T model);

            MsgResultVO<PageVO<T>> list(PageDTO<T> pageDTO);
        
            MsgResultVO<List<T>> getAll(T model);
        
            MsgResultVO<?> isExist(T model);
    
    继承 CellBaseControllerAb 后，你便可以快速调用接口中定义的各个方法。

     
           
###本包引入的jar包包括

 -------
 
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


###历史升级记录

 -------

1.0

- 添加  @OwlCheckParams

     该注解用于controller层校验请求参数，包含notAllNull（不可全部为空），notNull（不能为空）以及canNull
     （可以为空）三个属性，为方便书写接口文档以及后期查询代码，此三个属性合并起来应当为本接口可接收的所
      有参数，notAllNull中的参数不能全部为null，否则该接口将返回 "请求参数 \*\* 不能全为空"，notNull中的
      参数不能为空，否则该接口将返回 "请求参数 \*\* 不能为空"，canNull中的参数可以为空。
      使用本注解需设置默认返回的对象为MsgResultVO

1.1 

- 添加  @OwlLogInfo
    
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
 
 1.1.8
 
- 优化 

> 在上一版的基础上，去除CellBaseServiceUtil 以及 RelationBaseServiceUtil 工具类中的实现，并将它们的工作移动到抽象类中。
现在你可以使用 service 类继承 CellBaseServiceAb 和 RelationBaseServiceAb 以便使用它们当中提供好的 CRUD 。我不再推荐你
使用这两个工具类，但是你仍然可以使用

> 添加 IdListSO 处理ID集合情况的问题
> 现在你需要在继承CellBaseServiceAb 和 RelationBaseServiceAb 的时候使用 set*Dao 的方法，使得抽象类能明白你将要使用
的 dao 是哪一个 例如：

    @Resource
    private OwlMenuDao owlMenuDao;
    
    @Autowired
    public void setCellBaseDao() {
        super.setCellBaseDao(owlMenuDao);
    }
 
> @OwlSetNullDataAS 现在可以解决对象包含对象的时候，被包含对象的属性设置问题

> @OwlCheckParamsAS 现在可以解决对象包含对象的时候，被包含对象的属性设置问题

> @OwlBackToObjectAS 现在可以解决对象包含对象的时候，被包含对象的属性设置问题

> 现在 xml 支持按照名称排序

> 现在 MsgResultVO 支持 getInstanceSuccess 获取对象

> 添加 ModelSO 处理接受 model 已解决底层 xml 不能统一的问题 

> RelationBaseDao 添加了删除单个操作

> @OwlTry 现在能提供 value ，便于在使用的时候输出

> 添加观察者模式

> 大量的代码结构优化

