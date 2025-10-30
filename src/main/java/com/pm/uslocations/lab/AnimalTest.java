package com.pm.uslocations.lab;

public class AnimalTest {
    public static void main(String[] args) {
        Animal animal = new Cow();
        System.out.println(animal.sound());
        System.out.println(animal.speed());

        Animal animal2 = new Lion();
        System.out.println(animal2.sound());
        System.out.println(animal2.speed());
    }
}
