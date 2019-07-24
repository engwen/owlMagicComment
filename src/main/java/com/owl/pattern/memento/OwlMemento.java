package com.owl.pattern.memento;

import com.owl.magicUtil.model.ModelPrototype;
import com.owl.magicUtil.util.ObjectUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 備忘錄模式 抽象
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/7/11.
 */
public abstract class OwlMemento extends ModelPrototype {
    private List<OwlMemento> mementoList = new ArrayList<>();

    public List<OwlMemento> getMementoList() {
        return mementoList;
    }

    public void setMementoList(List<OwlMemento> mementoList) {
        this.mementoList = mementoList;
    }

    /**
     * 事件轉移。儅對象重新new或賦值時，可以使用本方法將之前的記錄移動到新的對象中
     * @param newOwlMemento 新對象
     * @param <T>           汎型
     * @return 汎型對象
     */
    public final <T extends OwlMemento> T transferMemento(T newOwlMemento) {
        newOwlMemento.getMementoList().addAll(mementoList);
        clearMementoHistory();
        return newOwlMemento;
    }

    /**
     * 保存快照
     */
    public final void saveToMemento() {
        try {
            OwlMemento owlMemento = ObjectUtil.setThisObjToAnotherObj(this, this.getClass().newInstance());
            mementoList.add(owlMemento);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 獲取歷史快照
     * @return 歷史快照集合
     */
    public final List<OwlMemento> getMementoHistory() {
        return mementoList;
    }

    /**
     * 獲取指定的快照
     * @param index 快照編號
     * @return 快照
     */
    public final OwlMemento getMemento(int index) {
        if (index >= 0 && mementoList.size() > index) {
            return mementoList.get(index);
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
        if (mementoList.size() > 0) {
            return mementoList.get(mementoList.size() - 1);
        }
        return null;
    }

    /**
     * 清除歷史快照
     */
    public final void clearMementoHistory() {
        mementoList = null;
        mementoList = new ArrayList<>();
    }
}
