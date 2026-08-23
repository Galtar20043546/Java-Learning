package finalProject;

import java.util.Objects;
import java.util.Scanner;

public class Cipher {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("1. Caesar Cipher");
            System.out.println("2. Vigenere Cipher");
            System.out.println("3. Exit");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    caesarMenu(sc);
                    break;
                case 2:
                    vigenereMenu(sc);
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    System.out.println("Error!");
            }
        }

        sc.close();
    }

    public static void caesarMenu(Scanner sc) {
        System.out.println("Enter the text: ");
        String text = sc.nextLine();

        System.out.println("Enter the shift: ");
        int shift = sc.nextInt();
        sc.nextLine();

        System.out.println("Encrypt/Decrypt: ");
        String action = sc.nextLine();
        sc.nextLine();

        String result;
        if (Objects.equals(action,"Encrypt")) {
            result = CaesarCipherCode.encrypt(text,shift);
            System.out.println("Encrypted: " + result);
        } else {
            result = CaesarCipherCode.decrypt(text, shift);
            System.out.println("Decrypted: " + result);
        }
    }

    public static void vigenereMenu(Scanner sc) {
        System.out.println("Enter the text: ");
        String text = sc.nextLine();

        System.out.println("Enter the key word: ");
        String keyWord = sc.nextLine();

        String result = VigenereCipherCode.encrypt(text, keyWord);
        System.out.println("Encrypted: " + result);
    }
}
