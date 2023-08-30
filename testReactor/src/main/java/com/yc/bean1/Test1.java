package com.yc.bean1;

import java.util.Observable;
import java.util.Observer;

public class Test1 {
    public static void main(String[] args) {
        ObserverDemo observerDemo = new ObserverDemo();
        //注册观察者   o:被观察的对象    arg：被观察对象变化的值 通知观察者
//        observerDemo.addObserver((o,arg) -> {
//            System.out.println(o + "数据发送变化A：" + arg);
//        });

        observerDemo.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                System.out.println(o + "数据发送变化A：" + arg);
            }
        });
        //注册观察者   o:被观察的对象    arg：被观察对象变化的值 通知观察者
        observerDemo.addObserver((o,arg) -> {
            System.out.println(o + "数据发送变化B：" + arg);
        });
        //变更数据，发出通知
        observerDemo.updateNumber();
        observerDemo.updateNumber();
    }
}

//被观察对象
class ObserverDemo extends Observable{
    int i = 0; //变化的数据
    public void updateNumber(){
        i++;
        //数据发送变更
        this.setChanged();
        //通知观察者数据变更了，传过去最新的值
        this.notifyObservers(i);
    }
}

/*
    Observable ： List<Observer>
            notifyObservers(Object updateObject){
                    //调用observer的业务方法
            }
 */

