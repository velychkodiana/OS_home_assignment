package org.example;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

// Клас, що представляє окреме завдання. Завдання виконується асинхронно за допомогою Future.

public class Task implements Callable<String> {
    private final String symbol; // Ідентифікатор завдання
    private final int timeLimit; // Обмеження часу для виконання завдання (у мс)
    private Future<String> futureResult; // Future для асинхронного отримання результату

    // Конструктор, що приймає символ завдання та обмеження часу
    public Task(String symbol, int timeLimit) {
        this.symbol = symbol;
        this.timeLimit = timeLimit;
    }

    public String getSymbol() {
        return symbol; // Повертає ідентифікатор завдання
    }

    public void setFutureResult(Future<String> futureResult) {
        this.futureResult = futureResult; // Встановлює Future для цього завдання
    }

    public Future<String> getFutureResult() {
        return futureResult; // Повертає Future для отримання результату
    }

    // Метод, який виконується під час обчислення завдання.
    // @return Результат обчислення у вигляді рядка.

    @Override
    public String call() throws Exception {
        Thread.sleep(timeLimit); // Емуляція обчислення через паузу
        return "Result for " + symbol + ": " + (timeLimit / 100); // Повернення результату
    }
}
