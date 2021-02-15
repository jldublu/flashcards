package flashcards;

public class Flashcard {

    private String term;
    private String definition;
    private int errorCount;

    public Flashcard(String term, String definition, int errorCount) {
        this.term = term;
        this.definition = definition;
        this.errorCount = errorCount;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }
}
