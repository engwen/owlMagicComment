package com.owl;

import com.owl.pattern.memento.OwlMemento;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/7/15.
 */
public class UserTestMemento extends OwlMemento {
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
