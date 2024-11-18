package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Клас, що відповідає за управління групами завдань і їх виконанням.
// Використовує ExecutorService для асинхронного виконання.

public class ComputationManager {
    // Карта для зберігання груп задач за іменем
    private final Map<String, TaskGroup> groups = new HashMap<>();
    // ExecutorService для керування пулом потоків, який виконує задачі
    private final ExecutorService executor = Executors.newCachedThreadPool();

    // Створюємо нову групу задач з лімітом часу
    public void createGroup(String groupName, int timeLimit) {
        groups.put(groupName, new TaskGroup(groupName, timeLimit));
        System.out.println("New group '" + groupName + "' created with limit " + timeLimit + ".");
    }

    // Додаємо задачу в існуючу групу задач
    public void addTask(String taskSymbol, int timeLimit, String groupName) {
        // Перевіряємо, чи існує група з таким ім'ям
        TaskGroup group = groups.get(groupName);
        if (group != null) {
            // Додаємо нову задачу до групи
            group.addTask(new Task(taskSymbol, timeLimit));
            System.out.println("Computation task '" + taskSymbol + "' added to group '" + groupName + "'.");
        } else {
            System.out.println("Group '" + groupName + "' does not exist.");
        }
    }

    // Запускаємо виконання всіх задач у всіх групах
    public void runTasks() {
        System.out.println("Computing...");
        // Для кожної групи виконуємо задачі
        for (TaskGroup group : groups.values()) {
            group.runTasks(executor); // Запускаємо задачі в групі
        }
        System.out.println("All computations finished.");
    }

    // Підсумок виконання всіх задач
    public void summarize() {
        StringBuilder summaryBuilder = new StringBuilder("Summary:\n");
        // Для кожної групи
        for (TaskGroup group : groups.values()) {
            summaryBuilder.append("Group '").append(group.getName()).append("':\n");
            // Для кожної задачі в групі
            for (Task task : group.getTasks()) {
                summaryBuilder.append("- Task '")
                        .append(task.getSymbol()) // Символ задачі
                        .append("': Result: ")
                        .append(task.getResult()) // Результат задачі
                        .append("\n");
            }
        }
        String summary = summaryBuilder.toString();
        // Виводимо підсумкову інформацію
        System.out.println(summary);
    }

    // Метод для коректного завершення роботи та зупинки всіх потоків
    public void shutdown() {
        try {
            // Відправляємо команду на завершення всіх потоків
            executor.shutdownNow();
            while (!executor.isTerminated()) {
                // Чекаємо, поки всі потоки завершать свою роботу
            }
        } catch (Exception e) {
            System.out.println("Error during shutdown: " + e.getMessage());
        }
    }
}
