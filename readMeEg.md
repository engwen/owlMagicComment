# OwlMagicComment
 
#### 

* Package name
com.owl.magicUtil
* The way of reference is
```
<dependency>
    <groupId>owl.magic.util</groupId>
    <artifactId>owlMagicComment</artifactId>
    <version>1.0</version>
</dependency>
```
This package relies on my other project, the OwlMagicUtil package, and the return object, MsgResultVO, is available at https://github.com/engwen/owlMagicUtil.

-------
> 自定义注解

1. @OwlCheckParams

  This annotation is used for the controller layer to check the request parameters, including notAllNull (not all null), notNull (not all null) and canNull (null) attributes. To facilitate writing interface documents and later query code, these three attributes should be combined with all the parameters that this interface can accept, notAllNull The parameters in the interface can not be all null, otherwise the interface will return "request parameters can not be all empty", the parameters in notNull can not be empty, otherwise the interface will return "request parameters can not be empty", the parameters in canNull can be empty.
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
    
1. @OwlLogInfo
    
This annotation will print "now in class% s, method% s" where it's used. Note that this doesn't give you any additional useful information, but it's used to output some logs to help you quickly locate the interface being called.
           
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
            <artifactId>spring-context</artifactId>
            <version>4.2.5.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-web</artifactId>
            <version>4.2.5.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aop</artifactId>
            <version>4.2.5.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjrt</artifactId>
            <version>1.9.1</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.aspectj/aspectjweaver -->
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.9.1</version>
        </dependency>

        <!-- 添加日志相关jar包 -->
        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.17</version>
        </dependency>
```