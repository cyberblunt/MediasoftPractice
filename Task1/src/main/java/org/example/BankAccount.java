package org.example;

import java.time.LocalDateTime;

public class BankAccount {
    private String ownerName;
    private int balance;
    private LocalDateTime openingDate;
    private boolean isBlocked;

    // Конструктор, принимающий только имя владельца
    public BankAccount(String ownerName) {
        this.ownerName = ownerName;
        this.balance = 0; // начальный баланс
        this.openingDate = LocalDateTime.now(); // текущая дата и время
        this.isBlocked = false; // счет не заблокирован по умолчанию
    }

    // Метод пополнения счета
    public boolean deposit(int amount) {
        // Проверяем, что счет не заблокирован и сумма положительная
        if (isBlocked || amount <= 0) {
            return false;
        }
        
        balance += amount;
        return true;
    }

    // Метод снятия денег
    public boolean withdraw(int amount) {
        // Проверяем, что счет не заблокирован, сумма положительная и достаточно средств
        if (isBlocked || amount <= 0 || amount > balance) {
            return false;
        }
        
        balance -= amount;
        return true;
    }

    // Метод перевода денег на другой счет
    public boolean transfer(BankAccount otherAccount, int amount) {
        // Проверяем, что оба счета не заблокированы, сумма положительная и достаточно средств
        if (isBlocked || otherAccount.isBlocked || amount <= 0 || amount > balance) {
            return false;
        }
        
        // Снимаем с текущего счета
        balance -= amount;
        // Зачисляем на другой счет
        otherAccount.balance += amount;
        return true;
    }

    // Геттеры для просмотра информации о счете
    public String getOwnerName() {
        return ownerName;
    }

    public int getBalance() {
        return balance;
    }

    public LocalDateTime getOpeningDate() {
        return openingDate;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    // Метод для блокировки/разблокировки счета
    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }
} 