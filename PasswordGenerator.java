package github;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;

public class PasswordGenerator {

    private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()_+";

    private boolean includeUppercase;
    private boolean includeLowercase;
    private boolean includeNumbers;
    private boolean includeSymbols;

    public PasswordGenerator() {
        includeUppercase = false;
        includeLowercase = false;
        includeNumbers = false;
        includeSymbols = false;
    }

    public void displayOptions() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select password criteria (Enter the numbers separated by commas):");
        System.out.println("1. Include uppercase letters");
        System.out.println("2. Include lowercase letters");
        System.out.println("3. Include numbers");
        System.out.println("4. Include symbols");

        String input = scanner.nextLine();
        String[] choices = input.split(",");

        for (String choice : choices) {
            switch (choice.trim()) {
                case "1":
                    includeUppercase = true;
                    break;
                case "2":
                    includeLowercase = true;
                    break;
                case "3":
                    includeNumbers = true;
                    break;
                case "4":
                    includeSymbols = true;
                    break;
                default:
                    System.out.println("Invalid choice: " + choice);
            }
        }
    }

    public String generatePassword(int length) {
        StringBuilder password = new StringBuilder(length);
        String charPool = "";

        if (includeUppercase) {
            charPool += UPPERCASE_LETTERS;
        }
        if (includeLowercase) {
            charPool += LOWERCASE_LETTERS;
        }
        if (includeNumbers) {
            charPool += NUMBERS;
        }
        if (includeSymbols) {
            charPool += SYMBOLS;
        }

        if (charPool.isEmpty()) {
            throw new IllegalStateException("No character types selected");
        }

        Random random = new Random();

        for (int i = 0; i < length; i++) {
            password.append(charPool.charAt(random.nextInt(charPool.length())));
        }

        return password.toString();
    }

    public void copyToClipboard(String password) {
        StringSelection selection = new StringSelection(password);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
        System.out.println("Password copied to clipboard.");
    }

    public static void main(String[] args) {
        PasswordGenerator generator = new PasswordGenerator();
        generator.displayOptions();

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.print("Enter the desired password length: ");
            int length = scanner.nextInt();

            String password = generator.generatePassword(length);
            System.out.println("Generated Password: " + password);

            System.out.println("1. Copy to clipboard");
            System.out.println("2. Generate another password");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline

            switch (choice) {
                case 1:
                    generator.copyToClipboard(password);
                    break;
                case 2:
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
        scanner.close();
    }
}