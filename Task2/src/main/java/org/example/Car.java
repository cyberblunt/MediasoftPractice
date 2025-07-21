package org.example;

import java.util.Objects;

public class Car implements Comparable<Car> {
    private String vin;
    private String model;
    private String manufacturer;
    private int year;
    private int mileage;
    private double price;
    private CarType type;

    public Car(String vin, String model, String manufacturer, int year, int mileage, double price, CarType type) {
        this.vin = vin;
        this.model = model;
        this.manufacturer = manufacturer;
        this.year = year;
        this.mileage = mileage;
        this.price = price;
        this.type = type;
    }

    // Геттеры
    public String getVin() { return vin; }
    public String getModel() { return model; }
    public String getManufacturer() { return manufacturer; }
    public int getYear() { return year; }
    public int getMileage() { return mileage; }
    public double getPrice() { return price; }
    public CarType getType() { return type; }

    // Сеттеры
    public void setVin(String vin) { this.vin = vin; }
    public void setModel(String model) { this.model = model; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }
    public void setYear(int year) { this.year = year; }
    public void setMileage(int mileage) { this.mileage = mileage; }
    public void setPrice(double price) { this.price = price; }
    public void setType(CarType type) { this.type = type; }

    // Переопределение equals - сравнение только по VIN
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Car car = (Car) obj;
        return Objects.equals(vin, car.vin);
    }

    // Переопределение hashCode - хэширование только по VIN
    @Override
    public int hashCode() {
        return Objects.hash(vin);
    }

    // Сравнение для сортировки по году выпуска (от новых к старым)
    @Override
    public int compareTo(Car other) {
        return Integer.compare(other.year, this.year); // обратный порядок
    }

    @Override
    public String toString() {
        return String.format("%s %s %s (%d год) - %.2f руб., пробег: %d км, VIN: %s",
                manufacturer, model, type, year, price, mileage, vin);
    }
} 