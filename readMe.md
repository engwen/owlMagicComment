Use my code, please note the source, thank you. 
           
 Author engwen            
 Email xiachanzou@outlook.com            
 Time 2018/07/16
 
Thank JetBrains Community Support Team for its support to this project, which provides IntelliJ idea tools 
for my faster development and iteration. <url>https://www.jetbrains.com/?From=owlMagicComment

#  To simple principles   至简原则

### use the jar,you can get like this
 
   ##### check params   (@OwlCheckParams  notNull,notAllNull,canNull)
    
        @RequestMapping("/signin")
        public MsgResultVO signin(User user) {
            MsgResultVO result = new MsgResultVO();
            if(null==user.getPassword || "" == user.getPassword ){
                result.errorMsg("The password must not be empty.");                \ \      @RequestMapping("/signin")
            } else if(null==user.getAccount || ""==user.getAccount ){      -------- \ \     @OwlCheckParams(notNull={"account","password"})
                result.errorMsg("Account can not be empty.");              -------- / /     public MsgResultVO signin(User user) {
            } else {                                                               / /           return userService.signin(user);
                result = userService.signin(user);                                          }
            }
            return result;
        }
        
   ##### change result type to your want (@OwlBackToObject  PS: you can use @OwlBackToMsgResult change result type to MsgResultVO)
   
    @RequestMapping("test")
    public Object test() {
        MsgResultVO<String> msgResultVO = MsgResultVO.getInstanceSuccess("aaaa");
        TestVO testVO = new TestVO();                                                \ \    @RequestMapping("test")
        testVO.setCode(msgResultVO.getResultCode());                        --------- \ \   @OwlBackToObject(classPath = "com.owl.shiro.vo.TestVO",msg = "msg",code = "code",data = "data")
        testVO.setMsg(msgResultVO.getResultMsg());                          --------- / /   public Object test() {
        testVO.setData(msgResultVO.getResultData());                                 / /        return MsgResultVO.getInstanceSuccess("aaaa");
        return testVO;                                                                      }
    }

   ##### count the method use time (@OwlCountTime)
    @RequestMapping("/signin")
    @OwlCheckParams(notNull={"account","password"})                               \ \       @RequestMapping("/signin")   
    public MsgResultVO signin(OwlUser user) {                           -----------\ \      @OwlCountTime
        Date startTime = new Date();                                    -----------/ /      @OwlCheckParams(notNull={"account","password"})     
        MsgResultVO<OwlUser> result = owlAuthService.signin(user);                / /       public MsgResultVO signin(OwlUser user) {
        Double second = (double)((new Date()).getTime() - startTime.getTime()) / 1000.0D;       return owlAuthService.signin(user);
        logger.info("cost time is " + second);                                              }
        return result;
    }
   
   
   
   ##### set params are null or set return data some value are null (@OwlSetNullData)
    @RequestMapping("/signin")
    @OwlCheckParams(notNull={"account","password"})
    public MsgResultVO signin(OwlUser user) {                                      \ \      @RequestMapping("/signin")
        user.setId(null);                                                -----------\ \     @OwlSetNullData(backValue = {"password"},paramsValue = {"id"})
        MsgResultVO<OwlUser> result = owlAuthService.signin(user);       -----------/ /     @OwlCheckParams(notNull={"account","password"})
        result.getResultData().setPassword(null);                                  / /      public MsgResultVO signin(OwlUser user) {
        return result;                                                                          return owlAuthService.signin(user);
    }                                                                                       }
        
        
        
           
  
   ### or like this
   (use Listening event like use Flex AS)
        
        // build event     
        OwlObserverEvent HH= new OwlObserverEvent("HH");
        
        //add event listen
        UserTest lili = new UserTest();
        OwlObserverUtil.addEventListen(HH,lili,(k)-> System.out.println("hh"));
        
        //in other class you can do this :
        OwlObserverUtil.dispatchEvent(HH);//ListenCode in listening will be executed .it will print "hh"
        OwlObserverUtil.removeEventListen(HH);
    
    //all most,I suggest you implement this method by extend OwlObserved using the newly created class.like this
        //build class
        public UserTest extend OwlObserved{}
        // build event     
        OwlObserverEvent HH= new OwlObserverEvent("HH");   
        //add event listen 
        UserTest lili = new UserTest();
        lili.addEventListen(HH,()->{System.out.println("33333");});
        lili.dispatchEvent(HH);//or OwlObserverAB.dispatchEvent(HH);        
        //remove
        lili.removeEventListen(HH);
   
  ### or like this
  
   (build a class extends OwlMemento ,so you can restore the class to the specified state)
   
     public class UserTest extends OwlMemento {
         private String name;
         private Integer age;
         public String getName() {
             return name;
         }
         public void setName(String name) {
             this.name = name;
         }
         public Integer getAge() {
             return age;
         }
         public void setAge(Integer age) {
             this.age = age;
         }
     }
 
    //in other class you can do this :
  
          UserTest lili = new UserTest();
          lili.setName("lili");
          lili.setAge(12);
             //save state to OwlMemento
          lili.saveToMemento();
          lili.setAge(13);
            //save state to OwlMemento
          lili.saveToMemento();
            //get first 
          System.out.println(ObjectUtil.toJSON(lili.getMementoFirst()));//{"name":"lili","age":"10"}
            //get last
          System.out.println(ObjectUtil.toJSON(lili.getMementoLast()));//{"name":"lili","age":"13"}
            //get history
          System.out.println(ObjectUtil.toJSON(lili.getMementoHistory()));//[{"name":"lili","age":"10"},{"name":"lili","age":"13"}]
          UserTest wade = lili.transferMemento(new UserTest());
            //transfer history
          System.out.println(ObjectUtil.toJSON(wade.getMementoHistory(0)));//[{"name":"lili","age":"10"},{"name":"lili","age":"13"}]
  

###  and so on

#  En

##  How To Use?

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

This package relies on my other project, the OwlMagicUtil package, is available at https://github.com/engwen/owlMagicUtil.


    <context: component-scan base-package="com.owl.comment.annotations"/>Spring MVC project needs to add the following configuration in the configuration file of the spring MVC Servlet

    <aop: aspectj-autoproxy/>

SpringBoot users need to configure scans on project startup classes

    @ComponentScan (base Packages = {"***", "com. owl", "***"})

En details ,please click <url>https://github.com/engwen/owlMagicComment/blob/master/readMeEn.md

-----
#  Cn

## 如何使用？
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

本包依赖于我的另一个项目OwlMagicUtil包， 请参考 https://github.com/engwen/owlMagicUtil

spring springMVC 项目需要在  spring mvc servlet 的配置文件中添加以下配置

     <context:component-scan base-package="com.owl"/>
     <aop:aspectj-autoproxy/>
    
 SpringBoot 用户需要在 项目启动类上配置扫描
 
    @ComponentScan(basePackages = { "***","com.owl" ,"***"})




中文详情，请点击 ：<url>https://github.com/engwen/owlMagicComment/blob/master/readMeCn.md
