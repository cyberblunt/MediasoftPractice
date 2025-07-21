package org.example;

//Класс для демонстрации работы с Consumer
public class HeavyBox {
    private int weight;

    public HeavyBox(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "HeavyBox{weight=" + weight + "}";
    }
} 