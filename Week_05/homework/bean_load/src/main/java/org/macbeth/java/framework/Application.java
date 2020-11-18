package org.macbeth.java.framework;

import org.macbeth.java.framework.model.Clazz;
import org.macbeth.java.framework.model.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Student student1 = (Student) context.getBean("student1");
        Student student2 = (Student) context.getBean("student2");
        System.out.println("student1: " + student1.toString());
        System.out.println("student2: " + student2.toString());
        Clazz clazz0203 = (Clazz) context.getBean("clazz0203");
        System.out.println("class 0203 has students: " + clazz0203.getStudents());
    }
}
