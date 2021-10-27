package bullscows;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        boolean controlFlag = true;
        String code = generateCode();
        int countTurn = 1;
        while (controlFlag) {
            controlFlag = checkCode(code, countTurn);
            countTurn++;
        }
    }

    public static String generateCode() {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        String code = "";
        String codeCache = "";
        String setOfCharacters = "0123456789abcdefghijklmnopqrstuvwxyz";
        System.out.println("Input the length of the secret code:");
        String slength = scanner.nextLine();
        if (!slength.matches("\\d+")) {
            System.out.printf("Error: \"%s\" isn't a valid number.", slength);
            System.exit(0);
        }
        int length = Integer.parseInt(slength);
        char[] randomStringArr = new char[length];

        if (length > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            System.exit(0);
        } else {
            System.out.println("Input the number of possible symbols in the code:");
            int symbolLength = scanner.nextInt();
            if (length > symbolLength || length == 0) {
                System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.", length, symbolLength);
                System.exit(0);
            }
            if (symbolLength > 36) {
                System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                System.exit(0);
            }
            int minVal = 0;
            int maxVal = symbolLength;

            boolean controlFlag = true;
            while (controlFlag) {
                for (int i = 0; i < length; i++) {
                    int index = random.nextInt(maxVal) + minVal;
                    randomStringArr[i] = setOfCharacters.charAt(index);
                }
                code = new String(randomStringArr);
                controlFlag = !hasAllUniqueChars(code);
            }

            for (int i = 0; i < length; i++) {
                codeCache = codeCache + "*";
            }
            if (symbolLength == 11) {
                System.out.printf("The secret is prepared: %s (0-9, a).%n", codeCache);
            } else if (symbolLength > 10) {
                System.out.printf("The secret is prepared: %s (0-9, %c-%c).%n", codeCache, setOfCharacters.charAt(10), setOfCharacters.charAt(maxVal - 1));
            } else {
                System.out.printf("The secret is prepared: %s (0-%c).%n", codeCache, setOfCharacters.charAt(maxVal - 1));
            }
        }
        System.out.println("Okay, let's start a game!");
        return code;
    }


    public static boolean hasAllUniqueChars (String word) {

        HashSet alphaSet=new HashSet();

        for(int index=0;index < word.length(); index ++)   {

            char c =word.charAt(index);

            // If Hashset's add method return false,that means it is already present in HashSet
            if(!alphaSet.add(c))
                return false;
        }

        return true;
    }

    public static boolean checkCode(String code, int countTurn) {
        boolean controlFlag = true;
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Turn %d:%n", countTurn);
        String answer = scanner.next();
        int cows = 0;
        int bulls = 0;
        char[] answerArray = answer.toCharArray();
        char[] codeArray = code.toCharArray();
        for (int i = 0; i < code.length(); i++) {
            if (answerArray[i] == codeArray[i]) {
                bulls++;
            } else {
                for (int j = 0; j < answerArray.length; j++) {
                    if (i != j && answerArray[i] == codeArray[j]) {
                        cows++;
                    }
                }
            }
        }
        if (bulls == answerArray.length) {
            System.out.printf("Grade: %d bulls%n", bulls);
            System.out.println("Congratulations! You guessed the secret code.");
            controlFlag = false;
        } else if (cows == 0 && bulls == 0) {
            System.out.println("None");
        } else if (bulls == 0) {
            if (cows == 1) {
                System.out.println("Grade: 1 cow");
            } else {
                System.out.printf("Grade: %d cows%n", cows);
            }
        } else if (cows == 0) {
            if (bulls == 1) {
                System.out.println("Grade: 1 bull");
            } else {
                System.out.printf("Grade: %d bulls%n", bulls);
            }
        } else {
            if (bulls == 1 && cows == 1) {
                System.out.println("Grade: 1 bull and 1 cow");
            } else if (bulls == 1) {
                System.out.printf("Grade: %d bull and %d cows%n", bulls, cows);
            } else if (cows == 1) {
                System.out.printf("Grade: %d bulls and %d cow%n", bulls, cows);
            } else {
                System.out.printf("Grade: %d bulls and %d cows%n", bulls, cows);
            }
        }
        return controlFlag;
    }
}
