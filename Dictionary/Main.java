package comprehensive;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

/**
 * The main class that runs the dictionary program and handles the GUI
 *
 * @author Logan Waller and James Crawford
 * @version December 5th, 2024
 */
public class Main {
    // Variables
    private static Dictionary dictionary;
    // Constants
    final private static String NEWLINE = System.lineSeparator();

    /**
     * The main method that runs the program
     *
     * @param args the file path of the dictionary to open
     */
    public static void main(String[] args) {
        // Create the dictionary
        boolean running;
        try {
            running = createDictionaryFromFile(args[0]);
        } catch (ArrayIndexOutOfBoundsException ignore) {
            System.out.println("No file passed");
            running = false;
        }
        // Main logic loop
        while (running) {
            showMainMenu();
            // Gather valid input
            int input = 0;
            boolean validInput = true;
            try {
                input = Integer.parseInt(scan()); // NumberFormatException is thrown if the input isn't an int
            } catch (NumberFormatException e) {
                validInput = false;
            }
            if(input > 11 || input < 1){
                validInput = false;
            }
            if(!validInput){
                System.out.println(NEWLINE + "Invalid Selection" + NEWLINE + NEWLINE);
            }
            // Act on input
            switch (input) {
                case 1: // Get metadata - O(log(words)) running time behavior
                    System.out.println(NEWLINE + dictionary.getStatistics() + NEWLINE);
                    break;
                case 2: // Get words in range - O(words in range) running time behavior
                    getWordsInRange();
                    break;
                case 3: // Get word - O(definitions of the word) running time behavior
                    getWord();
                    break;
                case 4: // Get first word - O(log(words) + definitions of the word) running time behavior
                    System.out.println(NEWLINE + dictionary.getFirstWord() + NEWLINE);
                    break;
                case 5: // Get last word - O(log(words) + definitions of the word) running time behavior
                    System.out.println(NEWLINE + dictionary.getLastWord() + NEWLINE);
                    break;
                case 6: // Get parts of speech - O(1) running time behavior
                    getPartsOfSpeech();
                    break;
                case 7: // Update definition - O(definitions of word) running time behavior
                    updateDefinition();
                    break;
                case 8: //Delete definition - O(definitions of word) if removing a definition, O(log(words)) if removing a word
                    removeDefinition();
                    break;
                case 9: // Add definition - O(log(definitions of word)) if adding a definition, O(log(words)) if adding a word
                    addDefinition();
                    break;
                case 10: //Save to file - O(definitions) running time behavior (words < definitions)
                    saveDictionary();
                    break;
                case 11: // Exit - O(1) running time behavior
                    running = false;
                    break;
            }
        }
    }

    /**
     * Presents the main menu to the user
     */
    private static void showMainMenu(){
        System.out.print("Main Menu" + NEWLINE +
                "1.  Get metadata" + NEWLINE +
                "2.  Get Words In Range" + NEWLINE +
                "3.  Get word" + NEWLINE +
                "4.  Get first word"  + NEWLINE +
                "5.  Get last word" + NEWLINE +
                "6.  Get parts of speech" + NEWLINE +
                "7.  Update definition" + NEWLINE +
                "8.  Delete definition"  + NEWLINE +
                "9.  Add new definition" + NEWLINE +
                "10. Save dictionary" + NEWLINE +
                "11. Exit"  + NEWLINE + NEWLINE +
                "Select an option: "
        );
    }

    /**
     * A private helper method for when the user selects the "Get words in range" option
     *
     * @implNote O(words in range) running time behavior
     */
    private static void getWordsInRange() {
        System.out.print("Starting word: ");
        String start = scan();
        System.out.print("Ending word: ");
        String end = scan();
        System.out.println();
        System.out.println(dictionary.getWordsInRange(start, end));
        System.out.println();
    }

    /**
     * Gets all entries for a given word, showing each definition and corresponding part of speech.
     *
     * @implNote (definitions of word) running time behavior
     */
    private static void getWord(){
        System.out.print("Select a word: ");
        String word = scan();
        String output = dictionary.getWord(word);
        System.out.println();
        if (output == null) {
            System.out.print(word);
            System.out.println(" not found\n");
        } else {
            System.out.println(output);
            System.out.println();
        }
    }

    /**
     * Gets all parts of speech for a given word.
     *
     * @implNote O(1) running time behavior
     */
    private static void getPartsOfSpeech(){
        System.out.print("Select a word: ");
        //create a scanner for the user to input the word to
        String wordPartOfSpeechScannerResult = scan();
        if (dictionary.getWord(wordPartOfSpeechScannerResult) == null)
            //if the word is not in the dictionary
            System.out.println("\nThe word entered is not in the dictionary\n");
        else {
            //if the word is in the dictionary
            System.out.println();
            System.out.println(dictionary.getPartsOfSpeechOfWord(wordPartOfSpeechScannerResult));
        }
    }

    /**
     * Private helper methods that updates the definition
     *
     * @implNote O(definitions of word) running time behavior
     */
    private static void updateDefinition() {
        //Get the word to update def
        boolean validWord = false;
        String userWord = null;
        System.out.print("Select a word: ");
        userWord = scan();
        if(dictionary.getWord(userWord) == null){
            //Check to see if the word is in the dictionary
            System.out.println("Invalid selection\n");
        } else {
            System.out.println();
            int numDefinitions = dictionary.getNumDefinitions(userWord);
            boolean validUserInput = false;
            while(!validUserInput){
                System.out.println("Definitions for " + userWord);
                System.out.println(dictionary.getDefinitions(userWord));
                System.out.print("Select a definition to update: ");
                //int defToUpdate = Integer.parseInt(scan());
                int defToUpdate = getNumberInRange(0, numDefinitions + 1);
                if(defToUpdate <= 0 || defToUpdate > numDefinitions + 1){
                    System.out.println("Invalid selection" + NEWLINE);
                }
                if(defToUpdate <= numDefinitions && defToUpdate > 0){
                    //If the user input is valid
                    validUserInput = true;
                    System.out.print("Type a new definition: ");
                    String newDef = scan();
                    if (!(newDef.length() == 0 || newDef.contains("::"))) {
                        System.out.println();
                        dictionary.updateDefinition(userWord, defToUpdate, newDef);
                        System.out.println("Definition Updated" + NEWLINE);
                    } else {
                        System.out.println("\nInvalid definition\n");
                    }
                    break;
                } else if (defToUpdate == numDefinitions + 1) {
                    //If the user chooses to return the main menu
                    System.out.println();
                    break;
                }
            }
        }
    }

    /**
     * Private helper method: Removes the definition from the current DictionaryWord
     *
     * @implNote O(definitions of word) if removing a definition
     * @implNote O(log(words)) if removing a word
     */
    private static void removeDefinition(){
        //Get the word to update def
        boolean validWord = false;
        String userWord = null;
        System.out.print("Select a word: ");
        userWord = scan();
        if(dictionary.getWord(userWord) == null){
            //Check to see if the word is in the dictionary
            System.out.println("Invalid selection\n");
        } else {
            System.out.println();
            int numDefinitions = dictionary.getNumDefinitions(userWord);
            boolean validUserInput = false;
            while(!validUserInput){
                System.out.println("Definitions for " + userWord);
                System.out.println(dictionary.getDefinitions(userWord));
                System.out.print("Select a definition to Remove: ");
                //int defToUpdate = Integer.parseInt(scan());
                int defToRemove = getNumberInRange(0, numDefinitions + 1);
                if(defToRemove <= 0 || defToRemove > numDefinitions + 1){
                    System.out.println("Invalid selection" + NEWLINE);
                }
                if(defToRemove <= numDefinitions && defToRemove > 0){
                    //If the user input is valid
                    validUserInput = true;
                    dictionary.removeDefinition(userWord, defToRemove);
                    System.out.println("Definition Removed" + NEWLINE);
                    break;
                } else if (defToRemove == numDefinitions + 1) {
                    //If the user chooses to return the main menu
                    System.out.println();
                    break;
                }
            }
        }
    }

    /**
     * Adds a new definition for a given word. If the word is not already in the dictionary, it adds the word to the
     * dictionary
     *
     * @implNote O(log(definitions of word)) if adding a definition
     * @implNote O(log(words)) if adding a word
     */
    private static void addDefinition(){
        System.out.print("Type a word: ");
        String addDefWord = scan();
        if (!(addDefWord.length() == 0 || addDefWord.contains("::"))) { // Check that the word is a valid word
            System.out.println("Valid parts of speech: [noun, verb, adj, adv, pron, prep, conj, interj]");
            boolean validPartOfSpeech = false;
            String addDefPartOfSpeech = "";
            while(!validPartOfSpeech) {
                System.out.print("Type a valid part of speech: ");
                addDefPartOfSpeech = scan();
                if(isValidPartOfSpeech(addDefPartOfSpeech)){
                    validPartOfSpeech = true;
                }
            }
            System.out.print("Type a definition: ");
            String addDefnewDef = scan();
            if (!(addDefnewDef.length() == 0 || addDefnewDef.contains("::"))) {
                dictionary.add(addDefWord, addDefPartOfSpeech, addDefnewDef);
                System.out.println(NEWLINE + "Successfully added!\n");
            } else {
                System.out.println("\nInvalid definition\n");
            }
        } else {
            System.out.println("\nInvalid word\n");
        }
    }

    /**
     * Private helper methods that determines if a string a valid part of speech
     *
     * @param partOfSpeech the string
     * @return boolean if the string is a part of speech
     */
    private static boolean isValidPartOfSpeech(String partOfSpeech) {
        final HashSet<String> PARTSOFSPEECH = new HashSet<String>(List.of(new String[]{"noun", "verb", "adj", "adv", "pron", "prep", "conj", "interj"}));
        return PARTSOFSPEECH.contains(partOfSpeech);
    }

    /**
     * Saves the dictionary to a file
     *
     * @implNote O(definitions) running time behavior (definitions must be >= words)
     */
    private static void saveDictionary(){
        System.out.print("Type a filename with path: ");
        String filePath = scan();
        boolean saved = true;
        try(PrintWriter outputFile = new PrintWriter(filePath)) {
            outputFile.print(dictionary.toFileFormat());
        }
        catch (IOException e) {
            System.out.println(NEWLINE + "File Not Found" + NEWLINE);
            saved = false;
        }
        if(saved){
            System.out.println(NEWLINE + "Successfully saved dictionary to " + filePath + NEWLINE);
        }
    }

    /**
     * Private helper method to create a dictionary object from a given file
     *
     * @implNote O(definitions) running time behavior
     * @param fileName the input file
     */
    private static boolean createDictionaryFromFile(String fileName) {
        dictionary = new Dictionary();
        //Note: While trying to efficiently input data from the file, we found the BufferedReader Class. It has decent
        // efficiency gains over using the scanner to read in the file.
        try(BufferedReader reader = new BufferedReader((new FileReader(fileName)))){
            String wordLine;
            while((wordLine = reader.readLine()) != null){
                //For all the lines with text in them
                String[] definitionParts = wordLine.split("::");
                dictionary.add(definitionParts[0], definitionParts[1], definitionParts[2]);
            }
            return true;
        } catch (IOException e) {
            System.out.print("IO Exception: " + e);
            return false;
        }
    }

    /**
     * Private helper method that scans the next line for user input
     *
     * @return the user input as a String
     */
    private static String scan() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     * Private Helper Method that determines if the number given by the user is withing the valid range
     * @param low minimum valid input (inclusive)
     * @param high maximum valid input (inclusive)
     * @return negative int if user-provided number is invalid, the number of the user-provided number is valid
     */
    private static int getNumberInRange(int low, int high){
        int userInput;
        try{
            userInput = Integer.parseInt(scan());
        } catch(NumberFormatException e) {
            return -1;
        }
        if(userInput >= low && userInput <= high) {
            return userInput;
        } else{
            return -1;
        }
    }
}

