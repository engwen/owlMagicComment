package com.owl;

import com.owl.comment.annotations.OwlCountTime;
import com.owl.mvc.dto.PageDTO;
import com.owl.mvc.model.MsgConstant;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.mvc.vo.PageVO;
import jdk.nashorn.internal.objects.annotations.ScriptClass;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/1/31.
 */
@ScriptClass
public class Owltest {
    @Test
    public void test() {

        System.out.println(4 << 9);
//        86400000
//        System.out.println(new Date("20190304150000"));
        List<PageVO> pageDTOList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            PageVO pageDTO = new PageVO();
            pageDTO.initPageVO(10, 1, i);
            pageDTOList.add(pageDTO);
            pageDTOList.add(pageDTO);
        }

//        System.out.println(new Date());
        MsgResultVO<PageVO> resultVO = new MsgResultVO<>();
        resultVO.successResult(pageDTOList.get(0));
        System.out.println(pageDTOList.size());
        Set<PageVO> set = new HashSet<>(pageDTOList);
        System.out.println(set.size());
        resultVO.print();

    }

    public boolean tes(MsgConstant requestParameterError, Predicate<MsgConstant> predicate) {
        return predicate.test(requestParameterError);
    }


    public static boolean isAg(String b, MsgConstant msgConstant) {
        return msgConstant.getMsg().equals(b);
    }

    @Test
    public void t() {
        //创建一个容量为1的BlockingQueue

        BlockingQueue<String> b = new ArrayBlockingQueue<>(1);
        //启动3个生产者线程
        new Producer(b).start();
        new Producer(b).start();
        new Producer(b).start();
        //启动一个消费者线程
        new Consumer(b).start();
    }

    class Producer extends Thread {
        private BlockingQueue<String> b;

        public Producer(BlockingQueue<String> b) {
            this.b = b;

        }

        public synchronized void run() {
            String[] str = new String[]{
                    "java",
                    "struts",
                    "Spring"
            };
            for (int i = 0; i < 3; i++) {
                System.out.println(getName() + "生产者准备生产集合元素！");
                try {

                    b.put(str[i]);
                    //尝试放入元素，如果队列已满，则线程被阻塞

                } catch (Exception e) {
                    System.out.println(e);
                }
                System.out.println(getName() + "生产完成：" + b);
            }

        }
    }

    class Consumer extends Thread {
        private BlockingQueue<String> b;

        public Consumer(BlockingQueue<String> b) {
            this.b = b;
        }

        public synchronized void run() {

            while (true) {
                String result = null;
                System.out.println(getName() + "消费者准备消费集合元素！");
                try {
                    //尝试取出元素，如果队列已空，则线程被阻塞
                    result = b.take();
                } catch (Exception e) {
                    System.out.println(e);
                }
                System.out.println(getName() + "消费完：" + result);
            }

        }
    }

    @Test
    public void te(){
        Long a = new Date().getTime();
        Long b = a;
        b= 30L;
        System.out.println(a);
        System.out.println(b);
    }
}
