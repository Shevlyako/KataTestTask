import org.jetbrains.annotations.NotNull;
import java.util.*;
import java.util.stream.Collectors;
class Calculator {
    public static void main(String[] args) throws calculator.ExceptionForCalculator {
        Scanner in = new Scanner(System.in);    //Создание объекта сканер с помощью передачи в его конструкцию объекта System.in
        System.out.print("Введите пример: ");   //Вывод приглашения для ввода примера который необходимо посчитать
        String example = in.nextLine(); // Считывание введенного примера в переменную example
        char[] array = example.toCharArray();   //делим строку на символы
        boolean bool = CheckForInvalidCharacters(array); // Проводим проверку что у нас присутствуют только разрешенные
        //и того что у нас используются числа только одной системы счисления возвращаем тру если считаем римские цифры
        char operator = FindOperator(example);  //Ищем в строке которую ввел человек оператор
        String [] number = example.split("[+\\-*/]");;   //Делим строку на числа по любому оператору оператору
        if (bool==true){    //считаем в зависимости от того какая система счисления разными способами
            ResultRoman(number, operator);
        } else {ResultArabian(number, operator);}

        }
    static boolean CheckForInvalidCharacters(char[] array) throws calculator.ExceptionForCalculator {
        List Roman = new ArrayList();//создаем лист с римскими цифрами
        Roman.add('I');
        Roman.add('V');
        Roman.add('X');
        List Number = new ArrayList();//создаем лист с арабскими цифрами
        Number.add('0');
        Number.add('1');
        Number.add('2');
        Number.add('3');
        Number.add('4');
        Number.add('5');
        Number.add('6');
        Number.add('7');
        Number.add('8');
        Number.add('9');
        List Operator = new ArrayList();//создаем лист со знаками операций
        Operator.add('+');
        Operator.add('-');
        Operator.add('*');
        Operator.add('/');
        int quantityRoman = 0, quantityArabian = 0, quantityOperator = 0;//создаем переменные для счета римских и арабских цифр
        boolean bool;//создаем булевую переменную для определения римской либо арабской системы счисления
        for (int i = 0; i < array.length; i++) {//проходимся по чаровому массиву
            if (!Roman.contains(array[i]) && !Operator.contains(array[i]) && !Number.contains(array[i])) {
                throw new calculator.ExceptionForCalculator("Строка содержит недопустимые символы");//проверка на недопустимые символы
            }
            if (Roman.contains(array[i])) {//считаем колличество символов относящихся к римским цифрам
                ++quantityRoman;
            }
            if (Number.contains(array[i])) {//считаем колличество символов относящихся к арабским цифрам
                ++quantityArabian;
            }
            if (Operator.contains(array[i])) {//считаем колличество операторов
                ++quantityOperator;
            }
        }
        if (quantityRoman != 0 && quantityArabian != 0) { //проверяем что одновременно нет арабских и римских чисел
            throw new calculator.ExceptionForCalculator("Одновременно используются разные системы счисления");
        }
        if (quantityOperator > 1){
            throw new calculator.ExceptionForCalculator("В примере больше одного оператора");
        }
        if (quantityRoman != 0) {// проверка на то какая система счисления используется
            bool = true;
        } else {
            bool = false;
        }
        return bool;
    }
    static char FindOperator(String string) throws calculator.ExceptionForCalculator {//проверка того какое действие задал нам пользователь
        char space = ' ';
        if (string.contains("+")){
            space = '+';
        }
        if (string.contains("-")){
            space = '-';
        }
        if (string.contains("/")){
            space = '/';
        }
        if (string.contains("*")){
            space = '*';
        }
        return space;
    }
    public static String ArabicToRoman(int number) {    //преобразование арабских в римские
        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;
        StringBuilder sb = new StringBuilder();

        while ((number > 0) && (i < romanNumerals.size())) {
            RomanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }

        return sb.toString();
    }
    static int RomanToArabic(String input) {    // преобразование римских в арабские
        String romanNumeral = input.toUpperCase();  //Переводим римские цифры в верхний регистр
        int result = 0;

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;

        while ((romanNumeral.length() > 0) && (i < romanNumerals.size())) {
            RomanNumeral symbol = romanNumerals.get(i);
            if (romanNumeral.startsWith(symbol.name())) {
                result += symbol.getValue();
                romanNumeral = romanNumeral.substring(symbol.name().length());
            } else {
                i++;
            }
        }

        return result;
    }
    static void ResultRoman(String[] number, char operator) throws calculator.ExceptionForCalculator {
        int number1 = RomanToArabic(number[0]), number2 = RomanToArabic(number[1]);    // преобразуем из масива строк в числа типа инт
        if(number1 > 10 | number2 > 10){    //проверка числа на то что оно не больше 10
            throw new calculator.ExceptionForCalculator("Числа не могут быть больше 10");
        }
        int result = 0;//вычисление в арабской системе счисления
        switch (operator){
            case '+': result = number1 + number2;
                break;
            case '-': result = number1 - number2;
                break;
            case '*': result = number1 * number2;
                break;
            case '/': result = number1 / number2;
                break;
        }
        if(result < 1){
            throw new calculator.ExceptionForCalculator("Ответ при операциях с римскими числами не может быть < 1");
        }

        System.out.println(ArabicToRoman(result));  //вывод ответа с предварительным обратным преобразованием в римские цифры
    }
    static void ResultArabian(String[] arrayString, char operator)throws calculator.ExceptionForCalculator {
        int number1 = Integer.parseInt(arrayString[0]), number2 = Integer.parseInt(arrayString[1]); //преобразование строкового массива в числа
        if(number1 > 10 | number2 > 10){    //проверка чтобы числа не были больше 10
            throw new calculator.ExceptionForCalculator("Числа не могут быть больше 10");
        }
        int result = 0;
        switch (operator){
            case '+': result = number1 + number2;
                break;
            case '-': result = number1 - number2;
                break;
            case '*': result = number1 * number2;
                break;
            case '/': result = number1 / number2;
                break;
        }
        System.out.println(result);
    }
}