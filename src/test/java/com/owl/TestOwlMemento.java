package com.owl;

import com.owl.util.ObjectUtil;
import com.owl.pattern.memento.OwlMemento;
import org.junit.Test;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/7/15.
 */
public class TestOwlMemento {
    @Test
    public void test(){
        UserTestMemento lili = new UserTestMemento();
        lili.setName("lili");
        lili.setAge(10);
        System.out.println(ObjectUtil.toJSON(lili));
        lili.saveToMemento();
        UserTestMemento zhangsan = new UserTestMemento();
        lili.transferMemento(zhangsan);

//        System.out.println(ObjectUtil.toJSON(lili));
        System.out.println(ObjectUtil.toJSON(zhangsan.getMemento(0)));
    }
}
