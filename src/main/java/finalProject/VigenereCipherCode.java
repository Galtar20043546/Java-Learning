package finalProject;

import q8.HashMapGenerics;

public class VigenereCipherCode {
    public static final String ENGLISH_ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    public static String encrypt(String openText, String keyWord) {
        if (openText == null || keyWord == null) {
            throw new IllegalArgumentException("Откытый текст и ключевое слово не могут быть null");
        }

        openText = openText.replaceAll(" ", "").toLowerCase();
        keyWord = keyWord.toLowerCase();
        StringBuilder encrypted = new StringBuilder();

        HashMapGenerics<Character, Integer> charPosition = new HashMapGenerics<>();
        for (int i = 0; i < ENGLISH_ALPHABET.length(); i++) {
            charPosition.put(ENGLISH_ALPHABET.charAt(i), i);
        }

        for (int i = 0; i < openText.length(); i++) {
            char currentChar = openText.charAt(i);
            char currentCharKey = keyWord.charAt(i % keyWord.length());

            Integer positionText = charPosition.get(currentChar);
            Integer positionKey = charPosition.get(currentCharKey);

            if (positionKey != null && positionText != null) {
                int newPosition = (positionKey + positionText) % ENGLISH_ALPHABET.length();
                encrypted.append(ENGLISH_ALPHABET.charAt(newPosition));
            } else {
                encrypted.append(currentChar);
            }
        }
        return encrypted.toString();
    }
}
