package spell;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector{
    private Trie dictionary;
    /**
     * Tells this <code>SpellCorrector</code> to use the given file as its dictionary
     * for generating suggestions.
     * @pre SpellCorrector will have had empty-param constructor called, but dictionary has nothing in it.
     * @param dictionaryFileName the file containing the words to be used
     * @throws IOException If the file cannot be read
     * @post SpellCorrector will have dictionary filled and be ready to suggestSimilarWord any number of times.
     */
    public void useDictionary(String dictionaryFileName) throws IOException{
        Path filePath = Paths.get (dictionaryFileName);
        InputStream in = Files.newInputStream(filePath);
        Scanner scanner = new Scanner(in);
        scanner.useDelimiter("((#[^\\n]*\\n)|(\\s+))+");

        while(scanner.hasNext()) {
            String str = scanner.next();
            str = str.toLowerCase();
            dictionary.add(str);
        }

    }

    /**
     * Suggest a word from the dictionary that most closely matches
     * <code>inputWord</code>.
     * @param inputWord the word we are trying to find or find a suggestion for
     * @return the suggestion or null if there is no similar word in the dictionary
     */
    public String suggestSimilarWord(String inputWord){
        if (dictionary.find(inputWord) != null){
            return inputWord;
        }
        //continue code from here for the 1 away case, then for the 2 away case.
        return null;
    }
}
