package com.pm.uslocations.lab;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {
    private String name;
    private Integer age;
    private String grade;
    private String school;
    private String major;
    private String teacher;

    @Override
    public String toString() {
        return "The Student name is " +
                "Name is " + name +"\n"+
                "His Age is " + age + "\n"+
                "His Teacher is " + teacher;
    }
}
