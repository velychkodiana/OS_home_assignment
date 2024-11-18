# OS_home_assignment
## Варіант : блокуючі запити, Future, перенаправлений ввід/вивід, Java

**Виконання умов** : 
1. **Блокуючі запити** -
    dикористовується метод get() на об'єкті Future, щоб заблокувати потік до отримання результату виконаного завдання.
   Блокує виконання потоку до тих пір, поки завдання не буде виконано та не буде повернуто значення.
   
    **у проекті** :
   
   String result = task.getFutureResult().get(); // Блокуючий запит на отримання результату

3. **Future** -
    у методі runTasks(), кожне завдання виконується асинхронно за допомогою FutureTask, а результат виконання можна
   отримати пізніше за допомогою методу get() (блокуючий запит).
   Дозволяє виконувати завдання паралельно і отримати результат у майбутньому, блокуючи потік при потребі.
   
   **у проекті**:
   
   FutureTask<String> futureTask = new FutureTask<>(task);
executor.submit(futureTask); // Завдання виконується в пулі потоків
task.setFutureResult(futureTask); // Збереження майбутнього результату

5. **Перенаправлення вводу/виводу** -
    перенаправлення виводу в файл використовується PrintStream .
   Дозволяє перенаправляти стандартний вивід у файл і читати вхідні дані з файлу.

   **у проекті**:
   
   PrintStream fileOut = new PrintStream(new FileOutputStream(filePath, true));
fileOut.println("Summary:");

**UI** : 
group <groupName> <limit> — створити групу.
new <taskSymbol> <timeLimit> <groupName> — додати завдання до групи.
run — запустити всі обчислення.
Cntrl+C (cancel <groupName>) - скасування 
summary — вивести результати обчислень.
exit — завершити програму.

**ПРИКЛАД ВИКОНАННЯ ** : 
> group group5 4000
New group 'group5' created with limit 4000.
> new factorial 2000 group5
Computation task 'factorial' added to group 'group5'.
> run
Computing...
Task 'factorial' finished with Result: 3628800
All computations finished.

> group group3 2000
New group 'group3' created with limit 2000.
> new calcPi 500 group3
Computation task 'calcPi' added to group 'group3'.
> run
Computing...
Task 'calcPi' finished with Result: 3.1415916535897743
Task 'factorial' finished with Result: 3628800
All computations finished.

> summary
Summary:
Group 'group3':
- Task 'calcPi': Result: 3.1415916535897743
Group 'group5':
- Task 'factorial': Result: 3628800

**ПРИКЛАД З ПЕРЕРИВАННЯМ** : 
> group group5 4000
New group 'group5' created with limit 4000.
> new factorial 2000 group5
Computation task 'factorial' added to group 'group5'.
> run
Computing...
**Cntrl C**
Program interrupted by user. Exiting gracefully.
All computations finished.
Exiting. 
