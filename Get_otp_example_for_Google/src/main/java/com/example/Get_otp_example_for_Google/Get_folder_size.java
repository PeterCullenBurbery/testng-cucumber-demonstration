package com.example.Get_otp_example_for_Google;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class Get_folder_size {

    public static void main(String[] args) {
        start_size_repl();
    }

    public static void start_size_repl() {
        Scanner input_scanner = new Scanner(System.in);

        System.out.println("--- Folder Size Calculator ---");
        System.out.println("Type 'exit' to quit.");

        while (true) {
            System.out.print("\nEnter folder path: ");
            String path_input = input_scanner.nextLine().trim();

            if (path_input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting. Goodbye!");
                break;
            }

            if (path_input.isEmpty()) {
                continue;
            }

            Path folder_path = Paths.get(path_input);

            if (!Files.exists(folder_path)) {
                System.out.println("Error: Path does not exist.");
                continue;
            }

            if (!Files.isDirectory(folder_path)) {
                System.out.println("Error: Path is not a directory.");
                continue;
            }

            calculate_and_display_size(folder_path);
        }

        input_scanner.close();
    }

    private static void calculate_and_display_size(Path path) {
        AtomicLong total_bytes = new AtomicLong(0);
        AtomicLong file_count = new AtomicLong(0);

        try (Stream<Path> path_stream = Files.walk(path)) {
            path_stream.forEach(p -> {
                if (Files.isRegularFile(p)) {
                    file_count.incrementAndGet();
                    try {
                        total_bytes.addAndGet(Files.size(p));
                    } catch (IOException e) {
                        System.err.println("Could not read size of: " + p);
                    }
                }
            });

            double size_in_mb = total_bytes.get() / (1024.0 * 1024.0);
            
            System.out.println(file_count.get() + " files");
            System.out.printf("%.2f MB\n", size_in_mb);

        } catch (IOException e) {
            System.out.println("Error walking the file tree: " + e.getMessage());
        }
    }
}