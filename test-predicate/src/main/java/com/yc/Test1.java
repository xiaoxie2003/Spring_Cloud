package com.yc;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 谓词
 */
public class Test1 {

    class A implements Predicate<String>{
        @Override
        public boolean test(String s) {
            if(s.length() > 3){
                return true;
            }
            return false;
        }
    }

    public static void main(String[] args) {
        Predicate p;
        BiPredicate pp;

        //使用Predicate过滤集合
        List<String> ns = Arrays.asList("Alice","Bob","Charlie","David"); //创建不可变集合
        List<String> names = new ArrayList<>(ns); //转为可变集合 以便后面使用removeif删除元素

        //定义一个Predicate 判断字符串长度是否大于3
        Predicate<String> lengthPredicate = s -> s.length() > 3;  //函数式写法
        //使用removeIf方法移除满足Predicate的元素（即元素长度大于3的字符串都删除）

        //1.函数写法
        //names.removeIf(lengthPredicate);

        //2.匿名内部类写法
//        names.removeIf(new Predicate<String>() {
//            @Override
//            public boolean test(String s) {
//                if(s.length() > 3){
//                    return true;
//                }
//                return false;
//            }
//        });

        //3.lambda写法
        names.removeIf( o -> o.length() > 3);

        System.out.println(names); //[Bob]

        //TODO:Predicate<T>add 方法使用
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        Predicate<Integer> predicate1 = x -> x > 3;
        Predicate<Integer> predicate2 = x -> x < 9;

        //jdk8的Stream流式计算 运用了构造器模式
        //过滤大于3小于9的元素
        List<Integer> collect = list.stream().filter(predicate1.and(predicate2)).collect(Collectors.toList());
        System.out.println(collect); //[4, 5, 6, 7, 8]

        //TODO:or方法使用
        collect = list.stream().filter(predicate1.or(predicate2)).collect(Collectors.toList());
        System.out.println(collect); //[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]

        //TODO:negate否定的使用
        List<String> list3 = Arrays.asList("java","c++","c","php","kotlin","go");
        Predicate<String> predicate3 = x -> x.endsWith("+");
        //不以+号结束
        List<String> collect3 = list3.stream().filter(predicate3.negate()).collect(Collectors.toList());
        System.out.println(collect3); //[java, c, php, kotlin, go]

        //TODO:test使用
        List<String> list4 = Arrays.asList("java","c++","c","php","kotlin","go");
        Predicate<String> predicate4 = x -> x.endsWith("+");
        List<String> collect4 = list3.stream().filter(predicate4::test).collect(Collectors.toList());
        System.out.println(collect4); //[c++]

        //TODO:predicate链式调用
        List<String> list5 = Arrays.asList("java","c++","c","php","kotlin","javascript");
        Predicate<String> predicate5 = x -> x.startsWith("c");
        //以c开头或者以t结尾
        boolean ret = predicate5.or(x -> x.endsWith("t")).test("javascript");
        System.out.println(ret); //true
        //！（以c开头长度等于4）
        boolean ret2 = predicate5.and(x -> x.length() == 4).negate().test("java");
        System.out.println(ret2); //true

        //使用BiPredicate判断两个字符串是否相等
        BiPredicate<String,String>equlPredicate = (s1,s2) -> s1.equals(s2);
        System.out.println(equlPredicate.test("Hello","Hello")); //true
        System.out.println(equlPredicate.test("hello","he")); //false
    }
}
