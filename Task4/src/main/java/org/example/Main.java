package org.example;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== ДЕМОНСТРАЦИЯ ЛЯМБДА-ВЫРАЖЕНИЙ ===\n");
        
        // 1. Лямбда выражение для интерфейса Printable
        System.out.println("1. Лямбда выражение для интерфейса Printable:");
        Printable printable = () -> System.out.println("Лямбда выражение...");
        printable.print();
        System.out.println();
        
        // 2. Проверка пустой строки
        System.out.println("2. Проверка пустой строки:");
        
        // a) Проверка на null
        Predicate<String> notNull = str -> str != null;
        System.out.println("Строка 'Привет' не null: " + notNull.test("Привет"));
        System.out.println("Строка null не null: " + notNull.test(null));
        
        // b) Проверка на пустоту
        Predicate<String> notEmpty = str -> !str.isEmpty();
        System.out.println("Строка 'Привет' не пуста: " + notEmpty.test("Привет"));
        System.out.println("Строка '' не пуста: " + notEmpty.test(""));
        
        // c) Комбинированная проверка с помощью and()
        Predicate<String> notNullAndNotEmpty = notNull.and(notEmpty);
        System.out.println("Строка 'Привет' не null и не пуста: " + notNullAndNotEmpty.test("Привет"));
        System.out.println("Строка null не null и не пуста: " + notNullAndNotEmpty.test(null));
        System.out.println("Строка '' не null и не пуста: " + notNullAndNotEmpty.test(""));
        System.out.println();
        
        // 3. Проверка строки
        System.out.println("3. Проверка строки:");
        Predicate<String> startsWithJOrN = str -> str.startsWith("J") || str.startsWith("N");
        Predicate<String> endsWithA = str -> str.endsWith("A");
        Predicate<String> complexCheck = startsWithJOrN.and(endsWithA);
        
        System.out.println("Строка 'JAVA' начинается с J или N и заканчивается A: " + complexCheck.test("JAVA"));
        System.out.println("Строка 'NASA' начинается с J или N и заканчивается A: " + complexCheck.test("NASA"));
        System.out.println("Строка 'KOTLIN' начинается с J или N и заканчивается A: " + complexCheck.test("KOTLIN"));
        System.out.println();
        
        // 4. Лямбда выражение для HeavyBox
        System.out.println("4. Лямбда выражение для HeavyBox:");
        Consumer<HeavyBox> shipBox = box -> System.out.println("Отгрузили ящик с весом " + box.getWeight());
        Consumer<HeavyBox> sendBox = box -> System.out.println("Отправляем ящик с весом " + box.getWeight());
        Consumer<HeavyBox> processBox = shipBox.andThen(sendBox);
        
        HeavyBox box1 = new HeavyBox(25);
        HeavyBox box2 = new HeavyBox(40);
        processBox.accept(box1);
        processBox.accept(box2);
        System.out.println();
        
        // 5. Лямбда для Function
        System.out.println("5. Лямбда для Function:");
        Function<Integer, String> numberClassifier = number -> {
            if (number > 0) {
                return "Положительное число";
            } else if (number < 0) {
                return "Отрицательное число";
            } else {
                return "Ноль";
            }
        };
        
        System.out.println("Число 5: " + numberClassifier.apply(5));
        System.out.println("Число -3: " + numberClassifier.apply(-3));
        System.out.println("Число 0: " + numberClassifier.apply(0));
        System.out.println();
        
        // 6. Лямбда для Supplier
        System.out.println("6. Лямбда для Supplier:");
        Supplier<Integer> randomSupplier = () -> new Random().nextInt(11);
        System.out.println("Случайное число от 0 до 10: " + randomSupplier.get());
        System.out.println("Случайное число от 0 до 10: " + randomSupplier.get());
        System.out.println("Случайное число от 0 до 10: " + randomSupplier.get());
        System.out.println();
        
        System.out.println("=== ДЕМОНСТРАЦИЯ АННОТАЦИЙ И РЕФЛЕКСИИ ===\n");
        
        // 7. Кастомная аннотация @DeprecatedEx
        System.out.println("7. Кастомная аннотация @DeprecatedEx:");
        DeprecatedProcessor.processDeprecated(TestClass.class);
        System.out.println();
        
        // 8. Кастомная сериализация в JSON с аннотацией @JsonField
        System.out.println("8. Кастомная сериализация в JSON с аннотацией @JsonField:");
        TestClass testObj = new TestClass("Иван", 25, "ivan@email.com");
        String json = JsonSerializer.toJson(testObj);
        System.out.println("JSON результат: " + json);
        System.out.println();
        
        System.out.println("=== ДЕМОНСТРАЦИЯ ЗАВЕРШЕНА ===");
    }
}