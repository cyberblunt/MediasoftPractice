package org.example;

import java.lang.reflect.Field;

//Класс-сериализатор для преобразования объектов в JSON
public class JsonSerializer {
    
    public static String toJson(Object obj) {
        StringBuilder json = new StringBuilder("{");
        
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        
        boolean first = true;
        for (Field field : fields) {
            if (field.isAnnotationPresent(JsonField.class)) {
                JsonField jsonField = field.getAnnotation(JsonField.class);
                
                field.setAccessible(true);
                try {
                    Object value = field.get(obj);
                    
                    if (!first) {
                        json.append(", ");
                    }
                    
                    json.append("\"").append(jsonField.name()).append("\": ");
                    
                    if (value instanceof String) {
                        json.append("\"").append(value).append("\"");
                    } else {
                        json.append(value);
                    }
                    
                    first = false;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        
        json.append("}");
        return json.toString();
    }
} 