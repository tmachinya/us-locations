package com.pm.uslocations.lab;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cow implements Animal{
    private String color;
    private String name;

    public String sound(){
        return "Moo";
    }

    public Integer speed(){
        return 10;
    }
}
