package factory.factorymethod2;

import factory.ICourse;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FactoryMethodTest {
    public static void main(String[] args) {
        //课程 工厂
        ICourseFactory factory = new PythonCourseFactory();
        //利用工厂创建课程
        ICourse course = factory.create();
        course.record();

        factory = new JavaCourseFactory();
        course = factory.create();
        course.record();

        //java.util.List就是工厂方法模式的一个应用
        //List接口下有不同的实现
        List ll = new ArrayList();
        List l = new LinkedList();
        ll.iterator();
    }
}
