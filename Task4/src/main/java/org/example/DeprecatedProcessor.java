package org.example;

import java.lang.reflect.Method;

//Обработчик для аннотации @DeprecatedEx
public class DeprecatedProcessor {
    
    public static void processDeprecated(Class<?> clazz) {
        // Проверяем класс на наличие аннотации
        if (clazz.isAnnotationPresent(DeprecatedEx.class)) {
            DeprecatedEx annotation = clazz.getAnnotation(DeprecatedEx.class);
            System.out.println("Внимание: класс '" + clazz.getSimpleName() + 
                             "' устарел. Альтернатива: '" + annotation.message() + "'");
        }
        
        // Проверяем методы на наличие аннотации
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(DeprecatedEx.class)) {
                DeprecatedEx annotation = method.getAnnotation(DeprecatedEx.class);
                System.out.println("Внимание: метод '" + method.getName() + 
                                 "' устарел. Альтернатива: '" + annotation.message() + "'");
            }
        }
    }
} 