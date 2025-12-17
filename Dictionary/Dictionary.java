package comprehensive;

import java.text.DecimalFormat;
import java.util.*;

/**
 * A class that represents a Dictionary of words
 *
 * @author Logan Waller and James Crawford
 * @version December 5th, 2024
 */
public class Dictionary {
    // Variables
    private int numWords;
    private int numDefinitions;
    private HashMap<String, DictionaryWord> wordMap;
    private TreeSet<String> words;
    private HashMap<String, Integer> usedPartsOfSpeech;
    // Constants
    final private String NEWLINE = System.lineSeparator();
    final private String TAB = "\t";

    /**
     * A constructor for the dictionary class
     */
    public Dictionary() {
        wordMap = new HashMap<String, DictionaryWord>();
        words = new TreeSet<String>();
        usedPartsOfSpeech = new HashMap<String, Integer>();
        numWords = 0;
        numDefinitions = 0;
    }

    /**
     * Adds a word or definition to the dictionary
     *
     * @implNote O(log(words)) running time behavior if adding a new word
     * @implNote O(log(definitions of the word)) running time behavior if adding a new definition to an existing word
     * @param word The word
     * @param partOfSpeech the part of speech for the associated word
     * @param definition the associated definition for the associated part of speech
     */
    public void add(String word, String partOfSpeech, String definition) {
        // If the word isn't already in the map (or if the map is empty) add it
        if (wordMap.isEmpty() || !(wordMap.containsKey(word))) {
            wordMap.put(word, new DictionaryWord(word, partOfSpeech, definition));
            words.add(word);
            numWords++;
        } else {
            // If the word is already in the map add a new definition
            wordMap.get(word).addDefinition(partOfSpeech, definition);
        }
        // Mark the part of speech as used, if it's been used before increment the number of definitions used by it
        if (usedPartsOfSpeech.containsKey(partOfSpeech)) {
            usedPartsOfSpeech.put(partOfSpeech, usedPartsOfSpeech.get(partOfSpeech) + 1);
        } else {
            usedPartsOfSpeech.put(partOfSpeech, 1);
        }
        numDefinitions++;
    }

    /**
     * Reports these statistics for the glossary: the total number of words, the total number of definitions,
     * the average number of definitions per word (with three digits to the right of the decimal point),
     * the number of parts of speech used, the first word (if ordered lexicographically),
     * the last word (if ordered lexicographically)
     *
     * @implNote O(log(words)) running time behavior
     * @return the metadata
     */
    public String getStatistics(){
        if(numWords == 0){ // For when the dictionary is empty
            String output = "words: " + "0" + NEWLINE +
                    "definitions: " + "0" + NEWLINE +
                    "definitions per word: " + "0.00" + NEWLINE +
                    "parts of speech: " + "0" + NEWLINE +
                    "first word: " + "" + NEWLINE +
                    "last word: "  + "";
            return output;
        } else {
            double avg = (double) numDefinitions / numWords;
            DecimalFormat doubleFormat = new DecimalFormat("#.000");
            String output = "words: " + numWords + NEWLINE +
                    "definitions: " + numDefinitions + NEWLINE +
                    "definitions per word: " + doubleFormat.format(avg) + NEWLINE + //limits the double to three decimals
                    "parts of speech: " + usedPartsOfSpeech.size() + NEWLINE +
                    "first word: " + words.first() + NEWLINE +
                    "last word: " + words.last();
            return output;
        }
    }

    /**
     * Returns all the words in the given range as a string
     *
     * @implNote O(words in range) running time behavior (Creating a submap is O(1), iterating is O(length))
     * @param start the starting word (inclusive)
     * @param end the ending word (inclusive)
     * @return all the words in the range as a string
     */
    public String getWordsInRange(String start, String end) {
        StringBuilder sb = new StringBuilder("The words between ")
                .append(start)
                .append(" and ")
                .append(end)
                .append(" are:");
        try {
            for (String word : words.subSet(start, true, end, true)) {
                sb
                        .append(NEWLINE)
                        .append(TAB)
                        .append(word);
            }
        } catch (IllegalArgumentException ignored) {}
        return sb.toString();
    }

    /**
     * Returns the requested word as a string or null if the word isn't in the dictionary
     *
     * @implNote O(definitions of the word) running time behavior
     * @param word the word to return a string for
     * @return the string format of the word or null
     */
    public String getWord(String word) {
        DictionaryWord wordObject = wordMap.get(word);
        if (wordObject == null) {
            return null;
        }
        return wordObject.toString();
    }

    /**
     * Provides the first word in the dictionary.
     *
     * @implNote O(log(words) + definitions of the word) running time behavior
     * @return the word
     */
    public String getFirstWord(){
        if (!words.isEmpty()) return getWord(words.first());
        return "null";
    }

    /**
     * Provides the last word in the dictionary.
     *
     * @implNote O(log(words) + definitions of the word) running time behavior
     * @return the word
     */
    public String getLastWord(){
        if (!words.isEmpty()) return getWord(words.last());
        return "null";
    }

    /**
     * Gets the DictionaryWord class associated with the user-defined word
     *
     * @implNote O(1) running time behavior
     * @param word the word associated with the DictionaryClass of interest
     * @return the DictionaryClass
     */
    public String getPartsOfSpeechOfWord(String word) {
        return wordMap.get(word).getPartsOfSpeech();
    }

    /**
     * Returns a numbered list of the definitions of a word as a string
     *
     * @implNote O(definitions of word) running time behavior
     * @param word the word to get the definitions for
     * @return a numbered list of the definitions of a word as a string
     */
    public String getDefinitions(String word){
        return wordMap.get(word).getListOfDefinitions();
    }

    /**
     * Gets the number of definitions for a word
     *
     * @implNote O(1) running time behavior
     * @param word the word to get the number of definitions for
     * @return the number of definitions of the word
     */
    public int getNumDefinitions(String word) {
        return wordMap.get(word).numberOfDefinitions();
    }

    /**
     * Updates the definition for a word
     *
     * @implNote O(definitions for word) running time behavior
     * @param word the word to update a definition for
     * @param definitionIndex the index of the definition to update
     * @param newDefinition the new definition for the word
     */
    public void updateDefinition(String word, int definitionIndex, String newDefinition){
        wordMap.get(word).updateDefinition(definitionIndex, newDefinition);
    }

    /**
     * Represents the dictionary as a string
     *
     * @implNote O(definitions) running time behavior (definitions must be >= words)
     * @return the dictionary represented as a string
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(wordMap.get(word).toString());
            sb.append(NEWLINE);
        }
        // Removes the extra newline character
        sb.delete(sb.length()-1, sb.length());
        return sb.toString();
    }

    /**
     * Provides the file output format of the dictionary
     * @implNote O(definitions) running time behavior (definitions must be >= words)
     * @return the dictionary in file format
     */
    public String toFileFormat(){
        StringBuilder fileFormatString = new StringBuilder();
        for(String word : words) {
            fileFormatString.append(wordMap.get(word).toFileFormat());
        }
        // Substring method removes the newline character
        return fileFormatString.substring(0, fileFormatString.length()-2);
    }

    /**
     * Removes a definition from the specified word based on the index passed to it
     *
     * @implNote O(definitions of word) if just removing a definition
     * @implNote O(log(words)) if removing the word and definition
     * @param userWord the word to remove a definition from
     * @param id the index of the definition to remove
     * @return A boolean that represents whether the word was also removed with the definition (true if removed)
     */
    public boolean removeDefinition(String userWord, int id){
        String partOfSpeech = wordMap.get(userWord).removeDefinition(id);
        numDefinitions--;
        boolean wordRemoved = false;
        usedPartsOfSpeech.put(partOfSpeech, usedPartsOfSpeech.get(partOfSpeech) - 1);
        if(usedPartsOfSpeech.get(partOfSpeech) == 0){
            usedPartsOfSpeech.remove(partOfSpeech);
        }
        if(wordMap.get(userWord).numberOfDefinitions() == 0){
            wordMap.remove(userWord);
            words.remove(userWord);
            numWords--;
            wordRemoved = true;
        }
        return wordRemoved;
    }

}
