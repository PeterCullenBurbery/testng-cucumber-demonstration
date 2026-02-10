package com.example.Get_otp_example_for_Google;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.EncryptionMethod;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Zip_folder_into_zip_file {

    public static void main(String[] args) {
        start_zip_repl();
    }

    public static void start_zip_repl() {
        Scanner input_scanner = new Scanner(System.in);

        System.out.println("--- Password Protected Zip Creator ---");
        System.out.println("Type 'exit' at any prompt to quit.");

        while (true) {
            System.out.print("\nEnter folder path: ");
            String source_path = input_scanner.nextLine().trim();
            if (source_path.equalsIgnoreCase("exit")) break;

            System.out.print("Enter target folder: ");
            String target_folder = input_scanner.nextLine().trim();
            if (target_folder.equalsIgnoreCase("exit")) break;

            System.out.print("Enter target file name: ");
            String target_name = input_scanner.nextLine().trim();
            if (target_name.equalsIgnoreCase("exit")) break;

            System.out.print("Enter password: ");
            String zip_password = input_scanner.nextLine().trim();
            if (zip_password.equalsIgnoreCase("exit")) break;

            process_zip_creation(source_path, target_folder, target_name, zip_password);
        }

        input_scanner.close();
        System.out.println("Exiting. Goodbye!");
    }

    private static void process_zip_creation(String source, String target_dir, String name, String password) {
        try {
            Path source_path = Paths.get(source);
            Path destination_dir = Paths.get(target_dir);

            // Ensure destination directory exists
            if (!Files.exists(destination_dir)) {
                Files.createDirectories(destination_dir);
            }

            File final_zip_file = destination_dir.resolve(name).toFile();
            
            // Delete if already exists to avoid appending
            if (final_zip_file.exists()) {
                final_zip_file.delete();
            }

            ZipFile zip_file = new ZipFile(final_zip_file, password.toCharArray());

            ZipParameters zip_parameters = new ZipParameters();
            zip_parameters.setEncryptFiles(true);
            zip_parameters.setEncryptionMethod(EncryptionMethod.AES);

            System.out.println("Compressing...");
            
            if (Files.isDirectory(source_path)) {
                zip_file.addFolder(source_path.toFile(), zip_parameters);
            } else {
                zip_file.addFile(source_path.toFile(), zip_parameters);
            }

            System.out.println("Success! Zip created at: " + final_zip_file.getAbsolutePath());

        } catch (Exception e) {
            System.err.println("Error creating zip: " + e.getMessage());
        }
    }
}