package pregunta2;

import java.util.Scanner;
import java.math.BigInteger;

/**
 * autor: rolando eynar pari cahuna
 * pregunta 2 examen final
 *  */
public class Criptografia {
    private static final int P = 61;
    private static final int Q = 53;
    private static final int E = 17;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingresar string");
        String input = scanner.nextLine();

        String upperCase = input.toUpperCase();
        System.out.println("String is: " + upperCase);

        int[] asciiCodes = convertToAscii(upperCase);
        printAsciiCodes(asciiCodes);

        System.out.println("MENSAJE ORIGEN");
        printAsciiCodesInLine(asciiCodes);

        BigInteger[] encryptedMessage = encryptRSA(asciiCodes);
        System.out.println("\nMENSAJE ENCRIPTADO");
        printBigIntegerArray(encryptedMessage);

        int[] decryptedMessage = decryptRSA(encryptedMessage);
        System.out.println("\nMENSAJE DESENCRIPTADO");
        printAsciiCodesInLine(decryptedMessage);
    }

    private static int[] convertToAscii(String str) {
        int[] asciiCodes = new int[str.length()];
        for (int i = 0; i < str.length(); i++) {
            asciiCodes[i] = (int) str.charAt(i);
        }
        return asciiCodes;
    }

    private static void printAsciiCodes(int[] asciiCodes) {
        for (int code : asciiCodes) {
            System.out.println(code);
        }
    }

    private static void printAsciiCodesInLine(int[] asciiCodes) {
        for (int code : asciiCodes) {
            System.out.print(code + " ");
        }
        System.out.println();
    }

    private static BigInteger[] encryptRSA(int[] asciiCodes) {
        BigInteger n = BigInteger.valueOf(P * Q);
        BigInteger e = BigInteger.valueOf(E);

        BigInteger[] encrypted = new BigInteger[asciiCodes.length];
        for (int i = 0; i < asciiCodes.length; i++) {
            BigInteger m = BigInteger.valueOf(asciiCodes[i]);
            encrypted[i] = m.modPow(e, n);
        }
        return encrypted;
    }

    private static int[] decryptRSA(BigInteger[] encryptedMessage) {
        BigInteger n = BigInteger.valueOf(P * Q);
        BigInteger phi = BigInteger.valueOf((P - 1) * (Q - 1));
        BigInteger e = BigInteger.valueOf(E);
        BigInteger d = e.modInverse(phi);

        int[] decrypted = new int[encryptedMessage.length];
        for (int i = 0; i < encryptedMessage.length; i++) {
            BigInteger c = encryptedMessage[i];
            BigInteger m = c.modPow(d, n);
            decrypted[i] = m.intValue();
        }
        return decrypted;
    }

    private static void printBigIntegerArray(BigInteger[] array) {
        for (BigInteger num : array) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}