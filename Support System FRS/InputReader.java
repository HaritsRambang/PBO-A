import java.util.Scanner;

public class InputReader {
    private Scanner scanner;

    public InputReader() {
        scanner = new Scanner(System.in);
    }    
    public String getInput() {
        System.out.print("> ");
        String input = scanner.nextLine().trim().toLowerCase();
        return input;
    }
}