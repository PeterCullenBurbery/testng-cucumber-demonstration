package com.example.Get_otp_example_for_Google;

import java.util.Scanner;

public class path_transformer {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String prefix_to_remove = "C:\\rsupply-web\\nested-folder-001\\";
        String replacement_prefix = "C:\\";

        System.out.println("--- Path Transformation Tool (Java 25) ---");
        System.out.println("Type 'exit' to quit.");

        while (true) {
            System.out.print("\nEnter a path: ");
            String input_path = scanner.nextLine();

            if (input_path.equalsIgnoreCase("exit")) {
                System.out.println("Exiting program.");
                break;
            }

            if (input_path.startsWith(prefix_to_remove)) {
                // Remove the nested prefix and prepend the root C:\
                String transformed_path = replacement_prefix + input_path.substring(prefix_to_remove.length());
                System.out.println("Transformed: " + transformed_path);
            } else {
                System.out.println("Path does not contain the specified nested folder prefix.");
            }
        }
        scanner.close();
    }
}