package com.example.Advent_of_code;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Advent_of_code_2025_012_003_001 {

    public static void main(String[] args) {
        start_joltage_path_repl();
    }

    public static void start_joltage_path_repl() {
        Scanner input_scanner = new Scanner(System.in);

        System.out.println("--- Advent of Code 2025-012-003-001 REPL ---");
        System.out.println("Logic: Maximize 2-digit combinations (Joltage) per line.");
        System.out.println("Type 'exit' to quit.");

        while (true) {
            System.out.print("\nEnter input file path: ");
            String path_input = input_scanner.nextLine().trim();

            if (path_input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting. Goodbye!");
                break;
            }

            if (path_input.isEmpty()) {
                continue;
            }

            Path file_path = Paths.get(path_input);

            if (!Files.exists(file_path)) {
                System.out.println("Error: Path does not exist.");
                continue;
            }

            try {
                BigInteger total_joltage = solve_joltage(file_path);
                System.out.println("Total Output Joltage: " + total_joltage);
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid input data: " + e.getMessage());
            }
        }

        input_scanner.close();
    }

    public static BigInteger solve_joltage(Path input_file_path) throws IOException {
        BigInteger total_sum = BigInteger.ZERO;

        try (BufferedReader buffered_reader = Files.newBufferedReader(input_file_path)) {
            String line;
            long line_number = 0;

            while ((line = buffered_reader.readLine()) != null) {
                line_number++;
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.length() < 2) {
                    throw new IllegalArgumentException("Line " + line_number + " is too short.");
                }

                int max_line_val = calculate_max_two_digit(line);
                total_sum = total_sum.add(BigInteger.valueOf(max_line_val));
            }
        }

        return total_sum;
    }

    private static int calculate_max_two_digit(String bank_digits) {
        int length = bank_digits.length();
        int[] digits = new int[length];

        for (int i = 0; i < length; i++) {
            char c = bank_digits.charAt(i);
            if (!Character.isDigit(c)) {
                throw new IllegalArgumentException("Non-digit found: " + c);
            }
            digits[i] = c - '0';
        }

        // Suffix Max: stores the largest digit found from index i to the end
        int[] suffix_max = new int[length];
        suffix_max[length - 1] = digits[length - 1];
        for (int i = length - 2; i >= 0; i--) {
            suffix_max[i] = Math.max(digits[i], suffix_max[i + 1]);
        }

        int best_value = -1;
        for (int i = 0; i < length - 1; i++) {
            int tens = digits[i];
            int ones = suffix_max[i + 1];
            int current_value = (10 * tens) + ones;

            if (current_value > best_value) {
                best_value = current_value;
            }

            // Optimization: Cannot exceed 99
            if (best_value == 99) break;
        }

        return best_value;
    }
}