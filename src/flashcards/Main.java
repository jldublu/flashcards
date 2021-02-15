package flashcards;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Actions actions = new Actions(args, scanner);

        boolean askAgain = true;
        while (askAgain) {
            String action = actions.promptUserForInput("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            askAgain = actions.executeAction(action);
        }
    }
}
