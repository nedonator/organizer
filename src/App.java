import java.util.Scanner;

public class App {
    private static boolean isRunning = true;
    private static final Database database = new Database("./database.json");

    public static void main(String[] args) {
        database.readFromFile();
        ConsoleHandler consoleHandler = new ConsoleHandler(database);
        Scanner scanner = new Scanner(System.in);
        while(isRunning){
            String s = scanner.nextLine();
            consoleHandler.handle(s);
        }
    }

    public static void terminate(){
        isRunning = false;
        database.writeToFile();
    }
}
