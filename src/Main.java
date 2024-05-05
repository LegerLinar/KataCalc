import Exceptions.IllegalInputException;
import Exceptions.WrongNumException;
import Exceptions.WrongOperatorException;
import Exceptions.WrongRomanInteger;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("Input your calculation:");
            String input = sc.nextLine();

            String result;
            // Пустая строка перезапускает запрос
            if (input.isBlank()) {
                continue;
            }

            String[] splitInp = splitter(input);
            boolean romanTumbler = isRomanBoth(splitInp[0], splitInp[2]);

            if (romanTumbler) {
                int numA = romanToInt(splitInp[0]);
                int numB = romanToInt(splitInp[2]);
                int tempRomaResult = operation(numA, numB, splitInp[1]);
                result = fromIntToRoman(tempRomaResult);

            } else {
                int numA = toParseInt(splitInp[0]);
                int numB = toParseInt(splitInp[2]);
                int tempArabicResult = operation(numA, numB, splitInp[1]);
                result = String.valueOf(tempArabicResult);
            }

            System.out.println(result);
            System.out.print("To continue press Enter, to stop enter 'stop'");
        }
        while (!sc.nextLine().equals("stop"));


    }

    public static String[] splitter(String input) {
        String[] inputSep = input.split(" ");
        if (inputSep.length != 3) {
            throw new IllegalInputException("Operation shall be inputted thru spaces");
        }
        return inputSep;
    }

    public static int toParseInt(String input) {
        /** Парс = проверка на целочисленный Арабо-Римский ввод и проверка на попадание в диапазон от 0 до 10 **/
        try {
            int parsed = Integer.parseInt(input);
            if (parsed <= 0 || parsed > 10) {
                throw new WrongNumException("Input must be between 0 and 10");
            }
            return parsed;
        } catch (NumberFormatException e) {
            throw new WrongNumException("Input must include only Arab integers or only Roma integers");
        }
    }

    public static int operation(int a, int b, String toParse) {
        char operator = toParse.charAt(0);
        return switch (operator) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> a / b;
            default -> throw new WrongOperatorException("Unexpected operator: " + operator);
        };
    }


    public static boolean isRoman(String subString) {
        /** Верификация римских цифр **/
        final char[] ROME_DIGITS = {'I', 'V', 'X'};


        // Цикл проверки на "римскость" цифр - здесь нужен метод
        boolean isRoman = false;
        char[] inputArray = subString.toCharArray();
        for (char inChar : inputArray) {
            for (char romanChar : ROME_DIGITS) {
                if (inChar == romanChar) {
                    isRoman = true;
                    break;
                }
            }
        }
        return isRoman;
    }

    public static boolean isRomanBoth(String subStringA, String subStringB) {
        if (isRoman(subStringA) && isRoman(subStringB)) {
            return true;
        }
        if (isRoman(subStringA) || isRoman(subStringB)) {
            throw new WrongNumException("Input must be both roman numbers");
        } else {
            return false;
        }
    }

    public static int romanToInt(String romanNum) {
        /** Приведение римских цифр к арабским **/
        char[] inputRoma = romanNum.toCharArray();
        int romeTotal = 0;
        for (char romanChar : inputRoma) {
            switch (romanChar) {
                case 'I':
                    romeTotal += 1;
                    break;
                case 'V':
                    romeTotal += 5;
                    break;
                case 'X':
                    romeTotal += 10;
                    break;
            }
        }

        for (int i = inputRoma.length - 1; i > 0; i--) {
            if (inputRoma[i - 1] == 'I' && (inputRoma[i] == 'V' || inputRoma[i] == 'X')) {
                romeTotal -= 2;
            }
        }
        if (romeTotal < 0 || romeTotal > 10) {
            throw new WrongNumException("The input shall be between 0 and 10");
        }
        return romeTotal;
    }

    public static String fromIntToRoman(int intNum) {
        if (intNum < 0 || intNum >= 400) {
            throw new WrongRomanInteger("Result must be between 0 and 400");
        }
        String finalRomaNum = "";
        int c = intNum / 100;
        finalRomaNum += lettersCreator(c, 'C');
        int l = (intNum % 100) / 50;
        finalRomaNum += lettersCreator(l, 'L');
        int x = ((intNum % 100) % 50) / 10;
        /** Отсечка для 9 * 10 **/
        if (finalRomaNum.endsWith("L") && lettersCreator(x, 'X').length() > 1 && lettersCreator(x, 'X').charAt(1) == 'L') {
            String tempRomanNum = finalRomaNum.substring(0, finalRomaNum.length() - 1);
            tempRomanNum += "XC";
            finalRomaNum = "";
            finalRomaNum += tempRomanNum;
        } else {
            finalRomaNum += lettersCreator(x, 'X');
        }

        int v = ((intNum % 100) % 10) / 5;
        finalRomaNum += lettersCreator(v, 'V');
        int i = (((intNum % 100) % 10) % 5);
        /** Отсечка для 9 **/
        if (finalRomaNum.endsWith("V") && lettersCreator(i, 'I').length() > 1 && lettersCreator(i, 'I').charAt(1) == 'V') {
            String tempRomanNum = finalRomaNum.substring(0, finalRomaNum.length() - 1);
            tempRomanNum += "IX";
            finalRomaNum = "";
            finalRomaNum += tempRomanNum;
        } else {
            finalRomaNum += lettersCreator(i, 'I');
        }

        return finalRomaNum;
    }

    public static String lettersCreator(int charsQuantity, char romaChar) {
        String romaChars = "CLXVI";
        String romaString = "";
        if (charsQuantity == 4) {
            int charIndex = romaChars.indexOf(romaChar);
            romaString += romaChar;
            romaString += romaChars.charAt(charIndex - 1);
        } else {
            while (charsQuantity > 0) {
                romaString += romaChar;
                charsQuantity--;

            }
        }
        return romaString;
    }
}