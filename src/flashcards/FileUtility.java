package flashcards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtility {
    private final File file;

    public FileUtility(String fileName) {
        file = new File(fileName);
    }

    public void exportCardsToFile(List<Flashcard> flashcards) {
        try (PrintWriter writer = new PrintWriter(file)) {
            int count = 0;

            for (Flashcard card : flashcards) {
                writer.println(card.getTerm() + "|" + card.getDefinition() + "|" + card.getErrorCount());
                count++;
            }

            Logger.logConsoleOutput(count + " cards have been saved.");
        } catch (IOException e) {
            Logger.logConsoleOutput("Error while writing to file...");
        }
    }

    public List<Flashcard> importCardsFromFile() {
        List<Flashcard> flashcards = new ArrayList<>();

        try (Scanner fileScanner = new Scanner(file)) {
            int count = 0;

            while (fileScanner.hasNextLine()) {
                String[] entry = fileScanner.nextLine().split("\\|");
                flashcards.add(new Flashcard(entry[0], entry[1], Integer.parseInt(entry[2])));
                count++;
            }

            Logger.logConsoleOutput(count + " cards have been loaded.");
        } catch (FileNotFoundException e) {
            Logger.logConsoleOutput("File not found.");
        }

        return flashcards;
    }

    public void exportLogsToFile(List<String> logs) {
        try (PrintWriter writer = new PrintWriter(file)) {
            for (String log : logs) {
                writer.println(log);
            }

            Logger.logConsoleOutput("The log has been saved.");
        } catch (IOException e) {
            Logger.logConsoleOutput("Error while writing to file...");
        }
    }
}
