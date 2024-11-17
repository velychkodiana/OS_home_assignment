package org.example;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.*;

// Клас, що відповідає за управління групами завдань і їх виконанням.
// Використовує ExecutorService для асинхронного виконання.

public class ComputationManager {
    private final Map<String, TaskGroup> groups = new HashMap<>(); // Зберігає всі групи завдань
    private final ExecutorService executor = Executors.newCachedThreadPool(); // Пул потоків для обчислень

    // Виконує команду користувача. @param command Введена команда.
    public void executeCommand(String command) {
        String[] parts = command.split(" "); // Розділяє команду на частини
        try {
            switch (parts[0].toLowerCase()) {
                case "group" -> createGroup(parts[1], Integer.parseInt(parts[2]));
                case "new" -> addTask(parts[1], Integer.parseInt(parts[2]), parts[3]);
                case "run" -> runTasks();
                case "summary" -> summary();
                default -> System.out.println("Unknown command.");
            }
        } catch (Exception e) {
            System.err.println("Error executing command: " + e.getMessage());
        }
    }

    // Створює нову групу завдань. @param groupName Назва групи.
    // @param limit Обмеження часу для групи (мс).

    private void createGroup(String groupName, int limit) {
        groups.put(groupName, new TaskGroup(groupName, limit));
        System.out.printf("New group '%s' created with limit: %d ms%n", groupName, limit);
    }

    // Додає нове завдання до групи. @param symbol Символ завдання.
    // @param timeLimit Обмеження часу для завдання (мс). @param groupName Назва групи.

    private void addTask(String symbol, int timeLimit, String groupName) {
        TaskGroup group = groups.get(groupName);
        if (group == null) {
            System.out.println("Group not found: " + groupName);
            return;
        }

        Task task = new Task(symbol, timeLimit); // Створюємо нове завдання
        group.addTask(task); // Додаємо завдання до групи
        System.out.printf("Task '%s' with limit %d ms added to group '%s'.%n", symbol, timeLimit, groupName);
    }

    // Запускає всі завдання у всіх групах.
    private void runTasks() {
        System.out.println("Computing..");
        for (TaskGroup group : groups.values()) {
            for (Task task : group.getTasks()) {
                FutureTask<String> futureTask = new FutureTask<>(task);
                executor.submit(futureTask); // Виконання завдання у пулі потоків
                task.setFutureResult(futureTask); // Збереження Future для завдання
            }
        }
    }

    // Виводить підсумок усіх виконаних завдань. Також зберігає підсумок у файл `output.txt`.
    public void summary() {
        String filePath = "output.txt";

        try (PrintStream fileOut = new PrintStream(new FileOutputStream(filePath, true))) {
            System.out.println("Summary:");
            fileOut.println("Summary:");

            for (TaskGroup group : groups.values()) {
                for (Task task : group.getTasks()) {
                    try {
                        String result = task.getFutureResult().get(); // Блокуючий запит на отримання результату
                        System.out.printf("Task '%s' completed with result: %s%n", task.getSymbol(), result);
                        fileOut.printf("Task '%s' completed with result: %s%n", task.getSymbol(), result);
                    } catch (InterruptedException | ExecutionException e) {
                        System.err.println("Error in task: " + task.getSymbol());
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error writing summary to file: " + e.getMessage());
        }
    }
}
