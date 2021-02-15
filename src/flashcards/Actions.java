package flashcards;

import java.util.List;
import java.util.Scanner;

public class Actions {

    private Scanner scanner;
    private final String[] args;
    private Flashcards flashcards;
    private boolean doFileExportOnExit = false;
    private String exportFileName = "";

    public Actions(String[] args, Scanner scanner) {
        this.scanner = scanner;
        this.args = args;

        flashcards = new Flashcards();

        if (args.length > 0) {
            handleCommandLineArguments();
        }
    }

    private void handleCommandLineArguments() {
        for (int i = 0, n = args.length - 1; i < n; i += 2) {
            if (args[i].equals("-import")) {
                List<Flashcard> importedFlashcards = new FileUtility(args[i + 1]).importCardsFromFile();
                flashcards.addImportedFlashcards(importedFlashcards);
            } else if (args[i].equals("-export")) {
                doFileExportOnExit = true;
                exportFileName = args[i + 1];
            }
        }
    }

    public String promptUserForInput(String prompt) {
        Logger.logConsoleOutput(prompt);

        String input = scanner.nextLine();
        Logger.logConsoleInput(input);

        return input;
    }

    public boolean executeAction(String action) {
        switch (action.toLowerCase()) {
            case "add":
                createFlashcard();
                Logger.logConsoleOutput("");
                break;
            case "remove":
                deleteFlashcard();
                Logger.logConsoleOutput("");
                break;
            case "import":
                importCardsFromFile();
                Logger.logConsoleOutput("");
                break;
            case "export":
                exportCardsToFile();
                Logger.logConsoleOutput("");
                break;
            case "ask":
                testUser();
                Logger.logConsoleOutput("");
                break;
            case "log":
                exportLogsToFile();
                Logger.logConsoleOutput("");
                break;
            case "hardest card":
                printHardestCard();
                Logger.logConsoleOutput("");
                break;
            case "reset stats":
                resetErrors();
                Logger.logConsoleOutput("");
                break;
            case "exit":
                Logger.logConsoleOutput("Bye bye!");
                if (doFileExportOnExit) {
                    new FileUtility(exportFileName).exportCardsToFile(flashcards.getFlashcards());
                }

                return false;
        }

        return true;
    }

    private void createFlashcard() {
        String term = promptUserForInput("The card:");

        if (flashcards.doesTermExist(term)) {
            Logger.logConsoleOutput("The card \"" + term + "\" already exists.");
        } else {
            String definition = promptUserForInput("The definition of the card:");

            if (flashcards.doesDefinitionExist(definition)) {
                Logger.logConsoleOutput("The definition \"" + definition + "\" already exists.");
            } else {
                flashcards.addCard(term, definition);
                Logger.logConsoleOutput("The pair (\"" + term + "\":\"" + definition + "\") has been added.");
            }
        }
    }

    private void resetErrors() {
        flashcards.resetErrors();
        Logger.logConsoleOutput("Card statistics have been reset.");
    }

    private void printHardestCard() {
        if (!flashcards.doFlashcardsHaveErrors()) {
            Logger.logConsoleOutput("There are no cards with errors.");
        } else {
            List<Flashcard> highestErrorCards = flashcards.getHighestErrorCards();
            int errorCount = highestErrorCards.get(0).getErrorCount();

            //print hardest card(s)
            if (highestErrorCards.size() == 1) {
                Logger.logConsoleOutput("The hardest card is \"" + highestErrorCards.get(0).getTerm() + "\". You have " + errorCount + " errors answering it.");
            } else {
                StringBuilder termsBuilder = new StringBuilder();

                for (Flashcard card : highestErrorCards) {
                    termsBuilder.append(" \"").append(card.getTerm()).append("\",");
                }

                String terms = termsBuilder.toString();
                terms = terms.substring(0, terms.length() - 1);

                Logger.logConsoleOutput("The hardest cards are" + terms + ". You have " + errorCount + " errors answering them.");
            }
        }
    }

    private void exportLogsToFile() {
        String fileName = promptUserForInput("File name:");
        new FileUtility(fileName).exportLogsToFile(Logger.getLogs());
    }

    private void exportCardsToFile() {
        String fileName = promptUserForInput("File name:");
        new FileUtility(fileName).exportCardsToFile(flashcards.getFlashcards());
    }

    private void importCardsFromFile() {
        String fileName = promptUserForInput("File name:");
        List<Flashcard> importedFlashcards = new FileUtility(fileName).importCardsFromFile();

        flashcards.addImportedFlashcards(importedFlashcards);
    }

    private void deleteFlashcard() {
        String term = promptUserForInput("Which card?");

        if (flashcards.doesTermExist(term)) {
            flashcards.removeCard(term);
            Logger.logConsoleOutput("The card has been removed.");
        } else {
            Logger.logConsoleOutput("Can't remove \"" + term + "\": there is no such card.");
        }
    }

    private void testUser() {
        int numberOfCards = Integer.parseInt(promptUserForInput("How many times to ask?"));

        for (int i = 0; i < numberOfCards; i++) {
            Flashcard flashcard = flashcards.getFlashcard();

            String term = flashcard.getTerm();
            String definition = flashcard.getDefinition();

            String answer = promptUserForInput("Print the definition of \"" + term + "\":");

            if (answer.equals(definition)) {
                Logger.logConsoleOutput("Correct!");
            } else {
                if (flashcards.doesDefinitionExist(answer)) {
                    Flashcard correctFlashcard = flashcards.getFlashcardWithDefinition(answer);

                    Logger.logConsoleOutput("Wrong. The right answer is \"" + definition + "\", but your definition " +
                                    "is correct for \"" + correctFlashcard.getTerm() + "\".");

                } else {
                    Logger.logConsoleOutput("Wrong. The right answer is \"" + definition + "\".");
                }

                //log wrong answers in errors map
                flashcards.logError(term);
            }
        }
    }
}