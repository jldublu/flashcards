package flashcards;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Flashcards {

    private List<Flashcard> flashcards;

    public Flashcards() {
        flashcards = new ArrayList<>();
    }

    public void addCard(String term, String definition) {
        flashcards.add(new Flashcard(term, definition, 0));
    }

    public void addImportedFlashcards(List<Flashcard> importedFlashcards) {
        for (Flashcard importedFlashcard : importedFlashcards) {
            boolean doesFlashcardExist = false;

            for (Flashcard flashcard : flashcards) {
                if (flashcard.getTerm().equals(importedFlashcard.getTerm())) {
                    flashcard.setDefinition(importedFlashcard.getDefinition());
                    flashcard.setErrorCount(importedFlashcard.getErrorCount());
                    doesFlashcardExist = true;
                    break;
                }
            }

            if (!doesFlashcardExist) {
                flashcards.add(importedFlashcard);
            }
        }
    }

    public boolean doesDefinitionExist(String definition) {
        return flashcards.stream().anyMatch(flashcard -> flashcard.getDefinition().equals(definition));
    }

    public boolean doesTermExist(String term) {
        return flashcards.stream().anyMatch(flashcard -> flashcard.getTerm().equals(term));
    }

    public boolean doFlashcardsHaveErrors() {
        return flashcards.stream().anyMatch(flashcard -> flashcard.getErrorCount() > 0);
    }

    public Flashcard getFlashcard() {
        return flashcards.get(new Random().nextInt(flashcards.size()));
    }

    public Flashcard getFlashcard(String term) {
        return flashcards.stream().filter(flashcard -> flashcard.getTerm().equals(term)).findFirst().get();
    }

    public Flashcard getFlashcardWithDefinition(String definition) {
        return flashcards.stream().filter(flashcard -> flashcard.getDefinition().equals(definition)).findFirst().get();
    }

    public List<Flashcard> getFlashcards() {
        return flashcards;
    }

    public List<Flashcard> getHighestErrorCards() {
        List<Flashcard> highestErrorCards = new ArrayList<>();
        int maxErrorCount = 0;

        for (Flashcard card : flashcards) {
            int errorCount = card.getErrorCount();

            if (errorCount == maxErrorCount) {
                highestErrorCards.add(card);
            } else if (errorCount > maxErrorCount) {
                highestErrorCards.clear();
                highestErrorCards.add(card);
                maxErrorCount = errorCount;
            }
        }

        return highestErrorCards;
    }

    public void removeCard(String term) {
        flashcards.remove(getFlashcard(term));
    }

    public void resetErrors() {
        flashcards.forEach(flashcard -> flashcard.setErrorCount(0));
    }

    public void logError(String term) {
        Flashcard card = getFlashcard(term);
        card.setErrorCount(card.getErrorCount() + 1);
    }
}
