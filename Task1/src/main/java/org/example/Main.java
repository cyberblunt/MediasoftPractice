package org.example;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Тестирование класса BankAccount ===\n");
        
        // Создание банковских счетов
        BankAccount account1 = new BankAccount("Иван Иванов");
        BankAccount account2 = new BankAccount("Петр Петров");
        
        System.out.println("Созданы счета:");
        System.out.println("Счет 1: " + account1.getOwnerName() + ", баланс: " + account1.getBalance());
        System.out.println("Счет 2: " + account2.getOwnerName() + ", баланс: " + account2.getBalance());
        System.out.println("Дата открытия счета 1: " + account1.getOpeningDate());
        System.out.println();
        
        // Тестирование пополнения счета
        System.out.println("=== Тестирование пополнения счета ===");
        boolean result1 = account1.deposit(1000);
        System.out.println("Пополнение счета 1 на 1000: " + (result1 ? "успешно" : "неуспешно"));
        System.out.println("Баланс счета 1: " + account1.getBalance());
        
        boolean result2 = account1.deposit(-500);
        System.out.println("Пополнение счета 1 на -500: " + (result2 ? "успешно" : "неуспешно"));
        System.out.println("Баланс счета 1: " + account1.getBalance());
        System.out.println();
        
        // Тестирование снятия денег
        System.out.println("=== Тестирование снятия денег ===");
        boolean result3 = account1.withdraw(300);
        System.out.println("Снятие 300 со счета 1: " + (result3 ? "успешно" : "неуспешно"));
        System.out.println("Баланс счета 1: " + account1.getBalance());
        
        boolean result4 = account1.withdraw(1000);
        System.out.println("Снятие 1000 со счета 1: " + (result4 ? "успешно" : "неуспешно"));
        System.out.println("Баланс счета 1: " + account1.getBalance());
        System.out.println();
        
        // Тестирование перевода между счетами
        System.out.println("=== Тестирование перевода между счетами ===");
        System.out.println("До перевода - Счет 1: " + account1.getBalance() + ", Счет 2: " + account2.getBalance());
        
        boolean result5 = account1.transfer(account2, 200);
        System.out.println("Перевод 200 со счета 1 на счет 2: " + (result5 ? "успешно" : "неуспешно"));
        System.out.println("После перевода - Счет 1: " + account1.getBalance() + ", Счет 2: " + account2.getBalance());
        
        boolean result6 = account1.transfer(account2, 1000);
        System.out.println("Перевод 1000 со счета 1 на счет 2: " + (result6 ? "успешно" : "неуспешно"));
        System.out.println("После попытки перевода - Счет 1: " + account1.getBalance() + ", Счет 2: " + account2.getBalance());
        System.out.println();
        
        // Тестирование блокировки счета
        System.out.println("=== Тестирование блокировки счета ===");
        account1.setBlocked(true);
        System.out.println("Счет 1 заблокирован: " + account1.isBlocked());
        
        boolean result7 = account1.deposit(500);
        System.out.println("Пополнение заблокированного счета на 500: " + (result7 ? "успешно" : "неуспешно"));
        
        boolean result8 = account1.withdraw(100);
        System.out.println("Снятие 100 с заблокированного счета: " + (result8 ? "успешно" : "неуспешно"));
        
        boolean result9 = account1.transfer(account2, 100);
        System.out.println("Перевод 100 с заблокированного счета: " + (result9 ? "успешно" : "неуспешно"));
        
        // Разблокировка и повторная попытка
        account1.setBlocked(false);
        boolean result10 = account1.deposit(500);
        System.out.println("Пополнение разблокированного счета на 500: " + (result10 ? "успешно" : "неуспешно"));
        System.out.println("Итоговый баланс счета 1: " + account1.getBalance());
        System.out.println("Итоговый баланс счета 2: " + account2.getBalance());
    }
}