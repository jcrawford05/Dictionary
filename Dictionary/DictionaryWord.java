package comprehensive;

import java.util.TreeSet;

/**
 * A class that represents a DictionaryWord in a Dictionary
 *
 * @author Logan Waller and James Crawford
 * @version December 5th, 2024
 */
public class DictionaryWord {
    // Variables
    private String word;
    private TreeSet<DictionaryDefinition> definitions = new TreeSet<>();
    private TreeSet<String> usedPartsOfSpeech = new TreeSet<String>() {};
    // Constants
    private final String NEWLINE = System.lineSeparator();
    private final String TAB = "\t";

    /**
     * Creates a DictionaryWord Object. Object contains the word, and its associated definitions and parts of speech
     * @param word The Word represented by the Object
     * @param definition the definition of the word
     * @param partOfSpeech the part of speech for the word
     * if the definition already exists for the current word, an exception is thrown
     * @implSpec The valid parts of speech are:
     * <p>(Part of Speech - Valid Entry)<p/>
     * <p> Noun - noun</p>
     * <p> Verb - verb</p>
     * <p> Adjective - adj</p>
     * <p> Pronoun - pron</p>
     * <p> Preposition - prep</p>
     * <p> Conjugation - conj</p>
     * <p> Interjection - interj</p>
     * @implNote Time Complexity: O(N)
     */
    public DictionaryWord(String word, String partOfSpeech, String definition) {
        this.word = word;
        // Adds the definition and part of speech to the word
        definitions.add(new DictionaryDefinition(partOfSpeech, definition));
        usedPartsOfSpeech.add(partOfSpeech);
    }

    /**
     * Gets all parts of speech for a given word.
     *
     * @implNote O(1) running time behavior
     * @return String of all parts of speech for the associated word
     */
    public String getPartsOfSpeech() {
        StringBuilder output = new StringBuilder(word + ":" + NEWLINE + TAB);
        for (String partOfSpeech : usedPartsOfSpeech){
            output.append(partOfSpeech).append(NEWLINE).append(TAB);
        }
        return output.toString();
    }

    /**
     * Gets a numbered list of all definitions for a word
     *
     * @implNote O(definitions of word) running time behavior
     * @return the list of definitions in the form a string
     */
    public String getListOfDefinitions() {
        int counter = 1;
        StringBuilder sb  = new StringBuilder();
        for(DictionaryDefinition def : definitions){
            sb.append(counter).append(". ").append(def.toString()).append(NEWLINE);
            counter++;
        }
        sb.append(counter).append(". Back to main menu").append(NEWLINE);
        return sb.toString();
    }

    /**
     * Adds a definition to the word
     *
     * @implNote O(log(definitions)) running time
     * @param partOfSpeech the part of speech of the definition
     * @param definition the definition
     */
    public void addDefinition(String partOfSpeech, String definition) {
        definitions.add(new DictionaryDefinition(partOfSpeech, definition));
        usedPartsOfSpeech.add(partOfSpeech);
    }

    /**
     * Updates a definition of the word using the definition's index (starting from 1) when in sorted order
     *
     * @implNote O(definitions of word) running time behavior
     * @param id the index of the definition to update
     * @param newDefinition the new definition
     */
    public void updateDefinition(int id, String newDefinition) {
        int num = 1;
        for (DictionaryDefinition definition : definitions) {
            if (num == id) {
                // Removing and re-adding the definition is necessary to keep them sorted
                String partOfSpeech = definition.getPartOfSpeech();
                definitions.remove(definition);
                definitions.add(new DictionaryDefinition(partOfSpeech, newDefinition));
                break;
            }
            num++;
        }
    }

    /**
     * Returns the word as a string containing the word and its definitions
     *
     * @implNote O(definitions of word) running time behavior
     * @return the word as a string
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder(word);
        for (DictionaryDefinition definition : definitions) {
            output
                    .append(NEWLINE)
                    .append(TAB)
                    .append(definition);
        }
        return output.toString();
    }

    /**
     * Provides the file output format of the word and its definitions
     *
     * @implNote O(definitions of word) running time behavior
     * @return the string of the file output
     */
    public String toFileFormat(){
        StringBuilder sb  = new StringBuilder();
        for(DictionaryDefinition def : definitions){
            sb.append(this.word).append("::").append(def.toFileFormat()).append(NEWLINE);
        }
        return sb.toString();
    }

    /**
     * Provides the number of definitions for the associated word
     *
     * @implNote O(1) running time behavior
     * @return the number of words
     */
    public int numberOfDefinitions(){
         return definitions.size();
    }

    /**
     * Removes a definition from the word based on the passed index and returns the part of speech the definition used
     *
     * @implNote O(definitions of word) running time behavior
     * @param id the index of the definition to remove
     * @return the part of speech the definition used
     */
    public String removeDefinition(int id) {
        int num = 1;
        String partOfSpeech ="";
        DictionaryDefinition targetDefinition = null;
        for (DictionaryDefinition definition : definitions) {
            if (num == id) {
                targetDefinition = definition;
                partOfSpeech = definition.getPartOfSpeech();
                break;
            }
            num++;
        }
        // Removing the DictionaryDefinition from the set of definitions
        definitions.remove(targetDefinition);
        return partOfSpeech;
    }
}