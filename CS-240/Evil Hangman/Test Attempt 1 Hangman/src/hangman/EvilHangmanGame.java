package hangman;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.SortedSet;
import java.util.*;

public class EvilHangmanGame implements IEvilHangmanGame {
    private TreeSet<String> allWords = new TreeSet<String>();
    private TreeSet<Character> guessedLetters = new TreeSet<Character>();
    private String partiallyGuessedWord = "";
    private Map<String, TreeSet<String>> devider = new TreeMap<String, TreeSet<String>>();
    @Override
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
        allWords = new TreeSet<String>();
        Scanner scanner = new Scanner(dictionary);
        scanner.useDelimiter("((#[^\\n]*\\n)|(\\s+))+");
        while (scanner.hasNext()){
            String str = scanner.next();
            str = str.toLowerCase();
            if (str.length() == wordLength) allWords.add(str);
        }
        if (allWords.size() == 0) throw new EmptyDictionaryException();
        StringBuilder str = new StringBuilder("");
        for (int i = 0; i < wordLength; i++){
            str.append('-');
        }
        partiallyGuessedWord = str.toString();
    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        StringBuilder changeToLower = new StringBuilder("");
        changeToLower.append(guess);
        String toUpper = changeToLower.toString();
        toUpper = toUpper.toLowerCase();
        guess = toUpper.charAt(0);
        int previousSize = guessedLetters.size();
        guessedLetters.add(guess);
        devider.clear();
        if (previousSize == guessedLetters.size()) throw new GuessAlreadyMadeException();
        TreeSet<String> helper = new TreeSet<String>();
        String actualKey = "";
        for (String word : allWords){
            helper.clear();
            helper.add(word);
            StringBuilder key = new StringBuilder("");
            for (int i = 0; i < word.length(); i++){
                if (word.charAt(i) == partiallyGuessedWord.charAt(i)) key.append(word.charAt(i));
                else if (word.charAt(i) == guess) key.append(guess);
                else key.append("-");
            }
            actualKey = key.toString();
            if (devider.containsKey(actualKey)) devider.get(actualKey).add(word);
            else{
                TreeSet<String> currentPattern = new TreeSet<String>();
                currentPattern.add(word);
                devider.put(actualKey, currentPattern);
            }
        }
        String prevKey = "";
        int largestSetSize = 0;
        for (Map.Entry<String, TreeSet<String>> group : devider.entrySet()){
            if (largestSetSize < group.getValue().size()){
                largestSetSize =  group.getValue().size();
                prevKey = group.getKey();
            }
        }
        TreeSet<String> toRemove = new TreeSet<String>();
        for (Map.Entry<String, TreeSet<String>> group : devider.entrySet()){
            if (group.getValue().size() != largestSetSize) toRemove.add(group.getKey());
        }
        devider.keySet().removeAll(toRemove);
        if (devider.size() == 1){
            allWords = devider.get(prevKey);
            partiallyGuessedWord = prevKey;
        }
        if (devider.size() > 1){
            int leastOccurrences = partiallyGuessedWord.length();
            for (Map.Entry<String, TreeSet<String>> group : devider.entrySet()){
                int frequency = 0;
                for (int i = 0; i < group.getKey().length(); i++) {
                    if (group.getKey().charAt(i) == guess) frequency++;
                }
                if (frequency < leastOccurrences){
                    leastOccurrences = frequency;
                    partiallyGuessedWord = group.getKey();
                }
            }
            TreeSet<String> notBigNuff = new TreeSet<String>();
            for (Map.Entry<String, TreeSet<String>> group : devider.entrySet()){
                int numOccurrences = 0;
                for (int i = 0; i < group.getKey().length(); i++) {
                    if (group.getKey().charAt(i) == guess) numOccurrences++;
                }
                if (numOccurrences != leastOccurrences) notBigNuff.add(group.getKey());
            }
            devider.keySet().removeAll(notBigNuff);
            if (devider.size() > 1){
                String compare = "~";
                for (Map.Entry<String, TreeSet<String>> group : devider.entrySet()){
                    String thisKey = group.getKey();
                    if (thisKey.compareTo(compare) < 0) compare = thisKey;
                }
                TreeSet<String> notEqual = new TreeSet<String>();
                for (Map.Entry<String, TreeSet<String>> group : devider.entrySet()){
                    if (!group.getKey().equals(compare)) notEqual.add(group.getKey());
                }
                devider.keySet().removeAll(notEqual);
                partiallyGuessedWord = compare;
            }

        }
        allWords = devider.get(partiallyGuessedWord);
        return allWords;
    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        return guessedLetters;
    }
}
