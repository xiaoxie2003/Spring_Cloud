package factory.factorymethod2;

import factory.ICourse;
import factory.PythonCourse;

public class PythonCourseFactory implements ICourseFactory{
    @Override
    public ICourse create() {
        return new PythonCourse();
    }
}
