package com.yc.bean1;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class Test3 {
    public static void main(String[] args) {
        Test3 t = new Test3();
        List<Integer> list = t.all();
        for(Integer i:list){
            System.out.print(i + "\t");
        }
        System.out.println();
        for(int i = 1;i<list.size();i++){
            System.out.print(list.get(i) + "\t");
        }
        System.out.println();

        //Stream写法
        Stream<Integer> stream = t.all2();
        //传统方法  map（）映射
        /*
            int ele = stream.get（i）
         */
        stream.map(new Function<Integer,String >() {
            @Override
            public String apply(Integer o) {
                return o+"\t";
            }
        }).map(o -> o+":").map(o->o + "=" + o.length() + "\n").filter(o -> o.startsWith("1")).forEach(System.out::print);
//
//        //升级lambda
//        stream.map(o -> o + "\t").forEach(System.out::print);
        System.out.println();

        //3.
        Flux<Integer> flux = t.all3();
        //订阅Flux序列 只有进行订阅后才触发数据流 不订阅什么都不会发生
        flux.map(o -> o+"\t").subscribe(System.out::print);
        System.out.println();

        //4.Mono只发出一个项 因此 当我们期望最多只有一个结果时 应该使用Mono 当我们期望有多个结果时 应该使用Flux
        Mono<String> mono = t.all4();
        mono.as(v -> v.map(o -> o + "\t map").subscribe(System.out::println));

    }

    //1.传统写法
    public List<Integer> all(){
        return Arrays.asList(1,2,3,4,5,6);
    }

    //2.Stream写法
    public Stream<Integer> all2(){
        return Stream.of(1,2,3,4,5,6);
    }

    //3.反应式Flux
    public Flux<Integer> all3(){
//        return Flux.fromArray(new Integer[]{1,2,3,4,5,6});  //还有fromIterable，fromStream
        return Flux.just(1,2,3,4,5,6);
    }

    //4.反应式Mono 只能响应一个数据
    public Mono<String > all4(){
        return Mono.just("hello world");
    }
}
