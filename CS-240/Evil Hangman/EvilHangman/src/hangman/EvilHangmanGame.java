package hangman;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.SortedSet;
import java.util.*;

public class EvilHangmanGame implements IEvilHangmanGame {
    private SortedSet<Character> guessedLetters = new TreeSet<Character>();
    private String partiallyGuessedWord = "";
    private SortedSet<String> possibilities = new TreeSet<String>();
    private Map<String, TreeSet<String>> words = new TreeMap<String, TreeSet<String>>();

    public String returnGuessedWord(){
        return partiallyGuessedWord;
    }
    public int numRemainingPossibilities(){
        return possibilities.size();
    }
    public String returnActualWord(){
        return possibilities.first();
    }
    public Set<String> returnPossibilities(){
        return possibilities;
    }
    @Override
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
        possibilities = new TreeSet<String>();
        Scanner scanner = new Scanner(dictionary);
        scanner.useDelimiter("((#[^\\n]*\\n)|(\\s+))+");

        while(scanner.hasNext()){
            String str = scanner.next();
            str = str.toLowerCase();
            if (str.length() == wordLength) possibilities.add(str);
        }
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < wordLength; i++) str.append("-");
        partiallyGuessedWord = str.toString();
        if (possibilities.size()  == 0) throw new EmptyDictionaryException();
    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        String str = "";
        str += guess;
        str = str.toLowerCase();
        guess = str.charAt(0);
        if (guess  < 'a') throw new GuessAlreadyMadeException();
        if (guess  > 'z') throw new GuessAlreadyMadeException();
        if (guessedLetters.contains(guess)) throw new GuessAlreadyMadeException();
        guessedLetters.add(guess);
        words.clear();
        for (String word : possibilities){
            StringBuilder possibleString = new StringBuilder("");
            for (int i = 0; i < word.length(); i++){
                if (word.charAt(i) == guess) possibleString.append(guess);
                else if (partiallyGuessedWord.charAt(i) != '-') possibleString.append(partiallyGuessedWord.charAt(i));
                else possibleString.append('-');
            }
            String pattern = possibleString.toString();
            if (words.containsKey(pattern))words.get(pattern).add(word);
            else{
                TreeSet<String> currentPattern = new TreeSet<String>();
                currentPattern.add(word);
                words.put(pattern, currentPattern);
            }
        }
        String key = evil(guess);
        possibilities = words.get(key);
        String newSet = possibilities.first();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < newSet.length(); i++){
            if (partiallyGuessedWord.charAt(i) != '-') builder.append(partiallyGuessedWord.charAt(i));
            else if (newSet.charAt(i) == guess) builder.append(guess);
            else builder.append('-');
        }
        partiallyGuessedWord = builder.toString();
        return possibilities;
    }
    public String evil(Character guess){
        String key = "";
        int largestSetSize = 0;
        for (Map.Entry<String, TreeSet<String>> group : words.entrySet()){
            if (largestSetSize < group.getValue().size()) largestSetSize =  group.getValue().size();
        }
        TreeSet<String> toRemove = new TreeSet<String>();
        for (Map.Entry<String, TreeSet<String>> group : words.entrySet()){
            if (group.getValue().size() != largestSetSize) toRemove.add(group.getKey());
        }
        words.keySet().removeAll(toRemove);
        if (words.size() > 1){
            int leastOccurrences = partiallyGuessedWord.length();
            for (Map.Entry<String, TreeSet<String>> group : words.entrySet()){
                int frequency = 0;
                for (int i = 0; i < group.getKey().length(); i++) {
                    if (group.getKey().charAt(i) == guess) frequency++;
                }
                if (frequency < leastOccurrences) leastOccurrences = frequency;
            }
            TreeSet<String> notBigNuff = new TreeSet<String>();
            for (Map.Entry<String, TreeSet<String>> group : words.entrySet()){
                int numOccurrences = 0;
                for (int i = 0; i < group.getKey().length(); i++) {
                    if (group.getKey().charAt(i) == guess) numOccurrences++;
                }
                if (numOccurrences != leastOccurrences) notBigNuff.add(group.getKey());
            }
            words.keySet().removeAll(notBigNuff);
            if (words.size() > 1){
                String compare = "~";
                for (Map.Entry<String, TreeSet<String>> group : words.entrySet()){
                    String thisKey = group.getKey();
                    if (thisKey.compareTo(compare) < 0) compare = thisKey;
                }
                TreeSet<String> notEqual = new TreeSet<String>();
                for (Map.Entry<String, TreeSet<String>> group : words.entrySet()){
                    if (!group.getKey().equals(compare)) notEqual.add(group.getKey());
                }
                words.keySet().removeAll(notEqual);
            }

        }
        for (String str : words.keySet()){
            key = str;
        }
        return key;
    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        if (guessedLetters.size() == 0) return null;
        else return guessedLetters;
    }
}
