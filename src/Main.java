import org.jetbrains.annotations.NotNull;
import java.util.*;
import java.util.stream.Collectors;
class Calculator {
    public static void main(String[] args) throws calculator.ExceptionForCalculator {
        Scanner in = new Scanner(System.in);    //�������� ������� ������ � ������� �������� � ��� ����������� ������� System.in
        System.out.print("������� ������: ");   //����� ����������� ��� ����� ������� ������� ���������� ���������
        String example = in.nextLine(); // ���������� ���������� ������� � ���������� example
        char[] array = example.toCharArray();   //����� ������ �� �������
        boolean bool = CheckForInvalidCharacters(array); // �������� �������� ��� � ��� ������������ ������ �����������
        //� ���� ��� � ��� ������������ ����� ������ ����� ������� ��������� ���������� ��� ���� ������� ������� �����
        char operator = FindOperator(example);  //���� � ������ ������� ���� ������� ��������
        String [] number = example.split("[+\\-*/]");;   //����� ������ �� ����� �� ������ ��������� ���������
        if (bool==true){    //������� � ����������� �� ���� ����� ������� ��������� ������� ���������
            ResultRoman(number, operator);
        } else {ResultArabian(number, operator);}

        }
    static boolean CheckForInvalidCharacters(char[] array) throws calculator.ExceptionForCalculator {
        List Roman = new ArrayList();//������� ���� � �������� �������
        Roman.add('I');
        Roman.add('V');
        Roman.add('X');
        List Number = new ArrayList();//������� ���� � ��������� �������
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
        List Operator = new ArrayList();//������� ���� �� ������� ��������
        Operator.add('+');
        Operator.add('-');
        Operator.add('*');
        Operator.add('/');
        int quantityRoman = 0, quantityArabian = 0, quantityOperator = 0;//������� ���������� ��� ����� ������� � �������� ����
        boolean bool;//������� ������� ���������� ��� ����������� ������� ���� �������� ������� ���������
        for (int i = 0; i < array.length; i++) {//���������� �� �������� �������
            if (!Roman.contains(array[i]) && !Operator.contains(array[i]) && !Number.contains(array[i])) {
                throw new calculator.ExceptionForCalculator("������ �������� ������������ �������");//�������� �� ������������ �������
            }
            if (Roman.contains(array[i])) {//������� ����������� �������� ����������� � ������� ������
                ++quantityRoman;
            }
            if (Number.contains(array[i])) {//������� ����������� �������� ����������� � �������� ������
                ++quantityArabian;
            }
            if (Operator.contains(array[i])) {//������� ����������� ����������
                ++quantityOperator;
            }
        }
        if (quantityRoman != 0 && quantityArabian != 0) { //��������� ��� ������������ ��� �������� � ������� �����
            throw new calculator.ExceptionForCalculator("������������ ������������ ������ ������� ���������");
        }
        if (quantityOperator > 1){
            throw new calculator.ExceptionForCalculator("� ������� ������ ������ ���������");
        }
        if (quantityRoman != 0) {// �������� �� �� ����� ������� ��������� ������������
            bool = true;
        } else {
            bool = false;
        }
        return bool;
    }
    static char FindOperator(String string) throws calculator.ExceptionForCalculator {//�������� ���� ����� �������� ����� ��� ������������
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
    public static String ArabicToRoman(int number) {    //�������������� �������� � �������
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
    static int RomanToArabic(String input) {    // �������������� ������� � ��������
        String romanNumeral = input.toUpperCase();  //��������� ������� ����� � ������� �������
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
        int number1 = RomanToArabic(number[0]), number2 = RomanToArabic(number[1]);    // ����������� �� ������ ����� � ����� ���� ���
        if(number1 > 10 | number2 > 10){    //�������� ����� �� �� ��� ��� �� ������ 10
            throw new calculator.ExceptionForCalculator("����� �� ����� ���� ������ 10");
        }
        int result = 0;//���������� � �������� ������� ���������
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
            throw new calculator.ExceptionForCalculator("����� ��� ��������� � �������� ������� �� ����� ���� < 1");
        }

        System.out.println(ArabicToRoman(result));  //����� ������ � ��������������� �������� ��������������� � ������� �����
    }
    static void ResultArabian(String[] arrayString, char operator)throws calculator.ExceptionForCalculator {
        int number1 = Integer.parseInt(arrayString[0]), number2 = Integer.parseInt(arrayString[1]); //�������������� ���������� ������� � �����
        if(number1 > 10 | number2 > 10){    //�������� ����� ����� �� ���� ������ 10
            throw new calculator.ExceptionForCalculator("����� �� ����� ���� ������ 10");
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