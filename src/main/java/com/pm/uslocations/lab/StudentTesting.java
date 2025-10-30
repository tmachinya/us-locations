package com.pm.uslocations.lab;

public class StudentTesting {
    public static void main(String[] args) {
//        Student student = new Student();
//        student.setName("Brighton");
//        student.setTeacher("Tongesai");
//
//        System.out.println("The Student name is  "+student.getName());
//        System.out.println("His teacher is "+student.getTeacher());

        System.out.println(
                Student.builder()
                        .name("Brighton")
                        .teacher("Tongesai")
                        .age(31).build()
        );
    }
}
