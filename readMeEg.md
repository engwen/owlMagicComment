# OwlMagicComment
 Use my code, please note the source, thank you.            
 Author engwen            
 Email xiachanzou@outlook.com            
 Time 2018/07/16
#### 

* Package name
com.owl.comment.annotations
* The way of reference is
```
<dependency>
    <groupId>com.github.engwen</groupId>
    <artifactId>owlMagicComment</artifactId>
    <version>*.*.*</version>
</dependency>
```
This package relies on my other project, the OwlMagicUtil package, and the return object, MsgResultVO, is available at https://github.com/engwen/owlMagicUtil.

-------
> 

1. @OwlCheckParams

  This annotation is used for the controller layer to check the request parameters, including notAllNull, notNull and canNull attributes. To facilitate writing interface documents and later query code, these three attributes should be combined with all the parameters that this interface can accept, notAllNull The parameters in the interface can not be all null, otherwise the interface will return "request parameters can not be all empty", the parameters in notNull can not be empty, otherwise the interface will return "request parameters can not be empty", the parameters in canNull can be empty.
     For example:

    Original code:
            
        @RequestMapping("/signin")
        public MsgResultVO signin(User user) {
            MsgResultVO result = new MsgResultVO();
            if(null==user.getPassword || "" == user.getPassword ){
                result.errorMsg("The password must not be empty.");
            } else if(null==user.getAccount || ""==user.getAccount ){
                result.errorMsg("Account can not be empty.");
            } else {
                result = userService.signin(user);
            }
            return result;
        }

   Using annotation Code:

        @RequestMapping("/signin")
        @OwlCheckParams(notNull={"account","password"})
        public MsgResultVO signin(User user) {
            return userService.signin(user);
        }

   it will return {"result":false,"resultCode":"0002","resultMsg":"请求参数 [account,password] 不能为空","resultData":null,"params":{}}
    Save time for writing code.
    
The jar package introduced in this package includes
 
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

- add

@OwlLogInfo

The annotation will print "now in class% s, method% s" where it is used. Note that this doesn't give you any additional useful information, just with
To output some logs to help you quickly locate the interface being invoked.

1.1.1

- Upgrading

Fixed some BUGs and optimized some code

1.1.2

- Upgrading

Remove the []from the return value and optimize some code

- add

@OwlSetNullData (value={""}) or @OwlSetNullData ("")@OwlSetNullData ({""})

This annotation will clear the specified return value where it is used.  Sometimes some attributes don't want to be returned to the front desk, but it's very difficult to modify them later.
Changes to SQL or service often lead to unexpected events in other places where they are used. Based on these considerations, I added this annotation for the purpose of
Return value is removed.
Note: This method only supports encapsulated objects in MsgResultVO, that is, like the OwlCheckParams interface, you need to set the return value
For MsgResultVO<T>, you can put the returned object in resultData, but it must be a custom object or a Map, and no other object will be supported.
The attributes it supports are just wrapper classes, String, Long, Integer, Float, Double, List and Date.

For example:     

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
    
- Upgrading（@OwlSetNullData）
    
 change @OwlSetNullData(value={})  to @OwlSetNullData (paramsValue={""}) or @OwlSetNullData (backValue={"})
    
Add the set request parameter set paramsValue to null, and set the set of return parameters to nul to backValue.


1.1.4

-add

@OwlBackToObject(msg="",code="",data="",classPath="com.*.*.TestVO")

It's use the returns value type, the msg code dataset to the returns value type(classPath="com.*.*.*.TestVO"),
In the returning type, the methods returns value to Objectobject

For example:    

        @RequestMapping("/test1")
        @OwlBackToObject(msg = "msg",code = "code",data = "data",classPath = "com.owl.shiro.util.TestVO")
        public Object test1() {
            MsgResultVO result = new MsgResultVO();
            result.errorResult(MsgConstant.REQUEST_NO_SIGNIN);
            result.setResultData(new Date());
            return result;
        }

In example, returns value types to the MsgResultVO to TestVO, contents: {"msg":"User is登录","code","0004","data":1545889878416}


-add

@OwlBackToMsgResult(msg="",code="",data="")

It's use the returns value type, and the msg code dataset to MsgResultVO,
In the returning type, the methods returns value to Objectobject

For example:    

        @RequestMapping("/test2")
        @OwlBackToMsgResult(msg = "msg",code = "code",data = "data")
        public Object test2() {
               TestVO<Date> result = new TestVO();
               result.setMsg("test");
               result.setCode("0000");
               result.setData(new Date());
               return result;
        }
        

In example, returns value types to the TestVO to MsgResultVO, content to: {"result":null,"resultCode","0000","resultMsg","test","resultData":1545890084447,"params": {}}


 1.1.5  -  1.1.7

-add. 
 @ OwlTry. 
> now you can stop writing try catch, and of course, the default return object is still MsgResultVO, which is really a pity if you can't use it. 

MVC abstraction module. 
> add controller service dao base classes and their utility class implementations, normal classes now you just need to inherit CellBaseControllerAb CellBaseServiceAb. 
And CellBaseDao can use the corresponding tool class CellBaseControllerUtil CellBaseServiceUtil tool class to complete the basic curd functionality. 
List object support Pagination. The relational class, which provides RelationBaseControllerAb and RelationBaseServiceAb, as well as the corresponding xml at the bottom of the Util, has also been templated. 
For consistency, it is recommended that you use the object that receives the data for DTO, to return the object for the VO, database corresponding to model. 

Tool acquisition for Bean. 
>SpringContextUtil can get bean objects directly from class or injected names, making it more friendly to multithreaded programs. 

-Optimization. 
> now checks the annotation of the parameter to check the parent class of the object. 
> to resolve exception checking, all try catch in the MVC schema will be open in version 1.1.7
 
 1.1.8
 
 - Optimization
 
 > On the basis of the previous version, the implementations of CellBaseService Util and RelationBaseService Util tool classes are removed, and their work is moved to abstract classes.
 Now you can inherit CellBaseServiceAb and RelationBaseServiceAb using the service class to provide good CRUDs among them. I won't recommend you any more.
 Use these two tool classes, but you can still use them
 
 > Adding IdListSO to handle ID collection problems
 
 > Now you need to use the set * Dao method when inheriting CellBaseService Ab and RelationBaseService Ab to make the abstract class understand what you are going to use
 Which is the Dao for example:
 
     @Resource
     Private Owl Menu Dao owl Menu Dao;
     @Autowired
     Public void setCellBaseDao (){
     Sup. setCellBaseDao (owl MenuDao);
     }
 
 >@ OwlSetNullDataAS can now solve the property setting problem of the contained object when it contains the object.
 
 >@ OwlCheck ParamsAS can now solve the property setting problem of the contained object when it contains the object.
 
 >@ OwlBackToObjectAS can now solve the property setting problem of the contained object when it contains the object.
 
 > Now XML supports sorting by name
 
 > Now MsgResultVO supports getInstance Success to get objects
 
 > Adding ModelSO to handle accepting models has solved the problem that underlying XML cannot be unified
 
 > RelationBaseDao adds delete single operation
 
 >@ OwlTry now provides value for easy output when used
 
 > Lots of code structure optimization