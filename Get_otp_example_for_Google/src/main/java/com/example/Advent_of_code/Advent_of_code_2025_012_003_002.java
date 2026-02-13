package com.example.Advent_of_code;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Advent_of_code_2025_012_003_002 {

    private static final int DIGITS_TO_PICK = 12;

    public static void main(String[] args) {
        start_subsequence_path_repl();
    }

    public static void start_subsequence_path_repl() {
        Scanner input_scanner = new Scanner(System.in);

        System.out.println("--- Advent of Code 2025-012-003-002 REPL ---");
        System.out.println("Logic: Find largest subsequence of length " + DIGITS_TO_PICK + " per line.");
        System.out.println("Type 'exit' to quit.");

        while (true) {
            System.out.print("\nEnter input file path: ");
            String path_input = input_scanner.nextLine().trim();

            if (path_input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting. Goodbye!");
                break;
            }

            if (path_input.isEmpty()) continue;

            Path file_path = Paths.get(path_input);
            if (!Files.exists(file_path)) {
                System.out.println("Error: Path does not exist.");
                continue;
            }

            try {
                BigInteger total_joltage = solve_subsequence(file_path);
                System.out.println("Total Output Joltage: " + total_joltage);
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.err.println("Data error: " + e.getMessage());
            }
        }

        input_scanner.close();
    }

    public static BigInteger solve_subsequence(Path input_file) throws IOException {
        BigInteger total_sum = BigInteger.ZERO;

        try (BufferedReader reader = Files.newBufferedReader(input_file)) {
            String line;
            long line_no = 0;

            while ((line = reader.readLine()) != null) {
                line_no++;
                line = line.trim();
                if (line.isEmpty()) continue;

                if (!is_all_digits(line)) {
                    throw new IllegalArgumentException("Line " + line_no + " contains non-digits.");
                }

                if (line.length() < DIGITS_TO_PICK) {
                    throw new IllegalArgumentException("Line " + line_no + " is too short for length " + DIGITS_TO_PICK);
                }

                String max_sub = get_max_subsequence(line, DIGITS_TO_PICK);
                total_sum = total_sum.add(new BigInteger(max_sub));
            }
        }

        return total_sum;
    }

    private static String get_max_subsequence(String digits, int k) {
        int n = digits.length();
        int deletes_remaining = n - k;

        char[] stack = new char[n];
        int stack_size = 0;

        for (int i = 0; i < n; i++) {
            char current = digits.charAt(i);

            // While we have deletions left and the current digit is bigger than the last, pop stack
            while (deletes_remaining > 0 && stack_size > 0 && stack[stack_size - 1] < current) {
                stack_size--;
                deletes_remaining--;
            }

            stack[stack_size++] = current;
        }

        // Return only the first K digits (in case we didn't delete enough yet)
        return new String(stack, 0, k);
    }

    private static boolean is_all_digits(String s) {
        for (char c : s.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }
}