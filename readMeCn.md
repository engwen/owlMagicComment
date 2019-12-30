# OwlMagicComment

[TOC]

-------

#> 自定义注解

##### 1. @OwlCheckParams

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
    
##### 2. @OwlLogInfo

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

##### 3. @OwlCountTime    

    类、方法注解。用来计算方法开始和结束的时间差
    
            @RequestMapping("signin")
            @OwlCheckParams(notAllNull = {"account", "email", "mobile"}, notNull = {"password"})
            @OwlCountTime
            public MsgResultVO signin(@RequestBody OwlUser user) {
                return owlAuthService.signin(user);
            }
            
   打印
   
        [INFO][2019-06-10 10:02:53][com.owl.comment.asImpl.OwlCountTimeAS] - 方法 signin 花费 ： 0.068 s

##### 4. @OwlSetNullData    

    方法注解。用来设置请求参数或者返回参数为null
    
    例如：
            
            @RequestMapping(value = "list")
            @OwlSetNullData(paramsValue = {"id"}, backValue = {"password"})
            public MsgResultVO<List<OwlUser>> list(@RequestBody OwlUser user) {
                 return owlUserService.list(user);
            }
    
##### 5. @OwlTry    
    
    类、方法注解。使用它就相当于在方法的最外层添加了一个try catch方法。他的返回值为MsgResultVO。
    
##### 6. @OwlBackToObjectAS    
    
    类、方法注解。当你不想使用MsgResultVO的时候，使用它可以将MsgResultVO改变成其它类型的返回值。
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
     
##### 7. @OwlBackToMsgResultAS    
    
    类、方法注解。当你想使用MsgResultVO作为返回值的时候，使用它可以将其它类型的返回值改变成MsgResultVO。使用方法和 
     @OwlBackToObjectAS
    几乎一样。但你不需要指定  classPath
    
-------

# >其它模式

#####  1. 观察者模式  灵感来自 Flex AS 中的事件监听机制

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
                //除此之外，你還可以使用
                public static void SystemOutYYYYY(OwlObserved owlObserved) {
                  //被觀察者需要执行的代碼，做你想做的一切。
                    System.out.println("----------yyy---------");
                }
        }

    之后
    
        @Test
        public void test() {
            TestOb testOb = new TestOb();
            OwlObserverEvent Y_EVENT = new OwlObserverEvent("SystemOutYYYYY");
            OwlObserverEvent H_EVENT = new OwlObserverEvent("SystemOutHHHH");
            OwlObserverEvent Y_LAMDA1_EVENT = new OwlObserverEvent("lamda1");
            testOb.addEventListen(Y_EVENT, testOb.SystemOutYYYYY());
            testOb.addEventListen(H_EVENT, testOb.SystemOutHHHH());
            //---------------------------------------------------
            testOb.addEventListen(Y_LAMDA_EVENT, TestOb::SystemOutYYYYY);
            OwlObserverAB.dispatchEvent(Y_EVENT, testOb.getClass());// For a specified event, the specified class accepts the event
            OwlObserverAB.dispatchEvent(H_EVENT);// For a specified event, all the classes that listen for that event
            testOb.dispatchEvent(H_EVENT);// == OwlObserverAB.dispatchEvent(H_EVENT); For a specified event, all the classes that listen for that event
            testOb.removeListen(Y_EVENT);
            OwlObserverAB.dispatchEvent(Y_EVENT);
            testOb.addEventListen(Y_EVENT, testOb.SystemOutYYYYY());
            OwlObserverAB.removeEventListen(Y_EVENT,testOb);
            OwlObserverAB.dispatchEvent(Y_EVENT);
        }

1. 你也可以直接使用 OwlObserverUtil ,他声明的监听事件同样会被上述方法触发,但是请注意，
    他只是为了防止类已经不能再继承的情况下，你仍然需要监听和抛出事件。
    我给你的意见是最好使用继承的方式去实现，更加高效（不需要遍历取数据），但是你可以使用 OwlObserverUtil 的
    抛出事件功能

------- 

<div id="CRUD"></div>

# >MVC 简写部分（常用的CRUD使用方法）

##### 1. SpringContextUtil
    
   我提供这个工具是为了正在多线程中方便你快速的获取指定的bean

##### 2. CellBaseDao<T, ID> 和 RelationBaseDao<T,MainID,FollowerID> 你可以在继承后编写自己的方法

   *  CellBaseDao<T, ID> 单属性模板 dao，为单属性的 model 提供数据库的直接操作
       
       本类中提供以下方法：


            /**
             * 直接插入
             * @param model 泛型对象
             * @return int
             */
            int insertSelective(T model);
        
            /**
             * 批量插入
             * @param modelListSO 泛型对象集合List
             * @return int
             */
            int insertList(ModelListSO<T> modelListSO);
        
            /**
             * 批量刪除
             * @param idListSO 内含id集合
             * @return int
             */
            int deleteByIdList(IdListSO<ID> idListSO);
        
            /**
             * 刪除
             * @param model 泛型对象
             * @return int
             */
            int deleteBySelective(T model);
        
            /**
             * 物理 批量刪除
             * @param idListSO 内含id集合
             * @return int
             */
            int deleteByIdListRe(IdListSO<ID> idListSO);
        
            /**
             * 物理 刪除
             * @param model 泛型对象
             * @return int
             */
            int deleteBySelectiveRe(T model);
        
            /**
             * 批量操作 禁用或啓用
             * @param banListDTO 對象
             *                   param idList 對象ID
             *                   param status 對象狀態
             * @return int
             */
            int banOrLeave(BanListDTO<ID> banListDTO);
        
            /**
             * 依據指定的屬性進行更新
             * @param model 泛型对象
             * @return int
             */
            int updateBySelective(T model);
        
            /**
             * 依據屬性獲取對象集合 粗略查询
             * @param selectLikeSO 泛型对象
             *                     Param("model")
             * @return 泛型对象集合
             */
            List<T> selectByLike(SelectLikeSO<T> selectLikeSO);
        
            /**
             * 依據屬性獲取對象集合 准确查询
             * @param selectLikeSO 泛型对象
             *                     Param("model")
             * @return 泛型对象集合
             */
            List<T> selectByExact(SelectLikeSO<T> selectLikeSO);
        
            /**
             * 依據 id 屬性獲取對象集合 准确查询
             * @param idSO id泛型
             * @return 泛型对象集合
             */
            T selectById(IdSO<ID> idSO);
        
            /**
             * 依據指定的屬性統計數據條數
             * @param selectLikeSO 泛型对象
             * @return int
             */
            Integer countSumByCondition(SelectLikeSO<T> selectLikeSO);
        
            /**
             * 依據指定的屬性獲取指定的集合
             * @param selectLikeSO Param("upLimit")
             *                     Param("rows")
             *                     Param("model")
             * @return 泛型对象集合
             */
            List<T> listByCondition(SelectLikeSO<T> selectLikeSO);
        
            /**
             * 查詢指定集合
             * @param idListSO 内含汎型對象
             * @return list
             */
            List<T> selectByIdList(IdListSO<ID> idListSO);

   *  RelationBaseDao<T,MainID,FollowerID> 关系属性模板 dao，为关系属性的 model 提供数据库的直接操作
          
      本类中提供以下方法：
      
      
            /**
             * 批量插入
             * @param modelListSO 内含汎型對象
             * @return int
             */
            int insertList(ModelListSO<T> modelListSO);
        
            /**
             * 批量插入
             * @param relationDTO 内含一對多
             * @return int
             */
            int insertRelation(RelationDTO<MainID,FollowerID> relationDTO);
        
            /**
             * 批量刪除或个别删除
             * @param modelSO 内含汎型對象
             * @return int
             */
            int delete(ModelSO<T> modelSO);
        
            /**
             * 批量刪除
             * @param modelListSO 内含汎型對象
             * @return int
             */
            int deleteList(ModelListSO<T> modelListSO);
        
            /**
             * 批量刪除
             * @param relationDTO 内含一對多
             * @return int
             */
            int deleteRelation(RelationDTO<MainID,FollowerID> relationDTO);
        
            /**
             * 查詢是否存在
             * @param modelSO 内含汎型對象
             * @return list
             */
            List<T> selectBySelective(ModelSO<T> modelSO);
##### 3. xml

   本模板中的 [CellDemo.xml](https://github.com/engwen/owlMagicComment/blob/master/src/main/resources/CellDemo.xml) 和
   [RelationDemo.xml](https://github.com/engwen/owlMagicComment/blob/master/src/main/resources/RelationDemo.xml)
    打包在jar中，你可以直接复制其中的内容到自己的xml文件中，
    指定<mapper namespace="com.*.dao.*Mapper">，修改其中的配置属性，Table_ID_Name 即为当前表的id名称
    sql Select_Like 表示的模糊查询，Select_Exact 为精确查询
    注释<!-- 以下不需要改變  -->的下方是不需要修改的代码。但是在此之前你需要修改其中的属性以使它和你的数据库表
    一一对应

##### 4. service

   我提供了完整的 [CellBaseService<T, ID>](https://github.com/engwen/owlMagicComment/blob/master/src/main/java/com/owl/mvc/service/CellBaseService.java)
    和 [RelationBaseService<T, MainID, FollowerID>](https://github.com/engwen/owlMagicComment/blob/master/src/main/java/com/owl/mvc/service/RelationBaseService.java)
   接口，为了便于你的使用，我提供了很多看似无用的方法，比如查询条件为id和model，这本可以将条件设置为一个model，
   但是考虑到那样你需要自己 new 一个 model 并塞入id再去查询，因此我直接提供了封装。
   
   你还可以自己实现这个接口，但是我推荐你使用继承，因为只要你的 dao 和 xml 都已经实现，那么继承并不需要重写便已经足够常规使用。
   Service层继承 [CellBaseServiceAb<M extends CellBaseDao<T, ID>, T, ID>](https://github.com/engwen/owlMagicComment/blob/master/src/main/java/com/owl/mvc/service/CellBaseServiceAb.java)
   或者 [RelationBaseServiceAb<M extends RelationBaseDao<T, MainID, FollowerID>, T, MainID, FollowerID>](https://github.com/engwen/owlMagicComment/blob/master/src/main/java/com/owl/mvc/service/RelationBaseServiceAb.java)
     
   请不要因为它们的长度而感到恐惧，这样编写的目标是为了帮助你快速为它的默认实现注入dao
   基础的增删改查已经由我帮助你实现，是时候彻底的离开基础代码的编写工作，把重心完全
   放在业务上面了
    
         
   PS: 你可以重写本类中的方法以使它按照你的方式进行业务处理。

##### 5. Controller
    
   [CellBaseController<T, ID>](https://github.com/engwen/owlMagicComment/blob/master/src/main/java/com/owl/mvc/controller/CellBaseController.java)
   类定义了基础的增删改查
    
   继承 [CellBaseControllerAb<M extends CellBaseServiceAb, T, ID>](https://github.com/engwen/owlMagicComment/blob/master/src/main/java/com/owl/mvc/controller/CellBaseControllerAb.java) 后，你便可以快速调用接口中定义的各个方法。我不会提供给你关系类型的controller，因为那是需要依托于
   你的业务做的工作，你需要重写 service，然后自己去实现那些业务。当然，service的默认实现

     
           
### 附录：本包引入的jar包包括

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


#历史升级记录



-------

 ##### 1.2.2
 
 -- 添加
 
 1.
 
 
 - 移动
 
 1. 为了更好的解耦，MsgResultVO 等相关代码移动至 owlMagicUtil 工具包中
 
 - BUG 优化
 
 1. 现在 OwlObserved 支持在派发事件时传递一个或者多个参数，建议在使用的时候，不要传递Long，String，int之类的基本数据类型，可能会引发莫名其妙的问题。推荐使用Map，Object，以及自定义类
 2. 现在 OwlObserved 支持直接使用 String 的方法名作为添加监听的依据，如 mimi.addEventListen(outName, "toString"); 注意，toString方法必须出现在对象 mimi 的类中，不然将会找不到这个方法
 3. 现在 OwlObserved 支持使用多个参数的 lambda 表达式，但是派发的事件参数个数一定要和 lambda 中的参数一致
 4. PageVO 类整改，现在 PageVO 对象加入 MsgResultVO 中的部分方法
 

 #####  1.2.0
 
 -- 添加
 
 1.添加 servletContextUtil 工具类，以便于获取request和session
 
 
 - 移动
 
 1. 为了更好的解耦，设计模式相关代码移动至 owlMagicUtil 工具包中
 
 - BUG 优化
 
 1. 调整了部分注解请求的顺序，现在如果同时使用 @OwlBackToMsgResult 和 @OwlBackToObject 注解，默认会先将返回类型指定为MsgResult，然后再转为指定的Object对象
 2. 现在 @OwlBackToMsgResult 和 @OwlBackToObject 可以对类使用
 3. 现在 @OwlCountTime 注解可以在类上使用
 4. 现在 @OwlLogInfo 注解可以在类上使用，并使用声明的 value 进行输出
 5. 现在 @OwlTry 注解可以在类上使用
 
 
 
 
 #####  1.1.9
 
 
- 添加

> 备忘录模式  OwlMemento
  
继承 OwlMemento 的类，将会获得备忘录功能，在执行代码后，可以将类属性数据恢复到使用备忘功能的时候
同时，它提供了历史数据的集合，你可以将数据恢复到任何一次使用备忘功能的时候。提供clear方法以便于
你在使用完备忘后清除缓存中的数据

- 添加

> 简化版的事件机制工具类  OwlObserverUtil

虽然提供几乎和继承类的一样的功能，并且已经和缘由的功能基本打通——和继承类能相互响应，但是我还是推荐你通过继承方法去实现，
它只是为了实现目标类在不能再次使用 extend 仍旧可以完成监听工作而产生的一个变种。
 
- 优化 

> 现在观察者模式使用线程安全的 ConcurrentHashMap 存储数据

> 观察者在接收到事件之后会开启新的线程处理所有的观察者的待执行代码

> 为了防止各个版本的 logger 的冲突，现在 jar 中的所有的 logger 改成基于 System.out 类似于 log4j的格式

> 修改观察者的几个方法，现在更加易用

> 修改代码传递为自定义 lambda  传递

> 添加了一个缓存线程池

> 现在即便是数组对象，toJSON依旧能够正确的输出结果

 
 #####  1.1.8
 
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

> 现在基础数据库对应的查询支持按照名称排序

> 现在 MsgResultVO 支持 getInstanceSuccess 获取对象

> 添加 ModelSO 处理接受 model 已解决底层 xml 不能统一的问题 

> RelationBaseDao 添加了删除单个操作

> @OwlTry 现在能提供 value ，便于在使用的时候输出

> 大量的代码结构优化

- 新功能 

> 添加观察者模式

 
 #####  1.1.5  -  1.1.7
 
 
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
 
 #####  1.1.4
 
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

 
 ##### 1.1.3 
 
- 升级
   
 @OwlSetNullData(paramsValue={"",""}) 或  @OwlSetNullData(backValue={""})
 
 添加设定请求参数集合 paramsValue 为null，设定返回参数集合为nul修改为 backValue
 
 
 ##### 1.1.2 

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
        
 
 
 ##### 1.1.1 

- 升级

   修复了一些BUG，优化了一些代码
   
 
 ##### 1.1 

- 添加  @OwlLogInfo
    
   该注解将会在使用它的地方打印"now in class %s , method %s" ，注意，这并不能给你一些额外的有用信息，只是用
   来输出一些日志帮助你快速定位被调用的接口。
    
 ##### 1.0

- 添加  @OwlCheckParams

     该注解用于controller层校验请求参数，包含notAllNull（不可全部为空），notNull（不能为空）以及canNull
     （可以为空）三个属性，为方便书写接口文档以及后期查询代码，此三个属性合并起来应当为本接口可接收的所
      有参数，notAllNull中的参数不能全部为null，否则该接口将返回 "请求参数 \*\* 不能全为空"，notNull中的
      参数不能为空，否则该接口将返回 "请求参数 \*\* 不能为空"，canNull中的参数可以为空。
      使用本注解需设置默认返回的对象为MsgResultVO
