package org.example;

import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static CarDealership dealership = new CarDealership();
    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();

    public static void main(String[] args) {
        System.out.println("=== СИСТЕМА УПРАВЛЕНИЯ АВТОПАРКОМ ===\n");
        
        // Инициализация тестовых данных
        initializeTestData();
        
        // Демонстрация всех задач
        demonstrateAllTasks();
        
        // Интерактивное меню
        showMainMenu();
    }

    private static void initializeTestData() {
        System.out.println("Инициализация тестовых данных...\n");
        
        // Добавляем тестовые машины
        dealership.addCar(new Car("VIN001", "Camry", "Toyota", 2020, 35000, 2500000, CarType.SEDAN));
        dealership.addCar(new Car("VIN002", "X5", "BMW", 2019, 45000, 4500000, CarType.SUV));
        dealership.addCar(new Car("VIN003", "Model S", "Tesla", 2022, 15000, 6000000, CarType.ELECTRIC));
        dealership.addCar(new Car("VIN004", "Accord", "Honda", 2021, 25000, 2200000, CarType.SEDAN));
        dealership.addCar(new Car("VIN005", "Q7", "Audi", 2018, 60000, 4000000, CarType.SUV));
        dealership.addCar(new Car("VIN006", "Corolla", "Toyota", 2023, 5000, 1800000, CarType.SEDAN));
        dealership.addCar(new Car("VIN007", "Model 3", "Tesla", 2023, 8000, 4500000, CarType.ELECTRIC));
        dealership.addCar(new Car("VIN008", "CX-5", "Mazda", 2020, 40000, 2800000, CarType.SUV));
        
        // Попытка добавить дубликат
        System.out.println("\nПопытка добавить дубликат:");
        dealership.addCar(new Car("VIN001", "Другая модель", "Другой производитель", 2000, 100000, 500000, CarType.COUPE));
    }

    private static void demonstrateAllTasks() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("ДЕМОНСТРАЦИЯ ВСЕХ ЗАДАЧ");
        System.out.println("=".repeat(50));
        
        task1_Arrays();
        task2_Collections();
        task3_EqualsHashCode();
        task4_StreamAPI();
    }

    // ЗАДАЧА 1: Массивы
    private static void task1_Arrays() {
        System.out.println("\n--- ЗАДАЧА 1: МАССИВЫ ---");
        
        // Создаем массив из 50 случайных годов (2000-2025)
        int[] carYears = new int[50];
        for (int i = 0; i < carYears.length; i++) {
            carYears[i] = 2000 + random.nextInt(26); // 2000-2025
        }
        
        System.out.println("Создан массив из 50 случайных годов выпуска автомобилей:");
        System.out.println(Arrays.toString(carYears));
        
        // Фильтруем машины после 2015 года
        System.out.println("\nМашины, выпущенные после 2015 года:");
        Arrays.stream(carYears)
                .filter(year -> year > 2015)
                .forEach(year -> System.out.print(year + " "));
        
        // Считаем средний возраст
        int currentYear = Year.now().getValue();
        double averageAge = Arrays.stream(carYears)
                .mapToDouble(year -> currentYear - year)
                .average()
                .orElse(0);
        
        System.out.printf("\n\nСредний возраст автомобилей: %.2f лет\n", averageAge);
    }

    // ЗАДАЧА 2: Коллекции
    private static void task2_Collections() {
        System.out.println("\n--- ЗАДАЧА 2: КОЛЛЕКЦИИ ---");
        
        // Создаем список с дубликатами
        List<String> carModels = new ArrayList<>(Arrays.asList(
                "Toyota Camry", "BMW X5", "Honda Accord", "Toyota Camry", 
                "Tesla Model S", "Audi Q7", "BMW X5", "Tesla Model 3",
                "Mazda CX-5", "Honda Civic", "Tesla Roadster"
        ));
        
        System.out.println("Исходный список моделей (с дубликатами):");
        carModels.forEach(System.out::println);
        
        // Удаляем дубликаты и сортируем в обратном алфавитном порядке
        Set<String> uniqueModels = new LinkedHashSet<>();
        List<String> processedModels = carModels.stream()
                .distinct()
                .sorted(Collections.reverseOrder())
                .map(model -> model.contains("Tesla") ? "ELECTRO_CAR" : model)
                .collect(Collectors.toList());
        
        System.out.println("\nОбработанный список (без дубликатов, обратная сортировка, Tesla заменена):");
        processedModels.forEach(System.out::println);
        
        // Сохраняем в Set
        Set<String> modelSet = new HashSet<>(processedModels);
        System.out.println("\nКоличество уникальных моделей в Set: " + modelSet.size());
    }

    // ЗАДАЧА 3: equals/hashCode
    private static void task3_EqualsHashCode() {
        System.out.println("\n--- ЗАДАЧА 3: EQUALS/HASHCODE ---");
        
        Set<Car> testCars = new HashSet<>();
        
        // Добавляем машины, включая дубликаты по VIN
        Car car1 = new Car("TEST001", "Camry", "Toyota", 2020, 30000, 2000000, CarType.SEDAN);
        Car car2 = new Car("TEST002", "X5", "BMW", 2019, 40000, 4000000, CarType.SUV);
        Car car3 = new Car("TEST001", "Другая модель", "Другой производитель", 2021, 50000, 3000000, CarType.COUPE); // Дубликат VIN
        
        System.out.println("Добавляем машины в HashSet:");
        System.out.println("Машина 1 добавлена: " + testCars.add(car1));
        System.out.println("Машина 2 добавлена: " + testCars.add(car2));
        System.out.println("Машина 3 (дубликат VIN) добавлена: " + testCars.add(car3));
        
        System.out.println("\nВсего машин в HashSet: " + testCars.size());
        
        // Демонстрация сортировки по году
        List<Car> sortedCars = testCars.stream().sorted().collect(Collectors.toList());
        System.out.println("\nМашины, отсортированные по году (от новых к старым):");
        sortedCars.forEach(System.out::println);
    }

    // ЗАДАЧА 4: Stream API
    private static void task4_StreamAPI() {
        System.out.println("\n--- ЗАДАЧА 4: STREAM API ---");
        
        // Машины с пробегом меньше 50,000 км, отсортированные по цене
        System.out.println("Машины с пробегом меньше 50,000 км (по убыванию цены):");
        dealership.getCarsWithLowMileage().forEach(System.out::println);
        
        // Топ-3 самые дорогие
        System.out.println("\nТоп-3 самые дорогие машины:");
        dealership.getTop3MostExpensive().forEach(System.out::println);
        
        // Средний пробег
        OptionalDouble avgMileage = dealership.getAverageMileage();
        if (avgMileage.isPresent()) {
            System.out.printf("\nСредний пробег всех машин: %.0f км\n", avgMileage.getAsDouble());
        }
        
        // Группировка по производителю
        System.out.println("\nМашины, сгруппированные по производителю:");
        dealership.groupCarsByManufacturer().forEach((manufacturer, cars) -> {
            System.out.println(manufacturer + " (" + cars.size() + " машин):");
            cars.forEach(car -> System.out.println("  " + car.getModel()));
        });
    }

    private static void showMainMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("ИНТЕРАКТИВНОЕ МЕНЮ АВТОЦЕНТРА");
        System.out.println("=".repeat(50));
        
        while (true) {
            System.out.println("\nВыберите действие:");
            System.out.println("1. Добавить машину");
            System.out.println("2. Найти машины по производителю");
            System.out.println("3. Средняя цена машин по типу");
            System.out.println("4. Показать машины по годам (от новых к старым)");
            System.out.println("5. Статистика по типам");
            System.out.println("6. Самая старая и новая машина");
            System.out.println("7. Показать все машины");
            System.out.println("8. Stream API операции");
            System.out.println("0. Выход");
            System.out.print("Ваш выбор: ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
                
                switch (choice) {
                    case 1: addCarMenu(); break;
                    case 2: findCarsByManufacturerMenu(); break;
                    case 3: averagePriceByTypeMenu(); break;
                    case 4: showCarsSortedByYear(); break;
                    case 5: showStatisticsByType(); break;
                    case 6: showOldestAndNewest(); break;
                    case 7: showAllCars(); break;
                    case 8: streamApiMenu(); break;
                    case 0: 
                        System.out.println("До свидания!");
                        return;
                    default:
                        System.out.println("Неверный выбор!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Введите корректное число!");
                scanner.nextLine(); // clear invalid input
            }
        }
    }

    private static void addCarMenu() {
        System.out.println("\n--- ДОБАВЛЕНИЕ МАШИНЫ ---");
        try {
            System.out.print("VIN: ");
            String vin = scanner.nextLine();
            System.out.print("Модель: ");
            String model = scanner.nextLine();
            System.out.print("Производитель: ");
            String manufacturer = scanner.nextLine();
            System.out.print("Год выпуска: ");
            int year = scanner.nextInt();
            System.out.print("Пробег (км): ");
            int mileage = scanner.nextInt();
            System.out.print("Цена (руб): ");
            double price = scanner.nextDouble();
            scanner.nextLine(); // consume newline
            
            System.out.println("Доступные типы: " + Arrays.toString(CarType.values()));
            System.out.print("Тип автомобиля: ");
            String typeStr = scanner.nextLine().toUpperCase();
            CarType type = CarType.valueOf(typeStr);
            
            Car newCar = new Car(vin, model, manufacturer, year, mileage, price, type);
            dealership.addCar(newCar);
        } catch (Exception e) {
            System.out.println("Ошибка ввода данных: " + e.getMessage());
        }
    }

    private static void findCarsByManufacturerMenu() {
        System.out.print("\nВведите производителя: ");
        String manufacturer = scanner.nextLine();
        List<Car> cars = dealership.findCarsByManufacturer(manufacturer);
        
        if (cars.isEmpty()) {
            System.out.println("Машины производителя " + manufacturer + " не найдены.");
        } else {
            System.out.println("\nНайденные машины производителя " + manufacturer + ":");
            cars.forEach(System.out::println);
        }
    }

    private static void averagePriceByTypeMenu() {
        System.out.println("Доступные типы: " + Arrays.toString(CarType.values()));
        System.out.print("Введите тип автомобиля: ");
        try {
            String typeStr = scanner.nextLine().toUpperCase();
            CarType type = CarType.valueOf(typeStr);
            OptionalDouble avgPrice = dealership.getAveragePriceByType(type);
            
            if (avgPrice.isPresent()) {
                System.out.printf("Средняя цена машин типа %s: %.2f руб.\n", type, avgPrice.getAsDouble());
            } else {
                System.out.println("Машины типа " + type + " не найдены.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Неверный тип автомобиля!");
        }
    }

    private static void showCarsSortedByYear() {
        System.out.println("\nМашины по годам выпуска (от новых к старым):");
        dealership.getCarsSortedByYear().forEach(System.out::println);
    }

    private static void showStatisticsByType() {
        System.out.println("\nСтатистика по типам автомобилей:");
        dealership.getCarCountByType().forEach((type, count) -> 
            System.out.println(type + ": " + count + " машин"));
    }

    private static void showOldestAndNewest() {
        Optional<Car> oldest = dealership.getOldestCar();
        Optional<Car> newest = dealership.getNewestCar();
        
        System.out.println("\nСамая старая машина:");
        oldest.ifPresentOrElse(System.out::println, () -> System.out.println("Нет данных"));
        
        System.out.println("\nСамая новая машина:");
        newest.ifPresentOrElse(System.out::println, () -> System.out.println("Нет данных"));
    }

    private static void showAllCars() {
        System.out.println("\nВсе машины в автоцентре (" + dealership.getSize() + " шт.):");
        dealership.getAllCars().forEach(System.out::println);
    }

    private static void streamApiMenu() {
        System.out.println("\n--- STREAM API ОПЕРАЦИИ ---");
        System.out.println("1. Машины с малым пробегом (< 50,000 км)");
        System.out.println("2. Топ-3 дорогие машины");
        System.out.println("3. Средний пробег");
        System.out.println("4. Группировка по производителям");
        System.out.print("Выберите операцию: ");
        
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    System.out.println("Машины с пробегом < 50,000 км:");
                    dealership.getCarsWithLowMileage().forEach(System.out::println);
                    break;
                case 2:
                    System.out.println("Топ-3 самые дорогие машины:");
                    dealership.getTop3MostExpensive().forEach(System.out::println);
                    break;
                case 3:
                    OptionalDouble avgMileage = dealership.getAverageMileage();
                    if (avgMileage.isPresent()) {
                        System.out.printf("Средний пробег: %.0f км\n", avgMileage.getAsDouble());
                    } else {
                        System.out.println("Нет данных для расчета");
                    }
                    break;
                case 4:
                    System.out.println("Группировка по производителям:");
                    dealership.groupCarsByManufacturer().forEach((manufacturer, cars) -> {
                        System.out.println(manufacturer + " (" + cars.size() + "):");
                        cars.forEach(car -> System.out.println("  " + car.getModel()));
                    });
                    break;
                default:
                    System.out.println("Неверный выбор!");
            }
        } catch (InputMismatchException e) {
            System.out.println("Введите корректное число!");
            scanner.nextLine();
        }
    }
}