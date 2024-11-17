package org.example;

import java.util.ArrayList;
import java.util.List;

// Клас, що представляє групу завдань із загальним обмеженням часу.

public class TaskGroup {
    private final String name; // Назва групи
    private final int limit; // Загальне обмеження часу для групи
    private final List<Task> tasks; // Список завдань у групі

    // Конструктор, що створює нову групу
    public TaskGroup(String name, int limit) {
        this.name = name;
        this.limit = limit;
        this.tasks = new ArrayList<>(); // Ініціалізація списку завдань
    }

    public String getName() {
        return name; // Повертає назву групи
    }

    public int getLimit() {
        return limit; // Повертає обмеження часу для групи
    }

    public List<Task> getTasks() {
        return tasks; // Повертає список завдань групи
    }

    // Додає нове завдання до групи. @param task Завдання для додавання.

    public void addTask(Task task) {
        tasks.add(task);
    }
}
