package org.example;

import java.util.concurrent.Callable;

public class Task implements Callable<String> {
    // Символ задачі, наприклад "calcPi" або "factorial"
    private final String symbol;
    // Ліміт часу для виконання задачі 
    private final int timeLimit;
    // Результат задачі, початково встановлений в "Pending"
    private String result = "Pending";

    // Конструктор для ініціалізації задачі з символом та лімітом часу
    public Task(String symbol, int timeLimit) {
        this.symbol = symbol;
        this.timeLimit = timeLimit;
    }

    // Геттер для символу задачі
    public String getSymbol() {
        return symbol;
    }

    // Геттер для ліміту часу задачі
    public int getTimeLimit() {
        return timeLimit;
    }

    // Геттер для результату виконання задачі
    public String getResult() {
        return result;
    }

    // Сеттер для результату задачі
    public void setResult(String result) {
        this.result = result;
    }

    // Метод call() реалізує логіку виконання задачі
    @Override
    public String call() throws Exception {
        // Перевіряємо символ задачі та виконуємо відповідний обчислювальний процес
        if ("calcPi".equals(symbol)) {
            return calculatePi(); // Обчислення числа Пі за допомогою ряду Лейбніца
        } else if ("factorial".equals(symbol)) {
            return calculateFactorial(); // Обчислення факторіала числа 10
        }
        // Якщо символ не розпізнаний, повертаємо повідомлення про невідому задачу
        return "Unknown task";
    }

    // Метод для обчислення числа Пі за допомогою ряду Лейбніца
    private String calculatePi() {
        double pi = 0;
        // Використовуємо ряд Лейбніца для наближення значення Пі
        for (int i = 0; i < 1_000_000; i++) {
            pi += Math.pow(-1, i) / (2 * i + 1); // Формула ряду Лейбніца
        }
        // Повертаємо результат, множачи на 4 для отримання значення Пі
        return String.valueOf(4 * pi);
    }

    // Метод для обчислення факторіала числа 10
    private String calculateFactorial() {
        long result = 1;
        // Обчислюємо факторіал числа 10
        for (int i = 1; i <= 10; i++) {
            result *= i;
        }
        // Повертаємо результат як строку
        return String.valueOf(result);
    }
}
