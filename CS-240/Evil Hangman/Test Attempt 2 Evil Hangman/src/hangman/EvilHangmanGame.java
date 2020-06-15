package hangman;

import com.sun.source.tree.Tree;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;
import java.util.*;



public class EvilHangmanGame implements IEvilHangmanGame {
    private TreeSet<String> suggestions = new TreeSet<String>();
    private TreeSet<Character> guessedLetters = new TreeSet<Character>();
    private String partiallyGuessedWord = "";
    private boolean letterWasFound = false;
    private int timesFound = 0;

    @Override
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
        suggestions = new TreeSet<String>();
        guessedLetters = new TreeSet<Character>();
        partiallyGuessedWord = "";
        Scanner scanner = new Scanner(dictionary);
        scanner.useDelimiter("((#[^\\n]*\\n)|(\\s+))+");
        while (scanner.hasNext()){
            String word = scanner.next();
            if (word.length() == wordLength) suggestions.add(word);
        }
        if (suggestions.size() == 0) throw new EmptyDictionaryException();
        StringBuilder str = new StringBuilder();
        for ( int i = 0; i < wordLength; i++){
            str.append('-');
        }
        partiallyGuessedWord = str.toString();
    }
    public String returnPartiallyGuessedWord(){
        return partiallyGuessedWord;
    }
    public TreeSet<String> returnSuggestions(){
        return suggestions;
    }
    public boolean wasFound(){
        return letterWasFound;
    }
    public int timesWasFound(){
        return timesFound;
    }
    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        letterWasFound = false;
        timesFound = 0;
        String toLower = "";
        toLower += guess;
        toLower = toLower.toLowerCase();
        guess = toLower.charAt(0);
        if (guessedLetters.contains(guess)) throw new GuessAlreadyMadeException();
        else guessedLetters.add(guess);
        Map<String, TreeSet<String>> allWords = new TreeMap<String, TreeSet<String>>();
        for(String word : suggestions){
            StringBuilder key = new StringBuilder();
            for (int i = 0; i < word.length(); i++){
                if (word.charAt(i) == guess) key.append(guess);
                else if (partiallyGuessedWord.charAt(i) != '-') key.append(word.charAt(i));
                else key.append('-');
            }
            String actualKey = key.toString();
            if (allWords.containsKey(actualKey)) allWords.get(actualKey).add(word);
            else{
                TreeSet<String> helper = new TreeSet<String>();
                helper.add(word);
                allWords.put(actualKey, helper);
            }
        }
        Set<Map.Entry<String, TreeSet<String>>> entries = allWords.entrySet();
        TreeSet<String> biggestSets = new TreeSet<>();
        int largestSetSize = 0;
        for (Map.Entry<String, TreeSet<String>> entry : entries){
            if (entry.getValue().size() > largestSetSize){
                largestSetSize = entry.getValue().size();
                biggestSets.clear();
                biggestSets.add(entry.getKey());
            }
            else if (entry.getValue().size() == largestSetSize){
                biggestSets.add(entry.getKey());
                partiallyGuessedWord = entry.getKey();
            }
        }
        if (biggestSets.size() == 1){
            suggestions = allWords.get(biggestSets.first());
            partiallyGuessedWord = biggestSets.first();
        }
        else{
            int numOccurrences = biggestSets.first().length();
            for (String thisKey : biggestSets){
                int thisOccurrences = 0;
                for (int i = 0; i < thisKey.length(); i++){
                    if (thisKey.charAt(i) == guess) thisOccurrences++;
                }
                if (thisOccurrences < numOccurrences) numOccurrences = thisOccurrences;
            }
            TreeSet<String> toRemove = new TreeSet<>();
            TreeSet<String> toKeep = new TreeSet<>();
            for (String thisKey : biggestSets){
                int thisOccurrences = 0;
                for (int i = 0; i < thisKey.length(); i++){
                    if (thisKey.charAt(i) == guess) thisOccurrences++;
                }
                if (thisOccurrences != numOccurrences) {
                    toRemove.add(thisKey);
                    allWords.remove(thisKey);
                    partiallyGuessedWord = thisKey;
                }
                else toKeep.add(thisKey);

            }
            if(toKeep.size() == 1){
                suggestions = allWords.get(toKeep.first());
                partiallyGuessedWord = toKeep.first();
            }

            else{
                int rightmostInstance = -1;
                for (String key : allWords.keySet()){
                    int myRightmost = 0;
                    for (int i = 0; i < key.length(); i++){
                        if (key.charAt(i) == guess) myRightmost = i;
                    }
                    if (myRightmost > rightmostInstance) rightmostInstance = myRightmost;
                }
                Set<String> allKeys = new TreeSet<>();
                for (String key : allWords.keySet()){
                    allKeys.add(key);
                }
                for (String key : allKeys){
                    int myRightmost = 0;
                    for (int i = 0; i < key.length(); i++){
                        if (key.charAt(i) == guess) myRightmost = i;
                    }
                    if (myRightmost != rightmostInstance) allWords.remove(key);
                }
                allKeys.clear();
                while (allWords.size() > 1){
                    int newRightmost = -1;
                    for (String key : allWords.keySet()){
                        int myRightmost = 0;
                        for (int i = 0; i < rightmostInstance; i++){
                            if (key.charAt(i) == guess) myRightmost = i;
                        }
                        if (myRightmost > newRightmost) newRightmost = myRightmost;
                    }
                    for (String key : allWords.keySet()){
                        allKeys.add(key);
                    }
                    for (String key : allKeys){
                        int myRightmost = 0;
                        for (int i = 0; i < rightmostInstance; i++){
                            if (key.charAt(i) == guess) myRightmost = i;
                        }
                        if (myRightmost != newRightmost) allWords.remove(key);
                    }
                    rightmostInstance = newRightmost;
                }
                String actualKey = "";
                for (String key : allWords.keySet()){
                    actualKey = key;
                }
                partiallyGuessedWord = actualKey;
                suggestions = allWords.get(actualKey);
            }
        }
        for (int i = 0; i < suggestions.first().length(); i++){
            if (suggestions.first().charAt(i) == guess){
                letterWasFound = true;
                timesFound++;
            }
        }

        return suggestions;
    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        return guessedLetters;
    }
}
