package factory.simplefactory1;

import factory.ICourse;
import factory.JavaCource;
import factory.PythonCourse;

//工厂
public class CourseFactory {

    //简单工厂 实现方案一： 通过if..else判断完成
    public ICourse create(String name){
        if("java".equals(name)){
            return new JavaCource();
        }else if ("python".equals(name)){
            return new PythonCourse();
        }else {
            return null;
        }
    }

    //方法一满足高内聚低耦合 但是违背了开闭原则
    //开闭原则：对拓展开放，对修改关闭

    //简单工厂 实现方案二： 通过反射完成对象创建
//    public ICourse create(String className) {
//        if (!(null == className) || "".equals(className)) {
//            try {
//                return (ICourse) Class.forName(className).newInstance();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }

    //简单工厂 实现方案三： 通过class完成对象创建
//    public ICourse create(Class<? extends ICourse> clazz){
//        if(null != clazz){
//            try {
//                return clazz.newInstance();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
}
