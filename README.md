# OS_home_assignment
## Варіант :
**блокуючі запити, Future, перенаправлений ввід/вивід, Java**

**Виконання умов** : 
1. **Блокуючі запити** -
    dикористовується метод get() на об'єкті Future, щоб заблокувати потік до отримання результату виконаного завдання.
   Блокує виконання потоку до тих пір, поки завдання не буде виконано та не буде повернуто значення.
    **у проекті** : 
   String result = task.getFutureResult().get(); // Блокуючий запит на отримання результату

2. **Future** -
    у методі runTasks(), кожне завдання виконується асинхронно за допомогою FutureTask, а результат виконання можна
   отримати пізніше за допомогою методу get() (блокуючий запит).
   Дозволяє виконувати завдання паралельно і отримати результат у майбутньому, блокуючи потік при потребі.
   **у проекті**: 
   FutureTask<String> futureTask = new FutureTask<>(task);
executor.submit(futureTask); // Завдання виконується в пулі потоків
task.setFutureResult(futureTask); // Збереження майбутнього результату

3. **Перенаправлення вводу/виводу** -
    перенаправлення виводу в файл використовується PrintStream .
   Дозволяє перенаправляти стандартний вивід у файл і читати вхідні дані з файлу.
   **у проекті**:
   PrintStream fileOut = new PrintStream(new FileOutputStream(filePath, true));
fileOut.println("Summary:");

**UI** : 
group <groupName> <limit> — створити групу.
new <taskSymbol> <timeLimit> <groupName> — додати завдання до групи.
run — запустити всі обчислення.
summary — вивести результати обчислень.
exit — завершити програму.

**Приклад виконання** : 
>group Group1 1000
>new TaskA 500 Group1
>new TaskB 700 Group1
>run
Computing..
>summary
Task 'TaskA' completed with result: Result for TaskA: 5
Task 'TaskB' completed with result: Result for TaskB: 7
