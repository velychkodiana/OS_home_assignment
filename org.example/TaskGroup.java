package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TaskGroup {
    // Ім'я групи задач
    private final String name;
    // Ліміт часу для виконання всіх задач у групі
    private final int timeLimit;
    // Список задач, що входять в цю групу
    private final List<Task> tasks = new ArrayList<>();

    // Конструктор для ініціалізації групи задач
    public TaskGroup(String name, int timeLimit) {
        this.name = name;
        this.timeLimit = timeLimit;
    }

    // Геттер для отримання імені групи
    public String getName() {
        return name;
    }

    // Геттер для отримання списку задач в групі
    public List<Task> getTasks() {
        return tasks;
    }

    // Додаємо нову задачу в групу
    public void addTask(Task task) {
        tasks.add(task);
    }

    // Метод для виконання всіх задач групи за допомогою ExecutorService
    public void runTasks(ExecutorService executor) {
        for (Task task : tasks) {
            // Завдання виконується асинхронно в пулі потоків
            Future<String> future = executor.submit(task);
            try {
                // Очікуємо результат виконання задачі з лімітом часу
                task.setResult(future.get(task.getTimeLimit(), TimeUnit.MILLISECONDS));
                System.out.println("Task '" + task.getSymbol() + "' finished with Result: " + task.getResult());
            } catch (TimeoutException e) {
                // Якщо задача не завершилась вчасно, скасовуємо її та встановлюємо результат як "Task timed out"
                future.cancel(true);
                task.setResult("Task timed out");
                System.out.println("Task '" + task.getSymbol() + "' timed out.");
            } catch (Exception e) {
                // Якщо сталася помилка під час виконання задачі
                task.setResult("Error: " + e.getMessage());
                System.out.println("Task '" + task.getSymbol() + "' failed with error.");
            }
        }
    }
}
