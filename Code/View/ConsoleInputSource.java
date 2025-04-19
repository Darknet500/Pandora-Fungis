package View;

import java.util.Scanner;

public class ConsoleInputSource implements InputSource {
    private Scanner scanner;
    public ConsoleInputSource() {
        scanner = new Scanner(System.in);
    }

    public String readLine() {
        return scanner.nextLine();
    }

    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    public void close(){
        scanner.close();
    }
}
