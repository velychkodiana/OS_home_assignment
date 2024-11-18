package org.example;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import sun.misc.Signal;
import sun.misc.SignalHandler;

public class Main {
    public static void main(String[] args) {
        // Створення об'єкта ComputationManager для управління задачами
        ComputationManager manager = new ComputationManager();
        // Створення сканера для вводу користувача з консолі
        Scanner scanner = new Scanner(System.in);
        // Використовуємо AtomicBoolean для безпечної роботи з флагом в багатопотоковому середовищі
        AtomicBoolean running = new AtomicBoolean(true); // Флаг для зупинки програми

        // Створюємо SignalHandler для обробки переривань (наприклад, при натисканні Ctrl+C)
        SignalHandler signalHandler = signal -> {
            System.out.println("\nProgram interrupted by user. Exiting gracefully.");
            manager.shutdown(); // Остановка всіх задач
            running.set(false); // Оновлення флагу для завершення циклу
        };

        // Реєстрація обробника для сигналу SIGINT (Ctrl+C)
        Signal.handle(new Signal("INT"), signalHandler);

        // Виведення привітального повідомлення і доступних команд
        System.out.println("The Computation Manager.");
        System.out.println("Available commands:");
        System.out.println(" group <groupName> <limit>,");
        System.out.println(" new <taskSymbol> <timeLimit> <groupName>,");
        System.out.println(" run, summary, exit.");

        // Цикл програми, що працює до тих пір, поки не буде отримано сигнал на зупинку
        while (running.get()) { // Перевірка флагу для роботи циклу
            try {
                // Виведення запиту для вводу команди
                System.out.print("> ");
                // Перевірка, чи є доступні рядки для вводу
                if (!scanner.hasNextLine()) break; // Якщо ввід більше не доступний - вихід з циклу

                // Читання вводу від користувача
                String input = scanner.nextLine();
                // Розбиття вводу на окремі частини (команду та аргументи)
                String[] command = input.split("\\s+");

                // Якщо команда пуста, пропускаємо ітерацію
                if (command.length == 0) continue;

                // Обробка введеної команди за допомогою switch
                switch (command[0].toLowerCase()) {
                    case "group" -> {
                        // Створення нової групи з обмеженням
                        if (command.length == 3) {
                            String groupName = command[1];
                            int limit = Integer.parseInt(command[2]);
                            manager.createGroup(groupName, limit); // Виклик методу для створення групи
                        } else {
                            // Якщо кількість аргументів неправильна, вивести інструкцію
                            System.out.println("Usage: group <groupName> <limit>");
                        }
                    }
                    case "new" -> {
                        // Створення нової задачі
                        if (command.length == 4) {
                            String taskSymbol = command[1];
                            int timeLimit = Integer.parseInt(command[2]);
                            String groupName = command[3];
                            manager.addTask(taskSymbol, timeLimit, groupName); // Виклик методу для додавання задачі
                        } else {
                            // Інструкція для створення задачі
                            System.out.println("Usage: new <taskSymbol> <timeLimit> <groupName>");
                        }
                    }
                    case "run" -> manager.runTasks(); // Запуск всіх задач
                    case "summary" -> manager.summarize(); // Підсумок виконаних задач
                    case "exit" -> {
                        // Завершення роботи програми
                        manager.shutdown(); // Остановка всіх задач
                        System.out.println("Exiting.");
                        running.set(false); // Зупинка циклу
                    }
                    default -> System.out.println("Unknown command."); // Якщо команда невідома
                }
            } catch (Exception e) {
                // Обробка можливих помилок під час виконання
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
