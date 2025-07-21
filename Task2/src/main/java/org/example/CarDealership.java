package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class CarDealership {
    private Set<Car> cars;

    public CarDealership() {
        this.cars = new HashSet<>();
    }

    // 1. Добавить машину в автоцентр (проверка дубликатов по VIN)
    public boolean addCar(Car car) {
        boolean added = cars.add(car);
        if (added) {
            System.out.println("Машина добавлена: " + car);
        } else {
            System.out.println("Машина с VIN " + car.getVin() + " уже существует!");
        }
        return added;
    }

    // 2. Найти все машины указанного производителя
    public List<Car> findCarsByManufacturer(String manufacturer) {
        return cars.stream()
                .filter(car -> car.getManufacturer().equalsIgnoreCase(manufacturer))
                .collect(Collectors.toList());
    }

    // 3. Вывести среднюю цену машин определённого типа
    public OptionalDouble getAveragePriceByType(CarType type) {
        return cars.stream()
                .filter(car -> car.getType() == type)
                .mapToDouble(Car::getPrice)
                .average();
    }

    // 4. Список машин, отсортированных по году выпуска (от новых к старым)
    public List<Car> getCarsSortedByYear() {
        return cars.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    // 5. Статистика: количество машин каждого типа
    public Map<CarType, Long> getCarCountByType() {
        return cars.stream()
                .collect(Collectors.groupingBy(Car::getType, Collectors.counting()));
    }

    // 5. Статистика: самая старая машина
    public Optional<Car> getOldestCar() {
        return cars.stream()
                .min(Comparator.comparingInt(Car::getYear));
    }

    // 5. Статистика: самая новая машина
    public Optional<Car> getNewestCar() {
        return cars.stream()
                .max(Comparator.comparingInt(Car::getYear));
    }

    // Дополнительные методы для Stream API задач
    
    // Фильтрация машин с пробегом меньше 50_000 км, сортировка по цене
    public List<Car> getCarsWithLowMileage() {
        return cars.stream()
                .filter(car -> car.getMileage() < 50_000)
                .sorted(Comparator.comparingDouble(Car::getPrice).reversed())
                .collect(Collectors.toList());
    }

    // Топ-3 самые дорогие машины
    public List<Car> getTop3MostExpensive() {
        return cars.stream()
                .sorted(Comparator.comparingDouble(Car::getPrice).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    // Средний пробег всех машин
    public OptionalDouble getAverageMileage() {
        return cars.stream()
                .mapToInt(Car::getMileage)
                .average();
    }

    // Группировка машин по производителю
    public Map<String, List<Car>> groupCarsByManufacturer() {
        return cars.stream()
                .collect(Collectors.groupingBy(Car::getManufacturer));
    }

    // Получить все машины
    public Set<Car> getAllCars() {
        return new HashSet<>(cars);
    }

    // Размер автопарка
    public int getSize() {
        return cars.size();
    }
} 