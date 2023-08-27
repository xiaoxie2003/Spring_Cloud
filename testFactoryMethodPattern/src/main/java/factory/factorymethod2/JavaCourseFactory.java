package factory.factorymethod2;

import factory.ICourse;
import factory.JavaCource;

public class JavaCourseFactory implements ICourseFactory{
    @Override
    public ICourse create() {
        return new JavaCource();
    }
}
