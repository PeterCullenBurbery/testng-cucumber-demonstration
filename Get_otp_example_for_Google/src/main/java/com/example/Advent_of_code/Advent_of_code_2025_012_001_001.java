package com.example.Advent_of_code;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Advent_of_code_2025_012_001_001 {

    private static final int MOD_VALUE = 100;

    public static void main(String[] args) {
        start_advent_path_repl();
    }

    public static void start_advent_path_repl() {
        Scanner input_scanner = new Scanner(System.in);

        System.out.println("--- Advent of Code 2025-012-001-001 Path REPL ---");
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
                long result = solve_from_file(file_path);
                System.out.println("Total count at zero: " + result);
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.err.println("Data error: " + e.getMessage());
            }
        }

        input_scanner.close();
    }

    public static long solve_from_file(Path input_file) throws IOException {
        int current_pos = 50;
        long count_at_zero = 0;

        try (BufferedReader reader = Files.newBufferedReader(input_file)) {
            String line;
            long line_no = 0;

            while ((line = reader.readLine()) != null) {
                line_no++;
                line = line.trim();
                if (line.isEmpty()) continue;

                char direction = Character.toUpperCase(line.charAt(0));
                if (direction != 'L' && direction != 'R') {
                    throw new IllegalArgumentException("Line " + line_no + " invalid: " + line);
                }

                try {
                    int distance = Integer.parseInt(line.substring(1).trim());
                    distance = ((distance % MOD_VALUE) + MOD_VALUE) % MOD_VALUE;

                    if (direction == 'R') {
                        current_pos = (current_pos + distance) % MOD_VALUE;
                    } else {
                        current_pos = (current_pos - distance) % MOD_VALUE;
                        if (current_pos < 0) current_pos += MOD_VALUE;
                    }

                    if (current_pos == 0) {
                        count_at_zero++;
                    }
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Line " + line_no + " has bad distance: " + line);
                }
            }
        }
        return count_at_zero;
    }
}