package com.owl.observer;

/**
 * 被观察者抽象
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/4/26.
 */
public abstract class OwlObserved {
    //被观察者代码编号
    public static final int OBSERVED_CODE = 1;

    //被觀察者需要执行的代碼
    public void listenIng() {
        System.out.println("默认的输出提示");
    }

    //被觀察者監聽事件
    public void listen(OwlObserverEvent code) {
        OwlObserverAB.addEventListen(code, this);
    }

    public void removeListen(OwlObserverEvent code){
        OwlObserverAB.removeEventListen(code,this);
    }
}
