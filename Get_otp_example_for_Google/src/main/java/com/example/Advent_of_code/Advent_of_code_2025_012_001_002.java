package com.example.Advent_of_code;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Advent_of_code_2025_012_001_002 {

    private static final int MOD_VALUE = 100;

    public static void main(String[] args) {
        start_advent_path_repl();
    }

    public static void start_advent_path_repl() {
        Scanner input_scanner = new Scanner(System.in);

        System.out.println("--- Advent of Code 2025-012-001-002 REPL ---");
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
                long total_zero_clicks = solve_file(file_path);
                System.out.println("Result (Zero Clicks): " + total_zero_clicks);
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.err.println("Input format error: " + e.getMessage());
            }
        }

        input_scanner.close();
    }

    public static long solve_file(Path input_file) throws IOException {
        int current_pos = 50;
        long total_zero_clicks = 0;

        try (BufferedReader reader = Files.newBufferedReader(input_file)) {
            String line;
            long line_no = 0;

            while ((line = reader.readLine()) != null) {
                line_no++;
                line = line.trim();
                if (line.isEmpty()) continue;

                char direction = Character.toUpperCase(line.charAt(0));
                if (direction != 'L' && direction != 'R') {
                    throw new IllegalArgumentException("Line " + line_no + " must start with L or R.");
                }

                try {
                    long distance = Long.parseLong(line.substring(1).trim());
                    if (distance < 0) throw new NumberFormatException();
                    if (distance == 0) continue;

                    // Calculate hits on zero during this specific rotation
                    total_zero_clicks += count_hits_zero_during_rotation(current_pos, direction, distance);

                    // Update the final position for the next command
                    long dist_mod = distance % MOD_VALUE;
                    if (direction == 'R') {
                        current_pos = (int) ((current_pos + dist_mod) % MOD_VALUE);
                    } else {
                        current_pos = (int) ((current_pos - dist_mod) % MOD_VALUE);
                        if (current_pos < 0) current_pos += MOD_VALUE;
                    }

                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Line " + line_no + " has invalid distance: " + line);
                }
            }
        }
        return total_zero_clicks;
    }

    private static long count_hits_zero_during_rotation(int pos, char dir, long dist) {
        int first_k;

        if (dir == 'R') {
            // pos + k = 0 (mod 100) -> k = -pos (mod 100)
            int k0 = (MOD_VALUE - (pos % MOD_VALUE)) % MOD_VALUE;
            first_k = (k0 == 0) ? MOD_VALUE : k0;
        } else {
            // pos - k = 0 (mod 100) -> k = pos (mod 100)
            int k0 = pos % MOD_VALUE;
            first_k = (k0 == 0) ? MOD_VALUE : k0;
        }

        if (first_k > dist) {
            return 0;
        }

        return 1 + (dist - first_k) / MOD_VALUE;
    }
}