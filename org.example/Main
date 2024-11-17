package org.example;

import java.util.Scanner;

//  Обробляє введення команд від користувача та передає їх до ComputationManager.

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Сканер для отримання команд користувача
        ComputationManager manager = new ComputationManager(); // Менеджер для обчислень

        System.out.println("The Computation Manager.");
        System.out.println("Available commands: \n group <groupName> <limit>, \n new <taskSymbol> <timeLimit> <groupName>, \n run, summary, exit.");

        // Головний цикл для обробки команд
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine(); // Зчитування команди користувача

            if (input.equalsIgnoreCase("exit")) { // Перевірка на завершення програми
                System.out.println("Exiting the program...");
                break;
            }

            // Виконання команди через менеджер
            manager.executeCommand(input);
        }
    }
}
