package com.example.Advent_of_code;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Advent_of_code_2025_012_002_001 {

    public static void main(String[] args) {
        start_advent_path_repl();
    }

    public static void start_advent_path_repl() {
        Scanner input_scanner = new Scanner(System.in);

        System.out.println("--- Advent of Code 2025-012-002-001 REPL ---");
        System.out.println("Logic: Sum of repeated-digit numbers in ranges.");
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
                BigInteger result = solve_repeated_sum(file_path);
                System.out.println("Total BigInteger Sum: " + result);
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Processing error: " + e.getMessage());
            }
        }

        input_scanner.close();
    }

    public static BigInteger solve_repeated_sum(Path input_file) throws IOException {
        String content = Files.readString(input_file).trim();
        if (content.isEmpty()) return BigInteger.ZERO;

        String[] range_tokens = content.split(",");
        long[][] range_bounds = new long[range_tokens.length][2];

        long global_max = 0;
        for (int i = 0; i < range_tokens.length; i++) {
            String token = range_tokens[i].trim();
            int dash_index = token.indexOf('-');
            if (dash_index < 0) throw new IllegalArgumentException("Invalid token: " + token);

            long start = Long.parseLong(token.substring(0, dash_index).trim());
            long end = Long.parseLong(token.substring(dash_index + 1).trim());

            if (start > end) {
                long temp = start; start = end; end = temp;
            }

            range_bounds[i][0] = start;
            range_bounds[i][1] = end;
            if (end > global_max) global_max = end;
        }

        int max_digits = get_digit_count(global_max);
        int max_k = max_digits / 2;

        long[] powers_of_10 = new long[Math.max(2, max_k + 1)];
        powers_of_10[0] = 1;
        for (int i = 1; i < powers_of_10.length; i++) {
            powers_of_10[i] = powers_of_10[i - 1] * 10L;
        }

        BigInteger total_sum = BigInteger.ZERO;

        for (long[] current_range : range_bounds) {
            long range_start = current_range[0];
            long range_end = current_range[1];
            if (range_start == 0 && range_end == 0) continue;

            for (int k = 1; k <= max_k; k++) {
                long multiplier = powers_of_10[k] + 1L;          // n = x * (10^k + 1)
                long x_min = powers_of_10[k - 1];               // min k-digit
                long x_max = powers_of_10[k] - 1;               // max k-digit

                long low_x = (range_start + multiplier - 1L) / multiplier; // ceilDiv
                long high_x = range_end / multiplier;

                if (low_x < x_min) low_x = x_min;
                if (high_x > x_max) high_x = x_max;

                if (low_x <= high_x) {
                    total_sum = total_sum.add(calculate_repeated_range_sum(low_x, high_x, multiplier));
                }
            }
        }

        return total_sum;
    }

    private static BigInteger calculate_repeated_range_sum(long lo, long hi, long m) {
        BigInteger count = BigInteger.valueOf(hi - lo + 1L);
        // Sum = (lo + hi) * count / 2
        BigInteger sum_of_x = BigInteger.valueOf(lo)
                .add(BigInteger.valueOf(hi))
                .multiply(count)
                .divide(BigInteger.valueOf(2L));
        return BigInteger.valueOf(m).multiply(sum_of_x);
    }

    private static int get_digit_count(long value) {
        if (value == 0) return 1;
        int count = 0;
        long temp = Math.abs(value);
        while (temp > 0) {
            count++;
            temp /= 10;
        }
        return count;
    }
}