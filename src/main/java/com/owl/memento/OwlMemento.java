package com.owl.memento;

import com.owl.magicUtil.util.ObjectUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 備忘錄模式 抽象
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/7/11.
 */
public abstract class OwlMemento {
    private static Map<OwlMemento, List<OwlMemento>> stateMap = new ConcurrentHashMap<>();

    /**
     * 事件轉移。儅對象重新new或賦值時，可以使用本方法將之前的記錄移動到新的對象中
     * @param newOwlMemento 新對象
     * @param <T> 汎型
     * @return 汎型對象
     */
    public final <T extends OwlMemento> T transferMemento(T newOwlMemento) {
        stateMap.put(newOwlMemento, stateMap.get(this));
        stateMap.remove(this);
        return newOwlMemento;
    }

    /**
     * 保存快照
     */
    public final void saveToMemento() {
        try {
            List<OwlMemento> mementoList = stateMap.computeIfAbsent(this, k -> new ArrayList<>());
            OwlMemento memento = ObjectUtil.setThisObjToAnotherObj(this, this.getClass().newInstance());
            mementoList.add(memento);
        } catch (Exception e) {
            //TODO 输出错误日志
        }
    }

    /**
     * 獲取歷史快照
     * @return 歷史快照集合
     */
    public final List<OwlMemento> getMementoHistory() {
        return stateMap.get(this);
    }

    /**
     * 獲取指定的快照
     * @param index 快照編號
     * @return 快照
     */
    public final OwlMemento getMemento(int index) {
        if (null != stateMap.get(this) && index >= 0 && stateMap.get(this).size() >= index) {
            return stateMap.get(this).get(index);
        }
        return null;
    }

    /**
     * 獲取第一個快照
     * @return 快照
     */
    public final OwlMemento getMementoFirst() {
        return getMemento(0);
    }

    /**
     * 獲取最後一個快照
     * @return 快照
     */
    public final OwlMemento getMementoLast() {
        if (null != stateMap.get(this)) {
            return stateMap.get(this).get(stateMap.get(this).size() - 1);
        }
        return null;
    }

    /**
     * 清除歷史快照
     */
    public final void clearMementoHistory() {
        stateMap.remove(this);
    }
}
