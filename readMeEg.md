# OwlMagicComment
 
#### 

* Package name
com.owl.annotations
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