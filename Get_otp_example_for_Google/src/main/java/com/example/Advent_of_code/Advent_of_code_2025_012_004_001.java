package com.example.Advent_of_code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Advent_of_code_2025_012_004_001 {

    public static void main(String[] args) {
        start_grid_path_repl();
    }

    public static void start_grid_path_repl() {
        Scanner input_scanner = new Scanner(System.in);

        System.out.println("--- Advent of Code 2025-012-004-001 REPL ---");
        System.out.println("Logic: Count '@' rolls with fewer than 4 adjacent neighbors.");
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
                long result = solve_grid_accessibility(file_path);
                System.out.println("Accessible Rolls Count: " + result);
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.err.println("Grid data error: " + e.getMessage());
            }
        }

        input_scanner.close();
    }

    public static long solve_grid_accessibility(Path input_file) throws IOException {
        List<String> grid_lines = Files.readAllLines(input_file);
        if (grid_lines.isEmpty()) return 0;

        int row_count = grid_lines.size();
        int col_count = grid_lines.get(0).length();

        char[][] grid_data = new char[row_count][col_count];
        for (int r = 0; r < row_count; r++) {
            String current_line = grid_lines.get(r);
            if (current_line.length() != col_count) {
                throw new IllegalArgumentException("Grid is not rectangular at line " + (r + 1));
            }
            for (int c = 0; c < col_count; c++) {
                char ch = current_line.charAt(c);
                if (ch != '@' && ch != '.') {
                    throw new IllegalArgumentException("Invalid char '" + ch + "' at (" + r + "," + c + ")");
                }
                grid_data[r][c] = ch;
            }
        }

        // Relative coordinates for 8 neighbors (N, S, E, W, and diagonals)
        int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};

        long accessible_count = 0;

        for (int r = 0; r < row_count; r++) {
            for (int c = 0; c < col_count; c++) {
                if (grid_data[r][c] != '@') continue;

                int neighbor_rolls = 0;
                for (int i = 0; i < 8; i++) {
                    int neighbor_r = r + dr[i];
                    int neighbor_c = c + dc[i];

                    // Check bounds and count '@' neighbors
                    if (neighbor_r >= 0 && neighbor_r < row_count && 
                        neighbor_c >= 0 && neighbor_c < col_count) {
                        if (grid_data[neighbor_r][neighbor_c] == '@') {
                            neighbor_rolls++;
                        }
                    }
                }

                // If fewer than 4 neighbors are rolls, it's accessible
                if (neighbor_rolls < 4) {
                    accessible_count++;
                }
            }
        }

        return accessible_count;
    }
}