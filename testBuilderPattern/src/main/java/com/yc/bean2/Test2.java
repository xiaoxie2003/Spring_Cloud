package com.yc.bean2;

public class Test2 {
    public static void main(String[] args) {
        Apple a = Apple.builder().name("apple").price(40).build();
        System.out.println(a);
    }
}
