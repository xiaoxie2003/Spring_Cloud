package com.yc.bean;

import lombok.Builder;

import javax.swing.text.html.parser.TagElement;

public class Person {
    private String name;
    private Integer age;
    private String gender;

    public Person(){

    }

    public Person(Builder b){
        this.name = b.name;
        this.age = b.age;
        this.gender = b.gender;
    }

    //公有static方法返回实体类的builder
    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder{
        //通常拥有宿主实体类的全部属性
        private String name;
        private Integer age;
        private String gender;
        //以实体属性名作为方法名，为属性赋值，并返回this builder
        public Builder name(String name){
            this.name = name;
            return this;
        }
        public Builder age(Integer age){
            this.age = age;
            return this;
        }
        public Builder gender(String gender){
            this.gender = gender;
            return this;
        }

        //最后提供一个build方法 使用builder收集来的属性创建实体类
        //实体类的创建方式多种多样 只要达到目标即可 通常实体类提供全属性的构造器 或者以Builder为参数的构造器
        public Person build(){
            return new Person(this);
        }
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }
}
