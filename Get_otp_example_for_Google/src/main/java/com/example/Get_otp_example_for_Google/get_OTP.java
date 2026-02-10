package com.example.Get_otp_example_for_Google;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import java.util.Scanner;

public class get_OTP {

    public static void main(String[] args) {
        start_otp_repl();
    }

    public static void start_otp_repl() {
        Scanner input_scanner = new Scanner(System.in);
        GoogleAuthenticator g_auth = new GoogleAuthenticator();

        System.out.println("--- Google OTP Generator REPL ---");
        System.out.println("Type 'exit' to quit.");

        while (true) {
            System.out.print("\nEnter Secret Key (Base32): ");
            String secret_key = input_scanner.nextLine().trim();

            if (secret_key.equalsIgnoreCase("exit")) {
                System.out.println("Exiting. Goodbye!");
                break;
            }

            if (secret_key.isEmpty()) {
                System.out.println("Error: Secret key cannot be empty.");
                continue;
            }

            try {
                // Generate the 6-digit code
                int password = g_auth.getTotpPassword(secret_key);
                
                // Format with leading zeros if necessary
                String formatted_code = String.format("%06d", password);
                
                System.out.println("Current OTP: " + formatted_code);
            } catch (Exception e) {
                System.out.println("Error: Invalid Secret Key format. Ensure it is Base32.");
            }
        }

        input_scanner.close();
    }
}