package comprehensive;

import java.util.HashSet;
import java.util.List;

/**
 * A class that represents a DictionaryDefinition for a DictionaryWord that implements the comparable interface
 *
 * @author Logan Waller and James Crawford
 * @version December 5th, 2024
 */
public class DictionaryDefinition implements Comparable<DictionaryDefinition> {
    // Variables
    private String partOfSpeech;
    private String definition;
    // Constants
    private final HashSet<String> VALIDPARTSOFSPEECH = new HashSet<String>(List.of(new String[]{"noun", "verb", "adj", "adv", "pron", "prep", "conj", "interj"}));
    private final String TAB = "\t";

    /**
     * Creates a DictionaryDefinition Class
     *
     * @param definition The definition of the Dictionary Entry (Word)
     * @param partOfSpeech The Part of Speech of the entry (view Implementation Note for more details)
     * @implSpec The valid parts of speech are:
     * <p>(Part of Speech - Valid Entry)<p/>
     * <p> Noun - noun</p>
     * <p> Verb - verb</p>
     * <p> Adjective - adj</p>
     * <p> Pronoun - pron</p>
     * <p> Preposition - prep</p>
     * <p> Conjugation - conj</p>
     * <p> Interjection - interj</p>
     * @throws IllegalArgumentException If the part of speech input is not one of the below-listed Parts of Speech,
     * an exception is thrown
     */
    public DictionaryDefinition(String partOfSpeech, String definition) throws IllegalArgumentException{
        // Check to see of the user PartOfSpeech is valid
        if (!VALIDPARTSOFSPEECH.contains(partOfSpeech)) {
            throw new IllegalArgumentException("The Part of Speech passed is not a valid input. Please refer to the JavaDoc for proper implementation");
        }
        this.definition = definition;
        this.partOfSpeech = partOfSpeech;
    }

    /**
     * Gets the Part of Speech of the DictionaryDefinition
     *
     * @implNote O(1) running time behavior
     * @return The Part of Speech
     */
    public String getPartOfSpeech(){
        return this.partOfSpeech;
    }

    /**
     * Gets the definition of the DictionaryDefinition
     *
     * @implNote O(1) running time behavior
     * @return The definition
     */
    public String getDefinition(){
        return this.definition;
    }

    /**
     * Sets the definition to the given string
     *
     * @implNote O(1) running time behavior
     * @param definition the string to set the definition to
     */
    public void setDefinition(String definition) {
        this.definition = definition;
    }

    /**
     * Compares this object with the other object based on the lexicographic sorting of their parts of speech
     * The lexicographic ordering of their definitions are used as a tiebreaker
     *
     * @implNote O(1) running time behavior
     * @param other the object to be compared.
     * @return an int representing the result of the comparison
     */
    public int compareTo(DictionaryDefinition other) {
        if (partOfSpeech.compareTo(other.partOfSpeech) != 0) {
            // Parts of speech are different, compare parts of speech
            return partOfSpeech.compareTo(other.partOfSpeech);
        } else {
            // Parts of speech are the same, compare definitions
            return definition.compareTo(other.definition);
        }
    }

    /**
     * Returns the definition as a string containing its part of speech and definition
     *
     * @implNote O(1) running time behavior
     * @return returns the definition as a string
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder(partOfSpeech)
                .append(".")
                .append(TAB)
                .append(definition);
        return output.toString();
    }

    /**
     * Provides the definition and part of speech in file output form
     *
     * @implNote O(1) running time behavior
     * @return string of the file output form
     */
    public String toFileFormat(){
        return this.partOfSpeech + "::" + this.definition;

    }
}

