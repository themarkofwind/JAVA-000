package org.macbeth.java.framework.model;

import java.util.List;

public class Clazz {

    private List<Student> students;

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void list() {
        System.out.println(students);
    }
}
