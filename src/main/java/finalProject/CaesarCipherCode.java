package finalProject;

import q8.HashMapGenerics;

public class CaesarCipherCode {
    public static final String ENGLISH_ALPHABET = "abcdefghijklmnopqrstuvwxyz";


    public static String encrypt(String message, int shift) {

        message = message.toLowerCase();
        StringBuilder encrypted = new StringBuilder();

        HashMapGenerics<Character, Integer> charPosition = new HashMapGenerics<>();
        for (int i = 0; i < ENGLISH_ALPHABET.length(); i++) {
            charPosition.put(ENGLISH_ALPHABET.charAt(i), i);
        }

        for (int i = 0; i < message.length(); i++) {
            char currentChar = message.charAt(i);

            Integer position = charPosition.get(currentChar);

            if (position != null) {
                int newPosition = (position + shift) % ENGLISH_ALPHABET.length();
                encrypted.append(ENGLISH_ALPHABET.charAt(newPosition));
            } else {
                encrypted.append(currentChar);
            }
        }
        return encrypted.toString();
    }

    public static String decrypt(String message, int shift) {
        return encrypt(message, -shift);
    }
}