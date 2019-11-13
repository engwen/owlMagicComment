package com.owl;

import com.owl.pattern.function.ListenCodeNoParams;
import com.owl.pattern.observer.OwlObserved;
import com.owl.pattern.observer.OwlObserverEvent;
import com.owl.util.MD5Util;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/3/27.
 */
public class Teeee {


    public static void SystemOutYYYYY(OwlObserved owlObserved) {
        System.out.println("-------------------");
    }

    public static OwlObserverEvent PRINT_EVENT_LAMDA1 = new OwlObserverEvent("lamda1");
    public static OwlObserverEvent PRINT_EVENT_LAMDA2 = new OwlObserverEvent("lamda2");

    @Test
    public void test() throws InterruptedException {

        TestOb testOb = new TestOb();
//        testOb.addEventListen(PRINT_EVENT_LAMDA1, testOb.SystemOutYYYYY());
//        testOb.addEventListen(PRINT_EVENT_LAMDA2, TestOb::SystemOutYYYYYY);
//        OwlObserverAB.removeEventListen(PRINT_EVENT_LAMDA1, TestOb.class); //or testOb.dispatchEvent(PRINT_EVENT_LAMDA1) || testObj.dispatchEvent(PRINT_EVENT_LAMDA1);
//        OwlObserverAB.dispatchEvent(PRINT_EVENT_LAMDA1, TestOb.class);
        testOb.removeAllEventListen();
//                Thread.sleep(2000L);
//        OwlObserverAB.dispatchEvent(PRINT_EVENT_LAMDA2, TestOb.class);
//        System.out.println(OwlThreadPool.getThreadPool().isShutdown());
//        Integer a = 1;
//        OwlObserverUtil.addEventListen(PRINT_EVENT_LAMDA1,testOb,()-> System.out.println("hhhhhh"));
//        OwlObserverUtil.dispatchEvent(PRINT_EVENT_LAMDA1);

//        Thread.sleep(1000);

//        testOb.dispatchEvent(PRINT_EVENT_LAMDA1);

        List<Integer> temp = new ArrayList<>();
        temp.add(1);
        temp.add(1);
        temp.add(1);
        temp.stream().distinct().collect(Collectors.toList()).forEach(System.out::println);
        System.out.println(MD5Util.getMD5("111111"));
    }


}

class TestOb extends OwlObserved {
    private static List<Integer> aaa = new ArrayList<>();

    static {
        for (int i = 0; i < 1000000; i++) {
            aaa.add(i);
        }

    }

    public ListenCodeNoParams SystemOutYYYYY() {
        return () -> {
            Date date = new Date();
            aaa.stream().filter(it -> it > 0).collect(Collectors.toList()).forEach(it->it++);
            System.out.println(date.getTime()- new Date().getTime());
        };
    }

    public static void SystemOutYYYYYY() {
        Date date = new Date();
        aaa.forEach(it -> {
            if (it > 0) {
                it++;
            }
        });
        System.out.println(date.getTime()- new Date().getTime());
    }
    // and more
}
