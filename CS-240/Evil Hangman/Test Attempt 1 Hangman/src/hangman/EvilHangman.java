package hangman;

import java.io.File;
import java.util.Scanner;

public class EvilHangman {

    public static void main(String[] args) {
        String dictionaryName = "";
        int wordLength = 0;
        int numGuesses = 0;
        if (args.length == 3) {
            dictionaryName = args[0];
            wordLength  = Integer.parseInt(args[1]);
            numGuesses = Integer.parseInt(args[2]);
        }
        EvilHangmanGame game = new EvilHangmanGame();
        File Dictionary = new File(dictionaryName);
        Scanner input = new Scanner(System.in);
        
    }

}
