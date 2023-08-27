package factory.simplefactory1;

import factory.JavaCource;

public class SimpleFactoryTest {
    public static void main(String[] args) {
        CourseFactory cf = new CourseFactory();
//        JavaCource jc = (JavaCource) cf.create(JavaCource.class);
        JavaCource jc = (JavaCource) cf.create("java");

        System.out.println(jc);
    }
}
