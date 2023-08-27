package com.yc.bean;

public class Test {
    public static void main(String[] args) {
        Person p = Person.builder()
                .name("zhangsan")   //返回this指的是 builder对象
                .age(18)  //再调用age() 返回this指的是 builder对象
                .gender("F") //到此 builder中三个属性 值已满
                .build();  //在builder中的属性值传给Person
        System.out.println(p);
    }
}
