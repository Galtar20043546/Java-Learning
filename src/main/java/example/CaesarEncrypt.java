package example;

import q8.HashMapGenerics;

import java.util.Objects;
import java.util.Scanner;

public class CaesarEncrypt {
    public static final String ENGLISH_ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    public static final String RUSSIAN_ALPHABET = "абвгдежзийклмнопрстуфхцчшщъыьэюя";

    public static String encrypt(String language,String message, int shift) {
        if (language == null || message == null) {
            throw new IllegalArgumentException("Язык и сообщение не могут быть null(");
        }

        message = message.toLowerCase();
        StringBuilder encrypted = new StringBuilder();
        String alphabet;

        if (Objects.equals(language, "English")){
            alphabet = ENGLISH_ALPHABET;
        } else if (Objects.equals(language,"Russian")){
            alphabet = RUSSIAN_ALPHABET;
        } else {
            throw new IllegalArgumentException("Только English или Russian");
        }

        for (int i = 0; i < message.length(); i++) {
            char currentChar = message.charAt(i);
            int position = alphabet.indexOf(currentChar);

            if (position != -1) {
                int newPosition = (position + shift) % alphabet.length();
                encrypted.append(alphabet.charAt(newPosition));

            } else {
                encrypted.append(currentChar);
            }
        }
        return encrypted.toString();
    }

    public static String decrypt(String language, String message, int shift) {
        return encrypt(language, message, -shift);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Language (English/Russian): ");
        String language = sc.nextLine();

        System.out.print("encrypt/decrypt: ");
        String operation = sc.nextLine();

        System.out.print("Text: ");
        String text = sc.nextLine();

        System.out.print("Shift: ");
        int shift = sc.nextInt();

        String result;
        if (operation.equals("encrypt")) {
            result = encrypt(language, text, shift);
        } else {
            result = decrypt(language, text, shift);
        }

        System.out.println("\nResult: " + result);

        sc.close();
    }
}