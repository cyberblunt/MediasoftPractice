import org.example.BankAccount;

public class BankAccountTest {
    public static void main(String[] args) {
        System.out.println("=== Дополнительные тесты BankAccount ===\n");
        
        // Тест 1: Создание счета и проверка начальных значений
        System.out.println("Тест 1: Создание счета");
        BankAccount account = new BankAccount("Анна Смирнова");
        System.out.println("Владелец: " + account.getOwnerName());
        System.out.println("Начальный баланс: " + account.getBalance());
        System.out.println("Заблокирован: " + account.isBlocked());
        System.out.println("Дата открытия: " + account.getOpeningDate());
        System.out.println();
        
        // Тест 2: Пограничные случаи для deposit
        System.out.println("Тест 2: Пограничные случаи для пополнения");
        System.out.println("Пополнение на 0: " + account.deposit(0));
        System.out.println("Пополнение на 1: " + account.deposit(1));
        System.out.println("Баланс: " + account.getBalance());
        System.out.println();
        
        // Тест 3: Пограничные случаи для withdraw
        System.out.println("Тест 3: Пограничные случаи для снятия");
        System.out.println("Снятие 0: " + account.withdraw(0));
        System.out.println("Снятие 1 (точно весь баланс): " + account.withdraw(1));
        System.out.println("Баланс: " + account.getBalance());
        System.out.println("Снятие 1 (больше баланса): " + account.withdraw(1));
        System.out.println();
        
        // Тест 4: Цепочка операций
        System.out.println("Тест 4: Цепочка операций");
        BankAccount acc1 = new BankAccount("Клиент 1");
        BankAccount acc2 = new BankAccount("Клиент 2");
        
        acc1.deposit(1000);
        System.out.println("Счет 1 пополнен на 1000. Баланс: " + acc1.getBalance());
        
        acc1.transfer(acc2, 300);
        System.out.println("Переведено 300 с счета 1 на счет 2");
        System.out.println("Баланс счета 1: " + acc1.getBalance());
        System.out.println("Баланс счета 2: " + acc2.getBalance());
        
        acc2.withdraw(150);
        System.out.println("Снято 150 со счета 2. Баланс: " + acc2.getBalance());
        System.out.println();
        
        // Тест 5: Перевод на заблокированный счет
        System.out.println("Тест 5: Перевод на заблокированный счет");
        acc2.setBlocked(true);
        boolean transferResult = acc1.transfer(acc2, 100);
        System.out.println("Перевод на заблокированный счет: " + transferResult);
        System.out.println("Баланс счета 1: " + acc1.getBalance());
        System.out.println("Баланс счета 2: " + acc2.getBalance());
        
        System.out.println("\n=== Все тесты завершены ===");
    }
} 