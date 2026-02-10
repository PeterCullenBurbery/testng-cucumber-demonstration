import java.util.Scanner;

public class length_of_text {

    public static void main(String[] args) {
        start_length_checker();
    }

    public static void start_length_checker() {
        Scanner input_scanner = new Scanner(System.in);
        
        System.out.println("--- String Length Tool (Java) ---");
        System.out.println("Type 'exit' to quit the program.");

        while (true) {
            // Get user input
            System.out.print("\nEnter a string: ");
            String user_input = input_scanner.nextLine();

            // Check for the exit condition
            if (user_input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting program. Goodbye!");
                break;
            }

            // Calculate and print length
            int input_length = user_input.length();
            System.out.println("Length: " + input_length);
        }

        input_scanner.close();
    }
}