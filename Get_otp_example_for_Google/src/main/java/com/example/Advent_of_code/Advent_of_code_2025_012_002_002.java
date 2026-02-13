package com.example.Advent_of_code;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class Advent_of_code_2025_012_002_002 {

    public static void main(String[] args) {
        start_advent_path_repl();
    }

    public static void start_advent_path_repl() {
        Scanner input_scanner = new Scanner(System.in);

        System.out.println("--- Advent of Code 2025-012-002-002 REPL ---");
        System.out.println("Logic: Sum of unique repeated-block numbers in ranges.");
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
                BigInteger result = solve_repeated_blocks(file_path);
                System.out.println("Total Repeated Block Sum: " + result);
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Processing error: " + e.getMessage());
            }
        }

        input_scanner.close();
    }

    public static BigInteger solve_repeated_blocks(Path input_file) throws IOException {
        String content = Files.readString(input_file).trim();
        if (content.isEmpty()) return BigInteger.ZERO;

        String[] parts = content.split(",");
        long[][] range_list = new long[parts.length][2];
        long global_max = 0;

        for (int i = 0; i < parts.length; i++) {
            String token = parts[i].trim();
            int dash = token.indexOf('-');
            if (dash < 0) throw new IllegalArgumentException("Bad range: " + token);

            long a = Long.parseLong(token.substring(0, dash).trim());
            long b = Long.parseLong(token.substring(dash + 1).trim());
            if (a > b) { long t = a; a = b; b = t; }

            range_list[i][0] = a;
            range_list[i][1] = b;
            if (b > global_max) global_max = b;
        }

        long[] invalid_ids = generate_repeated_numbers(global_max);

        // Precompute prefix sums for O(1) range sum after O(log N) search
        BigInteger[] prefix_sums = new BigInteger[invalid_ids.length + 1];
        prefix_sums[0] = BigInteger.ZERO;
        for (int i = 0; i < invalid_ids.length; i++) {
            prefix_sums[i + 1] = prefix_sums[i].add(BigInteger.valueOf(invalid_ids[i]));
        }

        BigInteger total = BigInteger.ZERO;
        for (long[] range : range_list) {
            int left_idx = find_lower_bound(invalid_ids, range[0]);
            int right_idx = find_upper_bound(invalid_ids, range[1]);
            if (left_idx < right_idx) {
                total = total.add(prefix_sums[right_idx].subtract(prefix_sums[left_idx]));
            }
        }

        return total;
    }

    private static long[] generate_repeated_numbers(long max) {
        if (max < 11) return new long[0];

        int max_digits = get_digit_count(max);
        long[] powers_of_10 = new long[max_digits + 1];
        powers_of_10[0] = 1L;
        for (int i = 1; i <= max_digits; i++) powers_of_10[i] = powers_of_10[i - 1] * 10L;

        Long_List collected = new Long_List(120_000);

        for (int total_len = 2; total_len <= max_digits; total_len++) {
            for (int block_len = 1; block_len < total_len; block_len++) {
                if (total_len % block_len != 0) continue;
                int repeat_count = total_len / block_len;

                long block_pow = powers_of_10[block_len];
                long start_x = powers_of_10[block_len - 1];
                long end_x = powers_of_10[block_len] - 1;

                for (long x = start_x; x <= end_x; x++) {
                    long value = 0L;
                    for (int r = 0; r < repeat_count; r++) {
                        value = value * block_pow + x;
                    }
                    if (value > max) break;
                    collected.add(value);
                }
            }
        }

        long[] sorted_unique = collected.to_array();
        Arrays.sort(sorted_unique);
        int unique_count = 0;
        for (int i = 0; i < sorted_unique.length; i++) {
            if (i == 0 || sorted_unique[i] != sorted_unique[i - 1]) {
                sorted_unique[unique_count++] = sorted_unique[i];
            }
        }
        return Arrays.copyOf(sorted_unique, unique_count);
    }

    private static int find_lower_bound(long[] arr, long key) {
        int lo = 0, hi = arr.length;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (arr[mid] < key) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }

    private static int find_upper_bound(long[] arr, long key) {
        int lo = 0, hi = arr.length;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (arr[mid] <= key) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }

    private static int get_digit_count(long x) {
        if (x == 0) return 1;
        int d = 0;
        while (x > 0) { d++; x /= 10; }
        return d;
    }

    private static class Long_List {
        private long[] internal_data;
        private int current_size;

        Long_List(int capacity) {
            internal_data = new long[capacity];
            current_size = 0;
        }

        void add(long val) {
            if (current_size == internal_data.length) {
                internal_data = Arrays.copyOf(internal_data, internal_data.length * 2);
            }
            internal_data[current_size++] = val;
        }

        long[] to_array() {
            return Arrays.copyOf(internal_data, current_size);
        }
    }
}